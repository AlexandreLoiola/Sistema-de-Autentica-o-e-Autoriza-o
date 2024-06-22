package com.AlexandreLoiola.AccessManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_authorization")
@EqualsAndHashCode
public class AuthorizationModel implements GrantedAuthority {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "description", length = 100, nullable = false, unique = true)
    private String description;

    @Column(name = "path", length = 100, nullable = false)
    private String path;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Version
    @Column(name = "version", nullable = false)
    private long version;

    @ManyToMany
    @JsonManagedReference
    @JoinTable(
            name="tb_authorization_method",
            joinColumns = {@JoinColumn(name = "id_authorization", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "id_method")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<MethodModel> methods = new HashSet<>();

    @JsonBackReference
    @ManyToMany(mappedBy = "authorizations")
    private Set<RoleModel> roles = new HashSet<>();

    @Override
    public String getAuthority() {
        return this.path;
    }
}