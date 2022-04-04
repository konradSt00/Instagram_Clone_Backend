package com.example.InstagramClon.Model.PKs;

import com.example.InstagramClon.Model.Roles;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class LikePK implements Serializable {
    protected long postID;
    protected long userID;


    public LikePK(long postID, long userID) {
        this.postID = postID;
        this.userID = userID;
    }

    public LikePK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikePK likePK = (LikePK) o;
        return postID == likePK.postID && userID == likePK.userID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postID, userID);
    }
}
