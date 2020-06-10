package com.example.movieserverjava.controllers;

import com.example.movieserverjava.models.Friendship;
import com.example.movieserverjava.models.User;
import com.example.movieserverjava.repositories.MovieRepository;
import com.example.movieserverjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    MovieRepository movieRepository;

    /**
     *  create a user
     * @param user the user object received from the client
     * @return depends on if the creation was successful. if successful, return the created user.
     * if not, return dummy user.
     */
    @PostMapping("/api/accounts")
    public User createUser(@RequestBody User user) {
        if (repository.findUserByUsername((user.getUsername())) != null) {
            return new User(-1,"dummyname","dummypassword", "admin");
        }
        repository.save(user);
        return user;
    }

    /**
     *  verify if the given password is correct.
     * @param user the user object get from client
     * @param session httpsession
     * @return original user if successful
     */
    @PostMapping("/api/accounts/verify")
    public User verifyPassword(@RequestBody User user,
                               HttpSession session) {
        User u = repository.findUserByUsername(user.getUsername());
        if (u == null) {
            return new User(-1,"wrong","userNoneExist", "admin");
        }
        if (u.getPassword().equals(user.getPassword())) {
            session.setAttribute("currentUser", u);
            return u;
        }
        return new User(-1,"wrong","passwordNotMatch", "admin");
    }

    /**
     * return the current login user
     * @param session cookie
     * @return the current user
     */
    @GetMapping("/currentUser")
    public User getCurrentUser(HttpSession session) {
        User u = (User)session.getAttribute("currentUser");
        if (u == null || u.getId() == -1) return new User(-1,"wrong","nocurrent", "admin");
        return repository.findUserByUsername(u.getUsername());
    }

    /**
     * return the current login username
     * @param session cookie
     * @return the current user
     */
    @GetMapping("/currentUsername")
    public User getCurrentUsername(HttpSession session) {
        User u = (User)session.getAttribute("currentUser");
        if (u == null || u.getId() == -1) return new User(-1,"wrong","nocurrent", "admin");
        return new User(-2,u.getUsername(),"current", "admin");
    }

    /**
     * logout by clearing cookie
     * @param session cookie
     */
    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @GetMapping("/getUser/{username}")
    public User getUser(@PathVariable("username") String username) {
        User u = repository.findUserByUsername(username);
        if (u == null) {
            return new User(-1, "dontexist", "wrong", "admin");
        }
        return u;
    }

    @PutMapping("/api/accounts/update/username")
    public void updateUsername(@RequestBody String[] info) {
        User user = repository.findUserByUsername(info[1]);
        user.setUsername(info[0]);
        repository.save(user);
    }

    @PutMapping("/api/accounts/update/password")
    public void updatePassword(@RequestBody String[] info) {
        User user = repository.findUserByUsername(info[1]);
        user.setPassword(info[0]);
        repository.save(user);
    }

    @RequestMapping(value = "/api/accounts/image/{username}", method = RequestMethod.POST, consumes = "multipart/form-data")
    public void uploadImage(@PathVariable("username") String username,
                            @RequestPart MultipartFile image) throws IOException
    {
        User user = repository.findUserByUsername(username);
        user.setProfileImage(image.getBytes());
        repository.save(user);
    }

    @GetMapping("/api/accounts/image/{username}")
    public User getImage(@PathVariable("username") String username) {
        User user = repository.findUserByUsername(username);
        return new User(user.getProfileImage());
    }

    @RequestMapping(value = "/api/accounts/bgImage/{username}", method = RequestMethod.POST, consumes = "multipart/form-data")
    public void uploadBackgroundImage(@PathVariable("username") String username,
                            @RequestPart MultipartFile image) throws IOException
    {
        User user = repository.findUserByUsername(username);
        user.setBackgroundImage(image.getBytes());
        repository.save(user);
    }

    @GetMapping("/api/accounts/friends")
    public List<String> getUserFriends(HttpSession session) {
        User u = (User)session.getAttribute("currentUser");
        List<String> result = new ArrayList<>();
        if (u == null || u.getId() == -1) return result;
        for (Friendship f : u.getRequested()) {
            if (f.isValid()) result.add(f.getReceiverName());
        }

        for (Friendship f : u.getReceived()) {
            if (f.isValid()) result.add(f.getRequesterName());
        }
        return result;
    }
}