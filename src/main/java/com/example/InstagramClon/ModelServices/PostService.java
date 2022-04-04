package com.example.InstagramClon.ModelServices;

import com.example.InstagramClon.Model.Comment;
import com.example.InstagramClon.Model.Like;
import com.example.InstagramClon.Model.Post;
import com.example.InstagramClon.Model.User;
import com.example.InstagramClon.Model.dto.PostDto;
import com.example.InstagramClon.Model.dto.UserDto;
import com.example.InstagramClon.repositories.LikeRepository;
import com.example.InstagramClon.repositories.PostRepository;
import com.example.InstagramClon.utils.ConfigProperties;
import com.example.InstagramClon.Model.PKs.LikePK;
import com.example.InstagramClon.utils.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PostService {
    private PostRepository postRepository;
    private ConfigProperties configProperties;
    private LikeRepository likeRepository;
    private CommentService commentService;
    @Autowired
    public PostService(PostRepository postRepository,
                       ConfigProperties configProperties,
                       LikeRepository likeRepository,
                       CommentService commentService
                       ) {
        this.postRepository = postRepository;
        this.configProperties = configProperties;
        this.likeRepository = likeRepository;
        this.commentService = commentService;
    }

    public Post getPost(long id) throws NoSuchElementException {
        Optional<Post> postOptional = postRepository.findById(id);
        if(postOptional.isPresent()){
            return postOptional.get();
        }else{
            throw new NoSuchElementException();
        }
    }
    public Post saveNewPost(User user, String description) throws RuntimeException{
        Post post = new Post(user, description);
        post = postRepository.save(post);
        String path =  configProperties.getImgsPath() + post.getId() + "." + configProperties.getImgsFormat();
        post.setImgLocalPath(path);
        return postRepository.save(post);
    }
    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    public byte[] getImage(long postID) throws IOException {
        try(FileInputStream file =
                    new FileInputStream(configProperties.getImgsPath() +
                            postID + "." + configProperties.getImgsFormat())){
            return file.readAllBytes();
        }
    }
    @Transactional
    public void addLike(Post post, User user) {

        boolean liked = post.addLike(user);
        if(liked) {
            Like like = this.likeRepository.findById(new LikePK(post.getId(), user.getUserID())).get();
            this.likeRepository.delete(like);
        }else {
            this.postRepository.save(post);
        }

    }

    public List<PostDto> getPostsWithCommentsByUsers(List<Long> userIds) {
        List<Long> postIds = new LinkedList<>();
        List<PostDto> result = new LinkedList<>();

        this.postRepository.findByUser_UserIDIn(userIds).stream()
                .forEach(post -> result.add(new PostDto(post.getId(),
                                                post.getUser().getUsername(),
                                                post.getDescription(),
                                                post.getLikes(),
                                                post.getDate())));
        result.stream()
            .map(post -> post.getId())
            .forEach(postIds::add);

        List<Comment> allComments = this.commentService.getComments(postIds);
        List<Like> allLikes = this.likeRepository.findByPostIDIn(postIds);

        return this.setCommentsAndLikes( result, allComments, allLikes );
    }

    private List<PostDto> setCommentsAndLikes(List<PostDto> posts, List<Comment> allComments, List<Like> allLikes){
        if(allComments != null && allComments.size() > 0){
            posts.stream()
                    .forEach(postDto -> postDto.setCommentList(allComments.stream()
                            .filter(comment -> comment.getPost().getId() == postDto.getId())
                            .map(comment -> comment.toFlat())
                            .collect(Collectors.toList())));
        }
        if(allLikes != null && allLikes.size() > 0){
            posts.stream()
                    .forEach(postDto -> {
                        postDto.setUsersLikes(allLikes.stream()
                                .filter(like -> like.getPostID() == postDto.getId())
                                .map(like -> like.getUserID())
                                .collect(Collectors.toList())
                        );
                    });
        }

         return posts;
    }

    public List<Tuple<Boolean, PostDto>> getLastPostsOfFollowing(UserService userService, String userName) throws RuntimeException{
        UserDto userDto = userService.getUserDto(userName);
        userDto.addRemoveFollowing(userDto.getUsername());
        List<Long> userIds = userService.getUsersIdsByNames(userDto.getFollowings());

        List<Tuple<Boolean, PostDto>> result =  this.getPostsWithCommentsByUsers(userIds).stream()
                .map(post -> new Tuple<>(post.userLked(userDto.getId()), post))
                .collect(Collectors.toList());

        return result.stream()
                .sorted((o1, o2) -> (-1) * (int)(o1.getPost().getDate().getTime() - o2.getPost().getDate().getTime()))
                .collect(Collectors.toList());
    }
}
