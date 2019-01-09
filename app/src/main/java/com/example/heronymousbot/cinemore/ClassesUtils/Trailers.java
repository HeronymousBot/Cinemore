package com.example.heronymousbot.cinemore.ClassesUtils;

public class Trailers {

    private String youtubeKey;

    public Trailers(String youtubeKey) {
        this.youtubeKey = youtubeKey;
    }

    public String getYoutubeKey() {
        return "https://img.youtube.com/vi/" + youtubeKey + "/maxresdefault.jpg";
    }

    public String getYoutubeUrl() {
        return youtubeKey;
    }
}
