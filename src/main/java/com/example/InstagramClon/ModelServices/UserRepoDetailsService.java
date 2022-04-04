package com.example.InstagramClon.ModelServices;

import com.example.InstagramClon.Model.User;
import com.example.InstagramClon.Model.dto.UserDto;
import com.example.InstagramClon.Model.dtoMappers.UserDtoMapper;
import com.example.InstagramClon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserRepoDetailsService implements UserDetailsService {
    UserRepository usersRepository;
    @Autowired
    public UserRepoDetailsService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDto user = UserDtoMapper.mapUserToDtoWithPassword(this.usersRepository.findByUsername(username));
            System.out.println("TU: " + user.getUsername() + "  " + user.getPassword());
            return user;
        }catch(NoSuchElementException e){
            throw new UsernameNotFoundException("User " + username + " not found");
        }

    }
}
