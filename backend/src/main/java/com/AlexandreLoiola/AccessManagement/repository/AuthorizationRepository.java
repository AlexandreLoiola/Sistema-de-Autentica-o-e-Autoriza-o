package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AuthorizationRepository extends JpaRepository<AuthorizationModel, UUID> {
        Optional<AuthorizationModel> findByDescription(String description);

    @Query(value = "SELECT a.description AS authorization_description, m.description AS method_description " +
            "FROM tb_authorization a " +
            "JOIN tb_authorization_method am ON a.id = am.id_authorization " +
            "JOIN tb_method m ON am.id_method = m.id " +
            "WHERE a.description = :authorizationDescription", nativeQuery = true)
    Set<Object[]> findAuthorizationWithMethods(@Param("authorizationDescription") String authorizationDescription);

    @Modifying
    @Query(value = "DELETE FROM TB_AUTHORIZATION_METHOD WHERE id_authorization = :authorizationId", nativeQuery = true)
    void deleteAuthorizationMethods(@Param("authorizationId") UUID authorizationId);

    Optional<AuthorizationModel> findByDescriptionAndIsActiveTrue(String description);

    Set<AuthorizationModel> findByIsActiveTrue();
}
