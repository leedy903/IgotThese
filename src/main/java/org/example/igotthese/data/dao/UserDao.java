package org.example.igotthese.data.dao;

import java.util.List;
import java.util.Optional;
import org.example.igotthese.data.entity.User;

public interface UserDao {
    User saveUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUserName(String userName);
    Optional<User> getUserByPostId(Long postId);
    List<User> getTop10User();
    List<User> getAllUser();
    void deleteUserById(Long id);
}
