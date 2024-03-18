package org.example.igotthese.data.dao.impl;

import java.util.List;
import java.util.Optional;
import org.example.igotthese.data.dao.UserDao;
import org.example.igotthese.data.entity.User;
import org.example.igotthese.data.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserDaoImpl implements UserDao {
    UserRepository userRepository;
    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> getUserByPostId(Long postId) {
        return userRepository.findByPostId(postId);
    }

    @Override
    public List<User> getTop10User() {
        return userRepository.findTop10OrderByHoldingSeals();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
