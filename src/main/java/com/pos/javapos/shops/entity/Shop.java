package com.pos.javapos.shops.entity;

import com.pos.javapos.authentication.entity.User;
import com.pos.javapos.helper.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "shop")
public class Shop extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    private String name;
    @Column(nullable = false,length = 100)
    private String address;
    @Column(nullable = false,length = 100)
    private String contact;
    @Email(message = "Invalid email")
    private String email;
    private String owner;

    private String logo;
    private String description;
    @Column(columnDefinition = "jsonb")
    private String shop_object;

    @OneToMany(mappedBy = "shop",fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Branch> branches = new HashSet<>();

    @OneToMany(mappedBy = "shops",fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<User> users = new HashSet<>();



    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Shop shop = (Shop) o;
        return getId() != null && Objects.equals(getId(), shop.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }


}
