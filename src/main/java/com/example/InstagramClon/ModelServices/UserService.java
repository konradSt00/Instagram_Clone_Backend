package com.example.InstagramClon.ModelServices;

import com.example.InstagramClon.Model.PKs.FollowerPK;
import com.example.InstagramClon.Model.Post;
import com.example.InstagramClon.Model.User;
import com.example.InstagramClon.Model.dto.UserDto;
import com.example.InstagramClon.Model.dtoMappers.UserDtoMapper;
import com.example.InstagramClon.repositories.FollowerRepository;
import com.example.InstagramClon.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserService {

    private UserRepository userRepository;
    private PostService postService;
    private FollowerRepository followerRepository;
    @Autowired
    public UserService(UserRepository userRepository, PostService postService, FollowerRepository followerRepository) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.followerRepository = followerRepository;
    }

    public User getUser(String name) throws NoSuchElementException{
        Optional<User> userOptional = this.userRepository.findByUsername(name);
        if(userOptional.isPresent()){
            return userOptional.get();
        }else{
            System.out.println(name);
            throw new NoSuchElementException();
        }
    }
    public Long getUserId(String name){
        return this.userRepository.findIdByUsername(name);
    }
    public UserDto getUserDtoWithPosts(String name) throws RuntimeException{
        UserDto userDto = this.getUserDto(name);
        userDto.setPosts(postService.getPostsWithCommentsByUsers(List.of(userDto.getId())));
        return userDto;
    }

    public UserDto getUserDto(String name) throws NoSuchElementException{
        UserDto userDto = UserDtoMapper.mapUserToDtoWithFollowers(this.userRepository.findByUsername(name));
        return userDto;
    }


    public byte[] getProfilePhoto(User user) throws IOException {
        try(FileInputStream file =
                    new FileInputStream(user.getProfileImgUrl())){
            return file.readAllBytes();
        } catch (FileNotFoundException e) {
            System.out.println(user.getProfileImgUrl());
        } catch (IOException e) {
            throw new IOException();
        }
        return null;
    }
    @Transactional
    public UserDto addRemoveFollower(String followedUserName, String followerUserName) throws NoSuchElementException{
        // TODO: n+1
        // TODO: not save posts null field

        User follower = this.getUser(followerUserName);
        User followedUser = this.getUser(followedUserName);

        followedUser = followedUser.addRemoveFollower(follower);
        follower = this.addRemoveFollowing(follower, followedUser);
        followerRepository
                .delete(followerRepository.getById(new FollowerPK(follower.getUserID(), followedUser.getUserID())));
        this.userRepository.saveAll(List.of(follower, followedUser));
        return follower.toFlat();
    }

    private User addRemoveFollowing(User follower, User following){
        return follower.addRemoveFollowing(following);
    }

    public UserDto addNewUser(String userName) throws RuntimeException{
        if(this.userRepository.existsByUsername(userName)){
            throw new RuntimeException();
        }else{
            User newUser = new User(userName);
            newUser = this.userRepository.save(newUser);
            return newUser.toFlat();
        }
    }

    public List<UserDto> getUsersByPhrase(String phrase) throws RuntimeException{
        List<UserDto> userDtoList = UserDtoMapper.mapUsersToDto(this.userRepository.findAll());

        List<UserDto> users = userDtoList.stream()
                                    .filter(user -> user.getUsername().startsWith(phrase))
                                    .collect(Collectors.toList());

        return users;
    }


    public List<Long> getUsersIdsByNames(List<String> followings) {
        return this.userRepository.findIdByUsernameIn(followings);
    }
}
