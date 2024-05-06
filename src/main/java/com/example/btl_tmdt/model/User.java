package com.example.btl_tmdt.model;

import com.example.btl_tmdt.dao.UserDao;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;
    private String userName;
    private String userEmail;
    private String userPass;
    private String userRole;
    private String userPhone;
    private String userFullName;
    private String userAddress;

    public User(String userName, String userPass, String userEmail, String userRole, String userPhone, String userFullName, String userAddress) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userRole = userRole;
        this.userPhone = userPhone;
        this.userFullName = userFullName;
        this.userAddress = userAddress;
    }

    public User(String userName, String userPass, String userEmail, String userPhone, String userFullName, String userAddress) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userRole = "1";
        this.userPhone = userPhone;
        this.userFullName = userFullName;
        this.userAddress = userAddress;
    }

    public UserDao toDao(){
        return new UserDao(userId, userName, userEmail, userPass, userRole, userPhone, userFullName, userAddress);
    }

}
