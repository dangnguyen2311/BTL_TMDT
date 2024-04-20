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
