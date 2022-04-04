package com.example.InstagramClon.repositories;

import com.example.InstagramClon.Model.Like;
import com.example.InstagramClon.Model.PKs.LikePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikePK> {
    List<Like> findByPostIDIn(List<Long> ids);
}
