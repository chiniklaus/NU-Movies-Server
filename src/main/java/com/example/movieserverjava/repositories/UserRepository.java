package com.example.movieserverjava.repositories;


import com.example.movieserverjava.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository
        extends CrudRepository<User, Long> {

    @Query(value = "select * from user where username=:username", nativeQuery = true)
    public User findUserByUsername(
            @Param("username") String uname);

    @Query("select user from User user where user.username=:username and user.password=:password")
    public User findUserByCredentials(
            @Param("username") String username,
            @Param("password") String password);
}