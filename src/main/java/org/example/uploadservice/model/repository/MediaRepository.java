package org.example.uploadservice.model.repository;


import org.example.uploadservice.constant.UploadType;
import org.example.uploadservice.model.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query("SELECT m FROM Media m WHERE m.uploadType = :type AND m.createdAt < :cutoff")
    List<Media> getAllDraftFile(@Param("type") UploadType type, @Param("cutoff") LocalDateTime cutoffDate);


}
