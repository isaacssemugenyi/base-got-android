package com.bcs.basegpt.api;

public class ChatItem {
    private String id;

    private String question;

    public ChatItem(String id, String question){
        this.id = id;
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                '}';
    }
}
