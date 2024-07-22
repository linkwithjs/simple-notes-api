package com.linkwithjs.simplenotesapi.controller;

import com.linkwithjs.simplenotesapi.dto.ReqRes;
import com.linkwithjs.simplenotesapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
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
        return ResponseEntity.ok(userService.updateUser(userId,userDetails));
    }
}
