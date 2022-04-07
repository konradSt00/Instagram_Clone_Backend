package com.example.InstagramClon.Controllers;

import com.example.InstagramClon.Model.Post;
import com.example.InstagramClon.Model.User;
import com.example.InstagramClon.Model.dto.UserDto;
import com.example.InstagramClon.ModelServices.CommentService;
import com.example.InstagramClon.ModelServices.PostService;
import com.example.InstagramClon.ModelServices.UserService;
import com.example.InstagramClon.Model.dto.PostDto;
import com.example.InstagramClon.utils.JsonParsing;
import com.example.InstagramClon.utils.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@Slf4j
public class PostController {

    private CommentService commentService;
    private PostService postService;
    private UserService userService;
    @Autowired
    public PostController(CommentService commentService, PostService postService, UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/p/{id}", method= RequestMethod.POST)
    public ResponseEntity<Long> addComment(@PathVariable long id,
                                           @RequestParam MultipartFile userName,
                                           @RequestParam MultipartFile comment) {
        try {
            Post post = this.postService.getPost(id);
            User user = this.userService.getUser(JsonParsing.toString(userName));
            return new ResponseEntity(
                    this.commentService.addNewComment(post, user,
                            JsonParsing.toString(comment)),
                    HttpStatus.OK);

        } catch (IOException e) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
    }
    @CrossOrigin
    @RequestMapping(value = "/p/{id}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable long id){
        Post post = this.postService.getPost(id);
        if(post == null){
            return ResponseEntity
                    .notFound()
                    .build();
        }else{
            try {
                return ResponseEntity
                    .ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(this.postService.getImage(post.getId()));
            } catch (IOException e) {
                return ResponseEntity
                        .noContent()
                        .build();
            }
        }
    }
    @CrossOrigin
    @RequestMapping(value = "/pi/{userName}/{id}" , method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Tuple<Boolean, PostDto>> mainWall(@PathVariable long id, @PathVariable String userName){
        try {
            Post post = this.postService.getPost(id);
            Tuple<Boolean, PostDto> result = new Tuple<>(post.userLiked(userName), post.toFlat());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/p/{id}/{userName}", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> addLike(@PathVariable long id, @PathVariable String userName ) {
        // TODO: n+1
        try {
            User user = this.userService.getUser(userName); // liking user
            Post post = this.postService.getPost(id);
            this.postService.addLike(post, user);
            return new ResponseEntity<>(user.toFlat(), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/p/{postID}/{commID}", method = RequestMethod.DELETE)
    public ResponseEntity<Long> deleteComment(@PathVariable long postID, @PathVariable long commID){
        try {
            long commId = this.commentService.deleteComment(commID);
            return new ResponseEntity<>(commId, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
