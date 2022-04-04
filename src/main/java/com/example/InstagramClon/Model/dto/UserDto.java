package com.example.InstagramClon.Model.dto;

import com.example.InstagramClon.Model.Post;
import com.example.InstagramClon.Model.Roles;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class UserDto implements UserDetails {
    private long id;
    private String username;

    private String password;
    //private Roles role;

    private List<PostDto> posts;
    private String description;
    private int numOfFollowers;
    private int numOfFollowing;
    private List<String> followings;
    private List<String> followers;

    public UserDto(long id, String name, List<Post> posts, String description, int numOfFollowers,
                   int numOfFollowing, List<String> followings,
                   List<String> followers) {
        this.id = id;
        this.username = name;
        this.posts = posts.stream().map(post -> post.toFlat()).collect(Collectors.toList());
        this.description = description;
        this.numOfFollowers = numOfFollowers;
        this.numOfFollowing = numOfFollowing;
        this.followings = followings;
        this.followers = followers;
    }

    public UserDto(long id, String name, String description, int numOfFollowers, int numOfFollowing) {
        this.id = id;
        this.username = name;
        this.description = description;
        this.numOfFollowers = numOfFollowers;
        this.numOfFollowing = numOfFollowing;
    }

    public UserDto(long id, String name, String description, int numOfFollowers, int numOfFollowing, String password) {
        this.id = id;
        this.username = name;
        this.description = description;
        this.numOfFollowers = numOfFollowers;
        this.numOfFollowing = numOfFollowing;
    }

    public void addRemoveFollowing(String name) {
        if(this.getFollowings().contains(name))
            this.getFollowings().remove(name);
        else
            this.getFollowings().add(name);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(this.role.toString()));
        return  null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
