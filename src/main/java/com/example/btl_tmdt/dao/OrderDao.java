package com.example.btl_tmdt.dao;

import com.example.btl_tmdt.model.Order;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDao {
    private String orderId;
    private String address;
    private String phone;
    private String fullName;
    private UserDao userDao;
    private Double total;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date order_time;

    public Order toModel(){
        return new Order(orderId,  address, phone, fullName, userDao.toModel(), total, order_time);
    }
}
