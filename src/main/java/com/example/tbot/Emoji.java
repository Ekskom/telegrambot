package com.example.tbot;


import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emoji {
    BLUSH(EmojiParser.parseToUnicode(":blush:")),
    DOLLAR(EmojiParser.parseToUnicode(":dollar:")),
    RAISED_HANDS(EmojiParser.parseToUnicode(":raised_hands:"));

    private final String emojiName;

    @Override
    public String toString(){ return emojiName; }
}
