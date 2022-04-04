package com.example.InstagramClon.repositories;

import com.example.InstagramClon.Model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
    boolean existsByUsername(String userName);
    @Query(value = "select u.userID from User u where u.username like ?1")
    Long findIdByUsername(String name);

    @Query(value = "select u.userID from User u where u.username IN ?1")
    List<Long> findIdByUsernameIn(List<String> followings);

}
