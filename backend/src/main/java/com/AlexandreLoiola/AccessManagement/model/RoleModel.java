package com.AlexandreLoiola.AccessManagement.model;

import lombok.Data;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name="TB_ROLE")
public class RoleModel {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="uuid")
    private UUID id;

    @Column(name = "description", length = 100, nullable = false, unique = true)
    private String description;

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

    @ManyToMany
    @JoinTable(
            name="TB_ROLE_AUTHORIZATION",
            joinColumns = {@JoinColumn(name = "id_role", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "id_authorization")
    )
    private Set<AuthorizationModel> authorizations = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<UserModel> users = new HashSet<>();
}
