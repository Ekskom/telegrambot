package com.example.tbot;


import com.vdurmont.emoji.EmojiParser;



public enum Emojis {

    BLUSH(EmojiParser.parseToUnicode(":blush:")),
    DOLLAR(EmojiParser.parseToUnicode(":dollar:")),
    RAISED_HANDS(EmojiParser.parseToUnicode(":raised_hands:"));

    private String emojiName;

    Emojis(String emojiName) {
        this.emojiName = emojiName;
    }

    @Override
    public String toString(){ return emojiName; }
}
