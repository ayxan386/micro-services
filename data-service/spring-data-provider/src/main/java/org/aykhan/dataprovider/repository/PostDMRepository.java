package org.aykhan.dataprovider.repository;

import org.aykhan.dataprovider.entity.PostDM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDMRepository extends JpaRepository<PostDM, Long> {
  List<PostDM> findAllByAuthor_Nickname(String nickname);
}
