package com.example.InstagramClon.Controllers;

import com.example.InstagramClon.Model.User;
import com.example.InstagramClon.ModelServices.UserService;
import com.example.InstagramClon.Model.dto.UserDto;
import com.example.InstagramClon.utils.JsonParsing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Slf4j
public class ProfileController {

    private UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/{userName}", method = GET, produces = "application/json")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("userName") String userName) {
        try {
            UserDto user1 = this.userService.getUserDtoWithPosts(userName);
            return new ResponseEntity<>(user1, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/profile_photo/{userName}", method = RequestMethod.GET, // TODO
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String userName){
        try{
            User user = this.userService.getUser(userName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(this.userService.getProfilePhoto(user));

        }catch (NoSuchElementException | IOException e){
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }
    @CrossOrigin
    @RequestMapping(value = "/{followedUser}/{followingUser}", method = RequestMethod.PUT) // TODO
    public ResponseEntity<UserDto> follow(@PathVariable String followedUser, @PathVariable String followingUser){
        try {
            UserDto follower = this.userService
                    .addRemoveFollower(followedUser, followingUser);
            return new ResponseEntity<>(follower, HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


}
