package com.example.InstagramClon.Controllers;

import com.example.InstagramClon.Model.Post;
import com.example.InstagramClon.Model.User;
import com.example.InstagramClon.Model.dto.PostDto;
import com.example.InstagramClon.ModelServices.PostService;
import com.example.InstagramClon.ModelServices.UserService;
import com.example.InstagramClon.utils.JsonParsing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.NoSuchElementException;

@RestController
@Slf4j
public class NewPostController {
    private UserService userService;
    private PostService postService;

    @Autowired
    public NewPostController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @CrossOrigin
    @PostMapping(value = "newPost")
    public ResponseEntity<PostDto> savePost(@RequestParam MultipartFile userName,
                                            @RequestParam MultipartFile photo,
                                            @RequestParam MultipartFile description){

        HttpStatus httpStatus = HttpStatus.OK; User user; Post post = null;

        try {
            user = this.userService.getUser(JsonParsing.toString(userName));
            try {
                post = this.postService.saveNewPost(user,
                        JsonParsing.toString(description));
                savePhoto(photo, post);
            }catch (RuntimeException e){
                httpStatus = HttpStatus.NOT_ACCEPTABLE;
            }
        }catch (NoSuchElementException | IOException e){
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(post.toFlat(), httpStatus);
    }
    private void savePhoto(MultipartFile photo, Post post) throws IOException{
        try (OutputStream os = new FileOutputStream(post.getImgLocalPath())) {
            os.write(photo.getBytes());
        }
    }
}
