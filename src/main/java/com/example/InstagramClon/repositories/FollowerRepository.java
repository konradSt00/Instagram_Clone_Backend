package com.example.InstagramClon.repositories;

import com.example.InstagramClon.Model.Follower;
import com.example.InstagramClon.Model.PKs.FollowerPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowerRepository extends JpaRepository<Follower, FollowerPK> {
}
