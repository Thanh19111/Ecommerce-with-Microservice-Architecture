package com.thanhpham.Chat.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SentenceUtil {
    //pass
    public static int countTokensNaive(String text) {
        if (text == null || text.isBlank()) return 0;
        // đếm từ đã chuẩn hóa và thay thế với token hóa
        return text.trim().split("\\s+").length;
    }

    // đếm vị trí đầu câu trong đoạn
    //pass
    public static int findOffsetOfSentence(String section, List<String> sentences, int sentenceIndex) {
        // lặp đếm các từ chuẩn hóa
        int pos = 0;
        for (int i = 0; i < sentenceIndex; i++) pos += sentences.get(i).length() + 1;
        // tìm vị trí đã làm tròn
        return Math.min(pos, Math.max(0, section.length()-1));
    }

    // đếm vị trí cuối câu
    // pass
    public static int findOffsetOfSentenceEnd(String section, List<String> sentences, int sentenceIndex) {
        int pos = 0;
        for (int i = 0; i <= sentenceIndex; i++) pos += sentences.get(i).length() + 1;
        return Math.min(pos, Math.max(0, section.length()-1));
    }

    // băm text thành mã hash
    // pass
    public static String sha256(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // shingle-based Jaccard similarity
    //pass
    public static double jaccardShingleSimilarity(String a, String b, int k) {
        Set<String> sa = shingles(a, k);
        Set<String> sb = shingles(b, k);
        if (sa.isEmpty() || sb.isEmpty()) return 0.0;
        Set<String> inter = new HashSet<>(sa);
        inter.retainAll(sb);
        Set<String> uni = new HashSet<>(sa);
        uni.addAll(sb);
        return (double) inter.size() / (double) uni.size();
    }

    //pass
    public static Set<String> shingles(String text, int k) {
        String[] words = text.toLowerCase().replaceAll("[^\\p{L}\\p{Nd}\\s]+"," ").split("\\s+");
        Set<String> set = new HashSet<>();
        for (int i = 0; i + k <= words.length; i++) {
            String sh = String.join(" ", Arrays.copyOfRange(words, i, i + k));
            set.add(sh);
        }
        return set;
    }
}
