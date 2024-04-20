package com.example.btl_tmdt.model;

import com.example.btl_tmdt.dao.CartDao;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "cart")
@AllArgsConstructor
public class Cart  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    private User user;

    public CartDao toDao(){
        return new CartDao(id, user.toDao());
    }

}
