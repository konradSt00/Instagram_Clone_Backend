package com.example.InstagramClon.utils;

public class Tuple<X, Y>{
    private X liked;
    private Y post;

    public Tuple(X x, Y y) {
        this.liked = x;
        this.post = y;
    }

    public X getLiked() {
        return liked;
    }

    public void setLiked(X liked) {
        this.liked = liked;
    }

    public Y getPost() {
        return post;
    }

    public void setPost(Y post) {
        this.post = post;
    }
}
