package com.example.InstagramClon.Model;

import com.example.InstagramClon.Model.dto.PostDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@Getter @Setter
@Table(name = "POSTS")
public class Post implements Comparator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "post")
    private List<Comment> commentList;

    @JoinTable(name = "LIKES", joinColumns = @JoinColumn(name = "postID"),
            inverseJoinColumns = @JoinColumn(name = "userID"))
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<User> usersLikes;

    @Column(name = "IMG_PATH")
    private String imgLocalPath;

    private int likes;
    private Date date;
    private String description;
    public Post() {
    }

    public Post(User user, String description) {
        this.user = user;
        this.description = description;
        this.likes = 0;
        this.commentList = new LinkedList<>();
        this.usersLikes = new LinkedList<>();
        this.date = Calendar.getInstance().getTime();
    }

    public Boolean userLiked(String name) {
        return User.checkIfContainsByName(this.usersLikes, name);
    }

    public PostDto toFlat(){
        return new PostDto(this.id, this.user.getUsername(),
                this.description, this.getCommentList(),
                this.getUsersLikes().stream().map(user1 -> user1.getUserID()).collect(Collectors.toList()),
                this.getLikes(), this.getDate());
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }

    public boolean addLike(User user){
        if(this.userLiked(user.getUsername())){
            this.setLikes(this.getLikes() - 1);
            List<User> newLikesList= this.getUsersLikes().stream()
                    .filter(like -> user.getUsername().equals(user.getUsername()))
                    .collect(Collectors.toList());
            this.setUsersLikes(newLikesList);
            return true;

        }else{
            this.getUsersLikes().add(user);
            this.setLikes(this.getLikes() + 1);
            return false;
        }

    }
    @Override
    public int compare(Object o1, Object o2) {
        if(o1 instanceof Post && o2 instanceof Post){
            return (-1) * (int)(((Post)o1).getDate().getTime() - ((Post)o2).getDate().getTime());
        }
        return 0;
    }
}
