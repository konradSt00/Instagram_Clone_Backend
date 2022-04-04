package com.example.InstagramClon.Model;

import com.example.InstagramClon.Model.PKs.LikePK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@IdClass(LikePK.class)
@Table(name = "LIKES")
public class Like {
    @Id
    private long postID;
    @Id
    private long userID;

    @Override
    public String toString() {
        return "Like{" +
                "postID=" + postID +
                ", userID=" + userID +
                '}';
    }

    public Like() {
    }

    public Like(long postID, long userID) {
        this.postID = postID;
        this.userID = userID;
    }
}
