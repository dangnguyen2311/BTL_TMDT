package com.example.btl_tmdt.model;


import com.example.btl_tmdt.dao.ProductInOrderDao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.convert.DataSizeUnit;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_in_order")
public class ProductInOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    private Order order;

    @OneToOne
    private Product product;

    private Integer quantity;
    private Double totalPrice;

    public ProductInOrderDao toDao(){
        return new ProductInOrderDao(id, order.toDao(), product.toDao(), quantity, totalPrice);
    }

}
