package com.example.InstagramClon.Model;

import com.example.InstagramClon.Model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@Table(name = "USERS")
public class User implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;
    private String username;
    private String password;
    //private String role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts;
    private String description;
    @Column(name = "num_of_followers")
    private int numOfFollowers;
    @Column(name = "num_of_following")
    private int numOfFollowing;

    @JoinTable(name = "FOLLOWERS", joinColumns = @JoinColumn(name = "followerID", referencedColumnName = "userID"),
            inverseJoinColumns = @JoinColumn(name = "followingID", referencedColumnName = "userID"))
    @ManyToMany(cascade = CascadeType.DETACH)
    private List<User> followings;

    @ManyToMany(mappedBy = "followings")
    private List<User> followers;

    @Column(name = "profile_img_url")
    private String profileImgUrl;



    public User(){
    }

    public User(String name, String encodedPassword) {
        this.username = name;
        this.description = "Lorem ipsum ijf oop wkkfs";
        this.numOfFollowers = 0;
        this.numOfFollowing = 0;
        this.posts = new LinkedList<>();
        this.followers = new LinkedList<>();
        this.followings = new LinkedList<>();
        this.followings.add(this);
        this.password = encodedPassword;
        //TODO: set profile photo option
        this.profileImgUrl = "C:/Users/stark/Desktop/Nowy folder/profileImg.jpg";
    }


    public void addPost(Post post) {
        this.posts.add(post);
    }

    public User addRemoveFollower(User follower){
        System.out.println(follower.getUsername());
        if(User.checkIfContainsByName(this.getFollowers(), follower.getUsername())){
            List<User> newFollowersList = this.followers.stream()
                                                .filter(user -> !user.getUsername().equals(follower.getUsername()))
                                                .collect(Collectors.toList());
            this.followers = newFollowersList;
            this.setNumOfFollowers(this.getNumOfFollowers() - 1); ;
        }else {
            this.getFollowers().add(follower);
            this.setNumOfFollowers(this.getNumOfFollowers() + 1);
        }
        return this;
    }

    public User addRemoveFollowing(User following){
        if(User.checkIfContainsByName(this.getFollowings(), following.getUsername())){
            List<User> newFollowingsList = this.getFollowings().stream()
                    .filter(user -> !user.getUsername().equals(this.getUsername()))
                    .collect(Collectors.toList());
            this.setFollowings(newFollowingsList);
            this.setNumOfFollowing(this.getNumOfFollowing() - 1);
        }else{
            this.getFollowings().add(following);
            this.setNumOfFollowing(this.getNumOfFollowing() + 1);
        }
        return this;
    }

    public UserDto toFlat(){
        return new UserDto(this.userID, this.username, this.posts, this.description, this.numOfFollowers,
                    this.numOfFollowing, User.mapUsersToNames(this.followings), User.mapUsersToNames(this.followers));
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof User){
            if(((User) o).getUsername().equals(this.username)){
                return 0;
            }
        }
        return -1;
    }

    public static boolean checkIfContainsByName(List<User> userList, String name){
        return userList.stream()
                .map(user -> user.getUsername())
                .anyMatch(name1 -> name1.equals(name));
    }
    public static List<String> mapUsersToNames(List<User> userList){
        return userList.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
