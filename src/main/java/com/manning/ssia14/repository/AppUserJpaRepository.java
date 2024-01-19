package com.manning.ssia14.repository;


import com.manning.ssia14.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AppUserJpaRepository extends JpaRepository<AppUser,Integer> {

    Optional<AppUser> findByUsername(String username);

}
