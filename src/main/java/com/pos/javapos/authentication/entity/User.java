package com.pos.javapos.authentication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pos.javapos.helper.AuditableEntity;
import com.pos.javapos.shops.entity.Shop;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
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

    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Shop> shops = new HashSet<>();

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
                ", shops=" + shops +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }

}
