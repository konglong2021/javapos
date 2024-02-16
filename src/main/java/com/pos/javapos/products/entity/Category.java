package com.pos.javapos.products.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.helper.AuditableEntity;
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
@Table(name = "categories")
public class Category extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String tag;

    @JsonIgnore
    @ManyToMany(mappedBy = "category",fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Product> products = new HashSet<>();
}
