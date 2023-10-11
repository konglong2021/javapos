package com.pos.javapos.shops.entity;

import com.pos.javapos.authentication.entity.Role;
import com.pos.javapos.authentication.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "shop")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = true,length = 100)
    private String address;
    @Column(nullable = true,length = 100)
    private String contact;
    @Column(nullable = true,length = 100)
    private String email;
    private String owner;

    private String logo;
    private String description;
    @Column(columnDefinition = "jsonb")
    private String shop_object;

    @OneToMany(mappedBy = "shop",fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Branch> branchs = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shop_user",
            joinColumns = {@JoinColumn(name = "shop_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
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

    public void assignUserToShop(User user){
        this.users.add(user);
        user.getShops().add(this);
    }

    public void removeUserFromShop(User user){
        this.users.remove(user);
        user.getShops().remove(this);
    }

}