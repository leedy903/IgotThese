package org.example.igotthese.controller;

import jakarta.validation.Valid;
import org.example.igotthese.data.dto.UserDto;
import org.example.igotthese.data.dto.UserDto.Create;
import org.example.igotthese.data.dto.UserDto.Update;
import org.example.igotthese.data.dto.UserDto.Response;
import org.example.igotthese.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Long> createUser(@RequestBody @Valid Create create) {
        Long userId = userService.saveUser(create);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }


    @GetMapping(value = "/info")
    public ResponseEntity<UserDto.Response> getUserByUserName(@RequestParam String userName) {
        Response user = userService.getUserByUserName(userName);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping(value = "/edit")
    public ResponseEntity<Long> updateUser(@RequestBody @Valid Update update) {
        Long userId = userService.updateUser(update);
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> deleteUserById(@RequestParam Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

//    deprecated
//    @GetMapping(value = "/info/{id}")
//    public ResponseEntity<UserDto.Response> getUserById(@PathVariable("id") Long id) {
//        Response user = userService.getUserById(id);
//        return ResponseEntity.status(HttpStatus.OK).body(user);
//    }
}
