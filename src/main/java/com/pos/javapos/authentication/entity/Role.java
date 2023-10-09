package com.pos.javapos.authentication.entity;

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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name) && Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, users);
    }

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "permission_role",
//            joinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
//    private Set<Permission> permissions = new HashSet<>();

    //    public void assignPermissionToRole(Permission permission){
//        this.permissions.add(permission);
//        permission.getRoles().add(this);
//    }
//
//
//
//    public void removePermissionFromRole(Permission permission){
//        this.permissions.remove(permission);
//        permission.getRoles().remove(this);
//    }


}
