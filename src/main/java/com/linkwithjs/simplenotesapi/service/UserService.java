package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.entity.UserEntity;
import com.linkwithjs.simplenotesapi.exception.CustomException;
import com.linkwithjs.simplenotesapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service

public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
   private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow();
    }

    public ResponseEntity<?> readAllUsers() {
        List<ReqRes> users = userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, ReqRes.class))
//                .map(user->new UserResponse(user.getId(),user.getUsername(),user.getEmail(),user.getRoles()))
                .collect(Collectors.toList());
        return ReqRes.successResponse("Users fetched successfully!", users);
    }

    public ResponseEntity<?> deleteUser(int id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("Error: User not found for this id : " + id));
        userRepository.delete(user);
        log.info("User Deleted. {}", user.getUsername());
        return ReqRes.successResponse("User deleted successfully!",user);
    }

}
