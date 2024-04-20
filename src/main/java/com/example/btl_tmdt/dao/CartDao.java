package com.example.btl_tmdt.dao;

import com.example.btl_tmdt.model.Cart;
import com.example.btl_tmdt.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDao {
    private String id;
    private UserDao userDao;

    public Cart toModel(){
        return new Cart(id, userDao.toModel());
    }
}
