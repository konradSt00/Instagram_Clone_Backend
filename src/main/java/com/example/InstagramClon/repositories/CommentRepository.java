package com.example.InstagramClon.repositories;

import com.example.InstagramClon.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdIn(List<Long> postIds);

}
