package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    @Query("SELECT DISTINCT a FROM AuthorizationModel a LEFT JOIN FETCH a.methods WHERE a.description = :description AND a.isActive = true")
    Optional<RoleModel> findByDescriptionAndFetchAuthorizations(@Param("description") String description);

    @Query("SELECT r FROM RoleModel r JOIN FETCH r.authorizations a JOIN FETCH a.methods WHERE r.isActive = true")
    Set<RoleModel> findByIsActiveTrueAndFetchAuthorizationsEagerly();

    @Modifying
    @Query(value = "DELETE FROM tb_role_authorization WHERE id_role = :roleId", nativeQuery = true)
    void deleteRoleAuthorization(@Param("roleId") UUID roleId);

    Optional<RoleModel> findByDescription(String description);
    Optional<RoleModel> findByDescriptionAndIsActiveTrue(String description);

    Set<RoleModel> findByIsActiveTrue();
}
