package org.aykhan.dataprovider.repository;

import org.aykhan.dataprovider.entity.UserDM;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDMRepository extends CrudRepository<UserDM, Long> {
  Optional<UserDM> getByNickname(String nickname);
}
