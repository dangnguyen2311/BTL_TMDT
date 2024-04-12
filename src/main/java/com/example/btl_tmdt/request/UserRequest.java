package com.example.btl_tmdt.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {
    private String userName;
    private String userEmail;
    private String userPass;
    private String userRole;
    private String userPhone;
}
