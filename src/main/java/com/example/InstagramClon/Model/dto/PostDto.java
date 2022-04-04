package com.example.InstagramClon.Model.dto;

import com.example.InstagramClon.Model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostDto {
    private long id;
    private String userName;
    private String description;
    private List<CommentDto> commentList;
    private List<Long> usersLikes;
    private int likes;
    private Date date;


    public PostDto(long id, String userName,
                   String description, List<Comment> commentList,
                   List<Long> usersLikes, int likes, Date date) {

        this.id = id;
        this.userName = userName;
        this.description = description;
        this.commentList = commentList.stream().map(comment -> comment.toFlat()).collect(Collectors.toList());
        this.usersLikes = usersLikes;
        this.likes = likes;
        this.date = date;
    }

    public PostDto(long id, String userName, String description, int likes, Date date) {
        this.id = id;
        this.userName = userName;
        this.description = description;
        this.likes = likes;
        this.date = date;
    }

    public Boolean userLked(Long userId) {
        if(usersLikes != null)
            return usersLikes.contains(userId);
        else{
            return false;
        }
    }
}
