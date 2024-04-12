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
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name") // loix do cai UserEntity
    private String userName;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_pass")
    private String userPass;
    @Column(name = "user_role")
    private String userRole;
    @Column(name = "user_phone")
    private String userPhone;

    public User(String userName, String userPass, String userEmail, String userRole, String userPhone) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userRole = userRole;
        this.userPhone = userPhone;
    }

    public User(String userName, String userPass, String userEmail, String userPhone) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userRole = "1";
        this.userPhone = userPhone;
    }

    public UserDao toDao(){
        return new UserDao(userId, userName, userEmail, userPass, userRole, userPhone);
    }

}
