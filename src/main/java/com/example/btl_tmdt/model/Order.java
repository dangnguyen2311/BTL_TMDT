package com.example.btl_tmdt.model;

import com.example.btl_tmdt.dao.OrderDao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;
    private String address;
    private String phone;
    private String fullName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Double total;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date orderTime;


    public OrderDao toDao() {
        return new OrderDao(orderId, address, phone, fullName, user.toDao(), total, orderTime);
    }
}
