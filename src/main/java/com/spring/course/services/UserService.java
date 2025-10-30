package com.spring.course.services;

import com.spring.course.entities.User;
import com.spring.course.repositories.UserRepository;
import com.spring.course.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void insert(User user) {
        userRepository.save(user);
    }

    public boolean deleteById(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    public User update(Long id, User newUser) {
        User entity = userRepository.getReferenceById(id);
        updateData(entity, newUser);
        return userRepository.save(entity);
    }

    private void updateData(User entity, User newUser) {
        entity.setName(newUser.getName());
        entity.setEmail(newUser.getEmail());
        entity.setPhone(newUser.getPhone());
    }


}
