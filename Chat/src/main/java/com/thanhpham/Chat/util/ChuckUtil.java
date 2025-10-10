package com.thanhpham.Chat.util;

import com.thanhpham.Chat.dto.response.Chuck;

import java.util.*;

public class ChuckUtil {
    // chia text thành chuck (chuck này dùng cho câu dài thôi)
    public static List<String> chunkText(String text, int chunkSize, int overlap) {
        List<String> chunks = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + chunkSize, text.length());
            chunks.add(text.substring(start, end));
            // nếu start nhỏ hơn 0 thay bằng 0
            start = Math.max(end - overlap, 0);
        }
        return chunks;
    }

    /*
    đocId: mã tài liệu
    sentences: mảng các câu đã tách từ đoạn văn(section)
    section: nội dung chưa tách câu của đoạn văn
    maxTokens: giá trị tối đa token có thể có trong chuck
    minTokens: giá trị tối thiểu token có trong chuck
    overlapSentences: thêm overlap để giữ ngữ cảnh so với chuck trước đó
     */
    public static List<Chuck> chunkBySentences(String docId, List<String> sentences, String section,
                                               int maxTokens, int overlapSentences, int minTokens) {
        List<Chuck> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int currentTokenCount = 0;
        int sentenceStartIdx = 0;

        // giả sử = 0, sau này sửa sau
        int sectionCharStart = 0;

        // duyệt mảng các câu
        for (int i = 0; i < sentences.size(); i++) {
            String s = sentences.get(i);
            // đếm các từ trong câu
            int sTokens = SentenceUtil.countTokensNaive(s);
            if (currentTokenCount + sTokens > maxTokens && currentTokenCount > 0) {
                // chuck này đã thỏa mãn điền kiện nhỏ hơn max token
                // mảng vẫn đang xử lý chuỗi s

                // tìm vị trí bắt đầu và kết thúc của chuck
                // chuckStartChar vị trí ký tự đầu tiên của chuck trong đoạn văn
                // chuckEndChar là vị trí ký tự cuối cùng của chuck trong đoạn văn
                // ví dụ: [H]ello World, .... the en[d]
                // dùng để highlight hoặc để trích nguồn khi cần
                int chunkStartChar = SentenceUtil.findOffsetOfSentence(section, sentences, sentenceStartIdx);
                int chunkEndChar = SentenceUtil.findOffsetOfSentenceEnd(section, sentences, i - 1);

                // xử lý nội dung cuối (loại bỏ khoảng cách ở đầu và cuối) và tạo checksum cho chuck (checksum kiểm tra xem có nội dung chuck có trùng với chuck khác không)
                String content = current.toString().trim();
                String checksum = SentenceUtil.sha256(content);

                //thêm record
                result.add(new Chuck(UUID.randomUUID().toString()
                        , docId
                        , result.size(),
                        sectionCharStart + chunkStartChar,
                        sectionCharStart + chunkEndChar,
                        sentenceStartIdx, i - 1,
                        content, currentTokenCount, checksum));

                // chuẩn bị thêm overlap cho chuck
                // đề phòng chuck chỉ có một câu thì overlap bằng chính nó luôn
                // overlapStartSentence câu bắt đầu overlap
                // đảm bảo overlapStart lớn hơn 0
                int overlapStartSentence = Math.max(sentenceStartIdx, i - overlapSentences);
                // reset chuck thêm overlap để tạo chuck mới
                current = new StringBuilder();
                currentTokenCount = 0;
                for (int j = overlapStartSentence; j < i; j++) {
                    current.append(sentences.get(j)).append(" ");
                    currentTokenCount += SentenceUtil.countTokensNaive(sentences.get(j));
                }
                sentenceStartIdx = overlapStartSentence;
            }
            // nếu chuck chưa đủ token, thêm câu vào cho current, tăng giá trị của token hiện thời, sau đó tiếp tục vòng lặp
            current.append(s).append(" ");
            currentTokenCount += sTokens;
        }

        // tạo chuck cuối
        // nêu current còn nội dung thì tạo chuck
        if (!current.isEmpty()) {
            int lastIdx = sentences.size() - 1;
            int chunkStartChar = SentenceUtil.findOffsetOfSentence(section, sentences, sentenceStartIdx);
            int chunkEndChar = SentenceUtil.findOffsetOfSentenceEnd(section, sentences, lastIdx);
            String content = current.toString().trim();
            String checksum = SentenceUtil.sha256(content);
            result.add(new Chuck(UUID.randomUUID().toString(), docId, result.size(),
                    sectionCharStart + chunkStartChar,
                    sectionCharStart + chunkEndChar,
                    sentenceStartIdx, lastIdx,
                    content, currentTokenCount, checksum));
        }

        // gộp các chuck nhỏ hơn với các chuck bên cạnh nó
        result = mergeSmallChunks(result, minTokens);
        return result;
    }

    // gộp các chuck nhỏ hơn
    //pass
    public static List<Chuck> mergeSmallChunks(List<Chuck> chunks, int minTokens) {
        if (chunks.isEmpty()) return chunks;
        List<Chuck> merged = new ArrayList<>();
        Chuck buffer = chunks.getFirst();
        for (int i = 1; i < chunks.size(); i++) {
            Chuck next = chunks.get(i);
            if (buffer.tokenCount() < minTokens) {
                // gộp chuck với chuck bên cạnh nó
                String combined = buffer.content() + " " + next.content();
                int combinedTokens = buffer.tokenCount() + next.tokenCount();
                String checksum = SentenceUtil.sha256(combined);
                buffer = new Chuck(buffer.id(), buffer.docId(), buffer.index(),
                        buffer.startChar(), next.endChar(),
                        buffer.sentenceStart(), next.sentenceEnd(),
                        combined, combinedTokens, checksum);
            } else {
                merged.add(buffer);
                buffer = next;
            }
        }
        merged.add(buffer);
        return merged;
    }

    // kiểm tra xem có chuck nào trùng nội dung không
    public static List<Chuck> deduplicateChunks(List<Chuck> chunks, double jaccardThreshold) {
        // trích xuất, kiểm tra checksum
        Map<String, Chuck> uniqueMap = new LinkedHashMap<>();
        for (Chuck c : chunks) {
            if (!uniqueMap.containsKey(c.checksum())) {
                uniqueMap.put(c.checksum(), c);
            }
        }
        List<Chuck> unique = new ArrayList<>(uniqueMap.values());

        // near-duplicate: simple O(n^2) compare (ok for moderate N, use LSH for big scale)
        boolean[] removed = new boolean[unique.size()];
        for (int i = 0; i < unique.size(); i++) {
            if (removed[i]) continue;
            for (int j = i + 1; j < unique.size(); j++) {
                if (removed[j]) continue;
                double sim = SentenceUtil.jaccardShingleSimilarity(unique.get(i).content(), unique.get(j).content(), 5);
                if (sim >= jaccardThreshold) {
                    // keep the longer or the earlier one
                    if (unique.get(i).content().length() >= unique.get(j).content().length()) {
                        removed[j] = true;
                    } else {
                        removed[i] = true;
                    }
                }
            }
        }
        List<Chuck> out = new ArrayList<>();
        for (int i = 0; i < unique.size(); i++) if (!removed[i]) out.add(unique.get(i));
        return out;
    }

}
