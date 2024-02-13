package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.RoleModel;
import com.AlexandreLoiola.AccessManagement.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByDescription(String description);
    Optional<UserModel> findByDescriptionAndIsActiveTrue(String description);
    List<UserModel> findByIsActiveTrue();

}
