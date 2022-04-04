package com.example.InstagramClon.Controllers;

import com.example.InstagramClon.Model.Post;
import com.example.InstagramClon.Model.User;
import com.example.InstagramClon.Model.dto.UserDto;
import com.example.InstagramClon.ModelServices.PostService;
import com.example.InstagramClon.ModelServices.UserService;
import com.example.InstagramClon.Model.dto.PostDto;
import com.example.InstagramClon.utils.Tuple;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class MainWallController {
    private UserService userService;
    private PostService postService;

    @Autowired
    public MainWallController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @CrossOrigin
    @GetMapping(path = "/main_wall/{userName}", produces="application/json" )
    public ResponseEntity<List<Tuple<Boolean, PostDto>>> mainWall(@PathVariable String userName){
        try {
            List<Tuple<Boolean, PostDto>> result =
                    this.postService.getLastPostsOfFollowing(this.userService, userName);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }






}
