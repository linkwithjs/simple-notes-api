package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.ChangePasswordRequest;
import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-users")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return userService.readAllUsers();
    }

    @DeleteMapping("/delete-user/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") int userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/update-user/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id") int userId, @RequestBody ReqRes userDetails) {
        return ResponseEntity.ok(userService.updateUser(userId, userDetails));
    }

    @PatchMapping("/change-password")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Principal connectedUser) {
        return ResponseEntity.ok(userService.changePassword(request, connectedUser));
    }

}
