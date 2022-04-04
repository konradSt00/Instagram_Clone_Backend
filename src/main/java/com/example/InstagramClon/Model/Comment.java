package com.example.InstagramClon.Model;
import com.example.InstagramClon.Model.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Entity
@Getter @Setter
@Table(name="COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMID")
    private long id;
    @ManyToOne
    @JoinColumn(name = "userID")
    public User user;
    @ManyToOne()
    @JoinColumn(name = "postID")
    private Post post;
    public Date date;
    public String text;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                '}';
    }

    public Comment() {
    }

    public Comment(User user, Post post, String text, Date date) {
        this.user = user;
        this.post = post;
        this.text = text;
        this.date = date;
    }


    public CommentDto toFlat(){
        return new CommentDto(this.id, this.user.getUsername(), this.date, this.text);
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }
}
