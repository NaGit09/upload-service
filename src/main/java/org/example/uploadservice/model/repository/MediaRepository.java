package org.example.uploadservice.model.repository;

import org.example.uploadservice.model.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    Optional<Media> findByPublicId(String publicId);
}
