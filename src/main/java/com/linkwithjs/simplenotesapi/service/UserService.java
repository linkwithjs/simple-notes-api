package com.linkwithjs.simplenotesapi.service;

import com.linkwithjs.simplenotesapi.controller.ChangePasswordRequest;
import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.entity.UserEntity;
import com.linkwithjs.simplenotesapi.exception.CustomException;
import com.linkwithjs.simplenotesapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
//@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;



    public UserService(@Lazy PasswordEncoder passwordEncoder){
        this.passwordEncoder   = passwordEncoder;
    }


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
        return ReqRes.successResponse("User deleted successfully!", user);
    }

    public ResponseEntity<?> updateUser(int id, ReqRes userDetails) {
        UserEntity user = userRepository.findById(id).
                orElseThrow(() -> new CustomException("Error: User not found for this id : " + id));

        UserEntity existingUserWithEmail = userRepository.findByEmail(userDetails.getEmail()).orElseThrow(()->
                new CustomException("Error: User not found for this email : " + userDetails.getEmail()));

        // Find user by email to check if it exists
        if (existingUserWithEmail.getEmail() != null && !existingUserWithEmail.getId().equals(user.getId())) {
            return ResponseEntity.badRequest().body("Email already taken.");
        }

        // Update user details
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());

        // Save updated user details
        userRepository.save(user);

        // Return success response
        return ResponseEntity.ok("User updated successfully!");
//        if (!userDetails.getEmail().equals(emailExist.getEmail()) || user.getEmail().equals(userDetails.getEmail())) {
//            user.setEmail(userDetails.getEmail());
//            user.setRole(userDetails.getRole());
//            userRepository.save(user);
//            return ReqRes.successResponse("User updated successfully!", user);
//        } else {
//            return ReqRes.successResponse("Email Already Taken.", user);
//        }
    }


    public ReqRes changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        ReqRes resp = new ReqRes();
        try{
//            Check if current password is correct
            if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
                throw new CustomException("Error: Current password does not match.");
            }
            //        Check if two password are same
            if(!request.getNewPassword().equals(request.getConfirmPassword())){
                throw new CustomException("Error: Password does not match.");
            }
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//        save the new password
            userRepository.save(user);
            resp.setMessage("Password Changed Successfully.");
        }catch (Exception e){
            resp.setStatusCode(500);
            log.error(e.getMessage());
            resp.setError(e.getMessage());
        }
        return resp;
    }
}
