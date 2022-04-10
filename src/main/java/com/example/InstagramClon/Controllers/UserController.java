package com.example.InstagramClon.Controllers;

import com.example.InstagramClon.Model.dto.UserDto;
import com.example.InstagramClon.ModelServices.UserService;
import com.example.InstagramClon.utils.JsonParsing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<UserDto> registerNewUser(@RequestParam MultipartFile newUserName,
                                                   @RequestParam MultipartFile password) {
        try {
            UserDto newUser = this.userService.addNewUser(JsonParsing.toString(newUserName),
                    JsonParsing.toString(password));
            return new ResponseEntity<>(newUser, HttpStatus.OK);

        } catch (IOException | RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/search/{phrase}", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> searchUsers(@PathVariable String phrase){
        try {
            List<UserDto> users = this.userService.getUsersByPhrase(phrase);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }


}
