package com.AlexandreLoiola.AccessManagement.repository;

import com.AlexandreLoiola.AccessManagement.model.MailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MailRepository extends JpaRepository<MailModel, UUID> {
    Optional<MailModel> findBySubjectAndIsActiveTrue(String subject);
}
