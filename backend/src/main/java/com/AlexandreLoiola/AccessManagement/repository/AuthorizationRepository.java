package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.AuthorizationModel;
import com.AlexandreLoiola.AccessManagement.model.RoleModel;
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

//    @Query(value = "SELECT a.description AS authorization_description, m.description AS method_description " +
//            "FROM tb_authorization a " +
//            "JOIN tb_authorization_method am ON a.id = am.id_authorization " +
//            "JOIN tb_method m ON am.id_method = m.id " +
//            "WHERE a.description = :authorizationDescription", nativeQuery = true)
//    Set<Object[]> findAuthorizationWithMethods(@Param("authorizationDescription") String authorizationDescription);

    @Query("SELECT DISTINCT a FROM AuthorizationModel a LEFT JOIN FETCH a.methods WHERE a.description = :description AND a.isActive = true")
    Optional<AuthorizationModel> findByDescriptionAndFetchMethods(@Param("description") String description);

    @Query("SELECT DISTINCT r FROM AuthorizationModel r LEFT JOIN FETCH r.methods WHERE r.isActive = true")
    Set<AuthorizationModel> findByIsActiveTrueAndFetchMethodsEagerly();

    @Modifying
    @Query(value = "DELETE FROM tb_authorization_method WHERE id_authorization = :authorizationId", nativeQuery = true)
    void deleteAuthorizationMethods(@Param("authorizationId") UUID authorizationId);

    Optional<AuthorizationModel> findByDescriptionAndIsActiveTrue(String description);

    Set<AuthorizationModel> findByIsActiveTrue();
}
