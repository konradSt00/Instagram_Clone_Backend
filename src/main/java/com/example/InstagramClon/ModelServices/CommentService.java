package com.example.InstagramClon.ModelServices;

import com.example.InstagramClon.Model.Comment;
import com.example.InstagramClon.Model.Post;
import com.example.InstagramClon.Model.User;
import com.example.InstagramClon.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long addNewComment(Post post, User user, String text) {
        Comment comment1 = new Comment(
                user,
                post,
                text,
                Calendar.getInstance().getTime());
        Comment newComment = this.commentRepository.save(comment1);
        if(newComment != null){
            return newComment.getId();
        }return null;

    }
    @Transactional
    public Long deleteComment(Long commID) throws NoSuchElementException {
        Comment comment = this.getComment(commID);
        if(comment != null) {
            this.commentRepository.delete(comment);
            return comment.getId();
        }else
            throw new NoSuchElementException();
    }
    @Transactional
    public Comment getComment(long commID){
        Optional<Comment> comment = this.commentRepository.findById(commID);
        if(comment.isPresent()){
            return comment.get();
        }
        return null;
    }

    public List<Comment>  getComments(List<Long> postIds) {
        return this.commentRepository.findByPostIdIn(postIds);
    }
}
