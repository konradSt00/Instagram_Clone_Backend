package com.example.InstagramClon.Model.dto;

import com.example.InstagramClon.Model.Comment;
import com.example.InstagramClon.repositories.CommentRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

@Getter @Setter
public class CommentDto {
    private long id;
    private String userName;
    private Date date;
    private String text;
    @Autowired
    private CommentRepository commentRepository;

    public CommentDto(long id, String userName, Date date, String text) {
        this.id = id;
        this.date = date;
        this.userName = userName;
        this.text = text;
    }
    public Comment toComment(){
        Optional<Comment> commentOptional = this.commentRepository.findById(id);
        if(commentOptional.isPresent()){
            return commentOptional.get();
        }else{
            return null;
        }
    }

}
