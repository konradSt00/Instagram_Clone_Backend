package com.example.InstagramClon.Model.dtoMappers;

import com.example.InstagramClon.Model.User;
import com.example.InstagramClon.Model.dto.UserDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDtoMapper {

    public static List<UserDto> mapUsersToDto(List<User> allUsers) throws RuntimeException {
        return allUsers.stream()
                .map(user -> new UserDto(user.getUserID(),
                        user.getUsername(),
                        user.getDescription(),
                        user.getNumOfFollowers(),
                        user.getNumOfFollowing()))
                .collect(Collectors.toList());
    }

    public static UserDto mapUserToDto(Optional<User> user) throws NoSuchElementException {
        if (!user.isPresent()) {
            throw new NoSuchElementException();
        }
        UserDto userDto = new UserDto(user.get().getUserID(),
                user.get().getUsername(),
                user.get().getDescription(),
                user.get().getNumOfFollowers(),
                user.get().getNumOfFollowing());


        return userDto;

    }
    public static UserDto mapUserToDtoWithFollowers(Optional<User> user) throws NoSuchElementException {
        UserDto userDto = mapUserToDto(user);
        userDto.setFollowings(user.get().getFollowings().stream()
                .map(user1 -> user1.getUsername()).collect(Collectors.toList()));
        userDto.setFollowers(user.get().getFollowers().stream()
                .map(user1 -> user1.getUsername()).collect(Collectors.toList()));
        return  userDto;
    }

    public static UserDto mapUserToDtoWithPassword(Optional<User> user) throws NoSuchElementException {
        UserDto userDto = mapUserToDto(user);
        userDto.setPassword(user.get().getPassword());
        return userDto;

    }

}
