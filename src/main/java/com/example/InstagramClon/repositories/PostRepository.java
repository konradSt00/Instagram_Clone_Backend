package com.example.InstagramClon.repositories;

import com.example.InstagramClon.Model.Post;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from LIKES l where l.postID=:postID and userID=:userID")
    void deleteLikeByIdAndUser_userID(@Param("postID") long postID, @Param("userID") long userID);

    List<Post> findByUser_UserIDIn(List<Long> userIds);
}
