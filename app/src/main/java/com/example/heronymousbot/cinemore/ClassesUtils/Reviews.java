package com.example.heronymousbot.cinemore.ClassesUtils;

public class Reviews {
    private String author;
    private String content;
    private String reviewUrl;

    public Reviews(String author, String content, String reviewUrl) {
        this.author = author;
        this.content = content;
        this.reviewUrl = reviewUrl;
    }

    public String getAuthor() {
        return "Review by " + author;
    }

    public String getContent() {
        if (content.length() >= 200) {
            return content.substring(0, 301) + "...";
        } else {
            return content.trim();
        }
    }

    public String getReviewUrl() {
        return reviewUrl;
    }
}
