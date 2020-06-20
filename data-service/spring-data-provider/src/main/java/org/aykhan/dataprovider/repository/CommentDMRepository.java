package org.aykhan.dataprovider.repository;

import org.aykhan.dataprovider.entity.CommentsDM;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDMRepository extends CrudRepository<CommentsDM, Long> {
}
