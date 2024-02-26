package com.pos.javapos.products.entity;


import com.pos.javapos.helper.AuditableEntity;
import com.pos.javapos.shops.entity.Branch;
import com.pos.javapos.shops.entity.Shop;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "products")
public class Product extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;
    private String image;
    private String sku;
    private String type;
    private Double price;
    @Column(columnDefinition = "jsonb")
    private String product_object;
    private String description;
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id",referencedColumnName = "id")
    @ToString.Exclude
    private Shop shops;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id",referencedColumnName = "id")
    @ToString.Exclude
    private Branch branches;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_category",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")})
    private Set<Category> category = new HashSet<>();

    public void addCategory(Category category){
        this.category.add(category);
        category.getProducts().add(this);
    }

    public void removeCategory(Category category){
        this.category.remove(category);
        category.getProducts().remove(this);
    }

}
