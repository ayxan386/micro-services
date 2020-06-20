package com.aykhand.contentmanager.repository;

import com.aykhand.contentmanager.entity.FileDM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends JpaRepository<FileDM, Long> {
  Optional<FileDM> findFirstByName(String filename);
}
