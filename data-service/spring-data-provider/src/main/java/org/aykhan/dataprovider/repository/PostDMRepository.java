package org.aykhan.dataprovider.repository;

import org.aykhan.dataprovider.entity.PostDM;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDMRepository extends CrudRepository<PostDM, Long> {
  List<PostDM> findAllByAuthor_Nickname(String nickname);
}
