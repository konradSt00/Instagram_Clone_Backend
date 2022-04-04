package com.example.InstagramClon.Model.PKs;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter

public class FollowerPK implements Serializable {
    private long followerID;
    private long followingID;

    public FollowerPK(long followerID, long followingID) {
        this.followerID = followerID;
        this.followingID = followingID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowerPK that = (FollowerPK) o;
        return followerID == that.followerID && followingID == that.followingID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerID, followingID);
    }
}
