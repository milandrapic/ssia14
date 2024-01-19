package com.manning.ssia14.repository;

import com.manning.ssia14.model.AppClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppClientJpaRepository extends JpaRepository<AppClient, Integer> {

    Optional<AppClient> findByClientId(String clientId);
}
