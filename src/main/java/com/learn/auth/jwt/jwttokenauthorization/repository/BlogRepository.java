package com.learn.auth.jwt.jwttokenauthorization.repository;

import com.learn.auth.jwt.jwttokenauthorization.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity,Integer> {

  List<BlogEntity> findByTitleContainingOrContentContaining(String text, String textAgain);

}