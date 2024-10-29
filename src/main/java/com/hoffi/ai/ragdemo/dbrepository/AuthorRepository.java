package com.hoffi.ai.ragdemo.dbrepository;

import com.hoffi.ai.ragdemo.dbmodel.Author;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

  Optional<Author> findByUsername(String username);
}
