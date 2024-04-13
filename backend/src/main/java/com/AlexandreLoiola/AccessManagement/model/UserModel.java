package com.AlexandreLoiola.AccessManagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name="TB_USER")
public class UserModel {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="uuid")
    private UUID id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name="TB_USER_ROLE",
            joinColumns = {@JoinColumn(name = "id_user", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<RoleModel> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserModel)) return false;
        UserModel that = (UserModel) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
