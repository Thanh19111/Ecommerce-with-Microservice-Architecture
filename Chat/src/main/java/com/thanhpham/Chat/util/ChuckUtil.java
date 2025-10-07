package com.thanhpham.Chat.util;

import com.thanhpham.Chat.dto.response.Chuck;

import java.text.BreakIterator;
import java.util.*;

public class ChuckUtil {
    public static List<String> chunkText(String text, int chunkSize, int overlap) {
        List<String> chunks = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + chunkSize, text.length());
            chunks.add(text.substring(start, end));
            start = end - overlap;
            if (start < 0) start = 0;
        }
        return chunks;
    }

    public static List<Chuck> chunkBySentences(String docId, List<String> sentences, String section,
                                               int sectionCharStart, int startingIndex,
                                               int maxTokens, int overlapSentences, int minTokens) {
        List<Chuck> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int currentTokenCount = 0;
        int sentenceStartIdx = 0;
        int charCursor = sectionCharStart; // approximate base offset

        for (int i = 0; i < sentences.size(); i++) {
            String s = sentences.get(i);
            int sTokens = countTokensNaive(s);
            if (currentTokenCount + sTokens > maxTokens && currentTokenCount > 0) {
                // finalize chunk
                int chunkStartChar = findOffsetOfSentence(section, sentences, sentenceStartIdx);
                int chunkEndChar = findOffsetOfSentenceEnd(section, sentences, i - 1);
                String content = current.toString().trim();
                String checksum = sha256(content);
                result.add(new Chunk(UUID.randomUUID().toString(), docId, result.size(),
                        sectionCharStart + chunkStartChar,
                        sectionCharStart + chunkEndChar,
                        sentenceStartIdx, i - 1,
                        content, currentTokenCount, checksum));
                // prepare overlap
                int overlapStartSentence = Math.max(sentenceStartIdx, i - (int)overlapSentences);
                current = new StringBuilder();
                currentTokenCount = 0;
                for (int j = overlapStartSentence; j < i; j++) {
                    current.append(sentences.get(j)).append(" ");
                    currentTokenCount += countTokensNaive(sentences.get(j));
                }
                sentenceStartIdx = overlapStartSentence;
            }
            current.append(s).append(" ");
            currentTokenCount += sTokens;
        }

        // finalize last chunk
        if (current.length() > 0) {
            int lastIdx = sentences.size() - 1;
            int chunkStartChar = findOffsetOfSentence(section, sentences, sentenceStartIdx);
            int chunkEndChar = findOffsetOfSentenceEnd(section, sentences, lastIdx);
            String content = current.toString().trim();
            String checksum = sha256(content);
            result.add(new Chunk(UUID.randomUUID().toString(), docId, result.size(),
                    sectionCharStart + chunkStartChar,
                    sectionCharStart + chunkEndChar,
                    sentenceStartIdx, lastIdx,
                    content, currentTokenCount, checksum));
        }

        // merge tiny chunks (< minTokens) with neighbor
        result = mergeSmallChunks(result, minTokens);
        return result;
    }

    // --- merge tiny chunks ---
    public static List<Chuck> mergeSmallChunks(List<Chunk> chunks, int minTokens) {
        if (chunks.isEmpty()) return chunks;
        List<Chunk> merged = new ArrayList<>();
        Chunk buffer = chunks.get(0);
        for (int i = 1; i < chunks.size(); i++) {
            Chunk next = chunks.get(i);
            if (buffer.tokenCount < minTokens) {
                // merge buffer + next
                String combined = buffer.content + " " + next.content;
                int combinedTokens = buffer.tokenCount + next.tokenCount;
                String checksum = sha256(combined);
                buffer = new Chunk(buffer.id, buffer.docId, buffer.index,
                        buffer.startChar, next.endChar,
                        buffer.sentenceStart, next.sentenceEnd,
                        combined, combinedTokens, checksum);
            } else {
                merged.add(buffer);
                buffer = next;
            }
        }
        merged.add(buffer);
        return merged;
    }

    // --- dedup: exact + near-dup (shingle Jaccard) ---
    public static List<Chuck> deduplicateChunks(List<Chunk> chunks, double jaccardThreshold) {
        // exact dedup using checksum
        Map<String, Chuck> uniqueMap = new LinkedHashMap<>();
        for (Chuck c : chunks) {
            if (!uniqueMap.containsKey(c.checksum())) {
                uniqueMap.put(c.checksum(), c);
            }
        }
        List<Chunk> unique = new ArrayList<>(uniqueMap.values());

        // near-duplicate: simple O(n^2) compare (ok for moderate N, use LSH for big scale)
        boolean[] removed = new boolean[unique.size()];
        for (int i = 0; i < unique.size(); i++) {
            if (removed[i]) continue;
            for (int j = i + 1; j < unique.size(); j++) {
                if (removed[j]) continue;
                double sim = jaccardShingleSimilarity(unique.get(i).content, unique.get(j).content, 5);
                if (sim >= jaccardThreshold) {
                    // keep the longer or the earlier one
                    if (unique.get(i).content.length() >= unique.get(j).content.length()) {
                        removed[j] = true;
                    } else {
                        removed[i] = true;
                    }
                }
            }
        }
        List<Chunk> out = new ArrayList<>();
        for (int i = 0; i < unique.size(); i++) if (!removed[i]) out.add(unique.get(i));
        return out;
    }
}
