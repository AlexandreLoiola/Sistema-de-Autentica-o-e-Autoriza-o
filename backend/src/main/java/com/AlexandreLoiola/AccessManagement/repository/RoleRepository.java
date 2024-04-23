package com.AlexandreLoiola.AccessManagement.repository;

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

    @Query(value = "SELECT a.description AS role_description, m.description AS authorization_description " +
            "FROM tb_role a " +
            "JOIN tb_role_authorization am ON a.id = am.id_role " +
            "JOIN tb_authorization m ON am.id_authorization = m.id " +
            "WHERE a.description = :roleDescription", nativeQuery = true)
    Set<Object[]> findRoleWithAuthorizations(@Param("roleDescription") String roleDescription);

    @Modifying
    @Query(value = "DELETE FROM TB_ROLE_AUTHORIZATION WHERE id_role = :roleId", nativeQuery = true)
    void deleteRoleAuthorization(@Param("roleId") UUID roleId);

    Optional<RoleModel> findByDescription(String description);
    Optional<RoleModel> findByDescriptionAndIsActiveTrue(String description);
    Set<RoleModel> findByIsActiveTrue();

}
