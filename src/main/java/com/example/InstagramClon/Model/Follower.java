package com.example.InstagramClon.Model;

import com.example.InstagramClon.Model.PKs.FollowerPK;
import com.example.InstagramClon.Model.PKs.LikePK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@IdClass(FollowerPK.class)
@Table(name = "FOLLOWERS")
public class Follower {
    @Id
    private long followerID;
    @Id
    private long followingID;

    public Follower() {
    }


}
