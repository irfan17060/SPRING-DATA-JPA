package org.irfan.sdjpaintro.repo;

import org.irfan.sdjpaintro.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
