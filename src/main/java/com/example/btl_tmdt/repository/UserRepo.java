package com.example.btl_tmdt.repository;

import com.example.btl_tmdt.dao.UserDao;
import com.example.btl_tmdt.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    User findUserByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);

    User getUserByUserName(String username);

//    void updateUserByUserName(String username);

    User getUserByUserEmail(String userEmail);

    User findUserByUserName(String  username);

    @Query(value = "UPDATE btl_web.users SET user_full_name = :fullName, user_phone = :phone, user_address = :address WHERE user_id = :userId", nativeQuery = true)
    @Transactional
    @Modifying
    void updateUserInfo(@Param("userId") String userId, @Param("fullName") String fullName, @Param("phone") String phone, @Param("address") String address);

//    @Query(value = "UPDATE btl_web.users SET user_full_name = :fullName, user_phone = :phone, user_address = :address WHERE user_id = :userId", nativeQuery = true)
//    @Transactional
//    @Modifying
//    void saveUser(@Param("userId") String userId, @Param("fullName") String fullName, @Param("phone") String phone, @Param("address") String address);

}
