package com.pos.javapos.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pos.javapos.helper.AuditableEntity;
import com.pos.javapos.shops.entity.Branch;
import com.pos.javapos.shops.entity.Shop;
import jakarta.persistence.*;
import lombok.*;


import java.util.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
public class User extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "username",unique = true, nullable = false)
    private String username;
    @JsonIgnore
    private String password;
    @Column(columnDefinition = "jsonb")
    private String user_object;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id",referencedColumnName = "id")
    @ToString.Exclude
    private Shop shops;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id",referencedColumnName = "id")
    @ToString.Exclude
    private Branch branches;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(user_object, user.user_object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, user_object);
    }

    public void assignRoleToUser(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRoleFromUser(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", user_object='" + user_object + '\'' +
                ", roles=" + roles +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }

    public Shop getShop(){
        return this.shops;
    }

    public Branch getBranch(){
        return this.branches;
    }

}
