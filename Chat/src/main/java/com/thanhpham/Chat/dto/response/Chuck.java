package com.thanhpham.Chat.dto.response;

public record Chuck(String id, String docId, int index, int startChar, int endChar, int sentenceStart, int sentenceEnd, String content, int tokenCount, String checksum) {}
