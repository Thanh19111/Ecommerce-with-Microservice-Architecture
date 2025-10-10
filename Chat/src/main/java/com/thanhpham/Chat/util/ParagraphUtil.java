package com.thanhpham.Chat.util;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ParagraphUtil {
    // chia 1 đoạn văn bản lớn thành nhiều đoạn văn bản nhỏ hơn
    public static List<String> splitByParagraph(String text){
        return new ArrayList<>();
    }

    // chia 1 đoạn văn thành nhiều câu
    // pass
    public static List<String> splitToSentences(String text) {
        List<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.forLanguageTag("vi"));
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            String sentence = text.substring(start, end).trim();
            if (!sentence.isEmpty()) sentences.add(sentence);
        }
        // fallback: nếu mảng rỗng, trả về toàn bộ văn bản
        if (sentences.isEmpty() && !text.isBlank()) sentences.add(text.trim());
        return sentences;
    }

}
