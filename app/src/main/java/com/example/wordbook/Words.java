package com.example.wordbook;

import android.provider.BaseColumns;

public class Words{

    public static class WordItem {
        public String id;
        public String word;

        public WordItem(String id, String word) {
            this.id = id;
            this.word = word;

        }
        @Override
        public String toString() {
            return word;
        }
    }

    public static class WordDescription {
        public String id;
        public String word;
        public String meaning;

        public String getId() {
            return id;
        }

        public String getWord() {
            return word;
        }

        public String getMeaning() {
            return meaning;
        }

        public String getSample() {
            return sample;
        }

        public void setSample(String sample) {
            this.sample = sample;
        }

        public void setMeaning(String meaning) {
            this.meaning = meaning;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String sample;
        public WordDescription(String id, String word,String meaning, String sample) {
            this.id = id;
            this.word = word;
            this.meaning = meaning;
            this.sample = sample;
        }

        public WordDescription() {

        }
    }


    public static abstract class Word implements BaseColumns {
        public static final String TABLE_NAME = "words";//表名
        // _ID字段：主键，从接口BaseColumn而来
        public static final String COLUMN_NAME_WORD = "word";//字段：单词
        public static final String COLUMN_NAME_MEANING = "meaning";//字段：单词含义
        public static final String COLUMN_NAME_SAMPLE = "sample";//字段：单词示例

    }

}
