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

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    private Product product;

    private Integer quantity;
    private Double totalPrice;

    public ProductInOrder(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = this.quantity * Double.parseDouble(this.product.getProdPrice());
    }

    public ProductInOrderDao toDao(){
        return new ProductInOrderDao(id, order.toDao(), product.toDao(), quantity, totalPrice);
    }

}
