package com.apress.dayle.rest;

import com.apress.dayle.dto.UsersDTO;
import com.apress.dayle.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRegistrationRestController {

    public static final Logger logger =
            LoggerFactory.getLogger(UserRegistrationRestController.class);

    private UserJpaRepository userJpaRepository;

    @Autowired
    public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<UsersDTO>> listAllUsers() {
        List<UsersDTO> users = userJpaRepository.findAll();
        return new ResponseEntity<List<UsersDTO>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersDTO> createUser(@RequestBody final UsersDTO user) {
        userJpaRepository.save(user);
        return new ResponseEntity<UsersDTO>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") final Long id) {
        UsersDTO user = userJpaRepository.findById(id);
        return new ResponseEntity<UsersDTO>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersDTO> updateUser(
            @PathVariable("id") final Long id, @RequestBody UsersDTO user) {
        // fetch user based on id and set it to currentUser object of type UserDTO
        UsersDTO currentUser = userJpaRepository.findById(id);
        // update currentUser object data with user object data
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());
        // save currentUser obejct
        userJpaRepository.saveAndFlush(currentUser);
        //return ResponseEntity object
        return new ResponseEntity<UsersDTO>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsersDTO> deleteUser(@PathVariable("id") final Long id) {
        userJpaRepository.delete(id);
        return new ResponseEntity<UsersDTO>(HttpStatus.NO_CONTENT);
    }
}
