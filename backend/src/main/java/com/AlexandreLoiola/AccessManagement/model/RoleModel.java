package com.AlexandreLoiola.AccessManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tb_role")
@EqualsAndHashCode
public class RoleModel implements GrantedAuthority {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "description", length = 100, nullable = false, unique = true)
    private String description;

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

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name="tb_role_authorization",
            joinColumns = {@JoinColumn(name = "id_role", referencedColumnName = "id")},
            inverseJoinColumns = @JoinColumn(name = "id_authorization")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<AuthorizationModel> authorizations = new HashSet<>();

    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Set<UserModel> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return this.authorizations.stream()
                .map(AuthorizationModel::getDescription)
                .collect(Collectors.joining(", "));
    }
}
