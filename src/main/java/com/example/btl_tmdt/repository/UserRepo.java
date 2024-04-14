package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findUserByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);

    User getUserByUserName(String username);

//    void updateUserByUserName(String username);

    User getUserByUserEmail(String userEmail);
}
