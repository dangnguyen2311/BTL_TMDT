package com.example.btl_tmdt.dao;

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
    private String order_id;
    private String address;
    private String phone;

    private String fullName;

    private Double total;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date order_time;
}
