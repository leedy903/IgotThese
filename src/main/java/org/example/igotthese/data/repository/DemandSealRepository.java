package org.example.igotthese.data.repository;

import java.util.List;
import org.example.igotthese.data.entity.DemandSeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandSealRepository extends JpaRepository<DemandSeal, Long> {
    List<DemandSeal> findAllByPostId(Long postId);
}
