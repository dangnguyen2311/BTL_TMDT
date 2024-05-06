package com.example.btl_tmdt.dao;

import com.example.btl_tmdt.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDao {

    private String userId;
    private String userName;
    private String userEmail;
    private String userPass;
    private String userRole;
    private String userPhone;
    private String userFullName;
    private String userAddress;

    public User toModel(){
        return new User(userId, userName, userEmail, userPass, userRole, userPhone, userFullName, userAddress);
    }

}
