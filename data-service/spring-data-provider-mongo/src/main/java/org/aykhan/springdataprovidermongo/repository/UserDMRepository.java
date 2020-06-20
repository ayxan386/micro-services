package org.aykhan.springdataprovidermongo.repository;

import org.aykhan.springdataprovidermongo.entity.UserDM;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDMRepository extends MongoRepository<UserDM, String> {
    Optional<UserDM> findByNickname(String nickname);

    void deleteById(String id);
}
