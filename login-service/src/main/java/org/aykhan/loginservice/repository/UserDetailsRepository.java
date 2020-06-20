package org.aykhan.loginservice.repository;

import org.aykhan.loginservice.entity.MyUserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDetailsRepository extends CrudRepository<MyUserDetails, UUID> {
    Optional<MyUserDetails> findByUsername(String username);

    Boolean existsByUsername(String username);
}
