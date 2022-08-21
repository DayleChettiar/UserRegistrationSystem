package com.apress.dayle.rest;

import com.apress.dayle.dto.UsersDTO;
import com.apress.dayle.exception.CustomErrorType;
import com.apress.dayle.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
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
        logger.info("Fetching all users");
        List<UsersDTO> users = userJpaRepository.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<List<UsersDTO>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UsersDTO>>(users, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersDTO> createUser(@Valid @RequestBody final UsersDTO user) {
        logger.info("Creating User : {}", user);
        if (userJpaRepository.findByName(user.getName()) != null) {
            logger.error("Unable to create. A User with name {} already exist", user.getName());
            return new ResponseEntity<UsersDTO>(
                    new CustomErrorType(
                            "Unable to create new user. A User with name " + user.getName() + " already exist."),
                    HttpStatus.CONFLICT);
        }
        userJpaRepository.save(user);
        return new ResponseEntity<UsersDTO>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") final Long id) {
        logger.info("Fetching User with id {}", id);
        UsersDTO user = userJpaRepository.findById(id);
        if(user == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity<UsersDTO>(new CustomErrorType(
                    "User with id "
                     + id + " not found."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UsersDTO>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsersDTO> updateUser(
            @PathVariable("id") final Long id, @RequestBody UsersDTO user) {
        logger.info("Updating User with id {}", id);
        // fetch user based on id and set it to currentUser object of type UserDTO
        UsersDTO currentUser = userJpaRepository.findById(id);
        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity<UsersDTO>(
                    new CustomErrorType("Unable to update. User with id "
                            + id + " not found."), HttpStatus.NOT_FOUND);
        }
        // update currentUser object data with user object data
        currentUser.setName(user.getName());
        currentUser.setAddress(user.getAddress());
        currentUser.setEmail(user.getEmail());
        // save currentUser object
        userJpaRepository.saveAndFlush(currentUser);
        //return ResponseEntity object
        return new ResponseEntity<UsersDTO>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsersDTO> deleteUser(@PathVariable("id") final Long id) {
        logger.info("Deleting User with id {}", id);
        UsersDTO user = userJpaRepository.findById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity<UsersDTO>(
                    new CustomErrorType("Unable to delete. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        userJpaRepository.delete(id);
        return new ResponseEntity<UsersDTO>(new CustomErrorType("Deleted User with id " + id + "."),
                HttpStatus.NO_CONTENT);
    }
}
