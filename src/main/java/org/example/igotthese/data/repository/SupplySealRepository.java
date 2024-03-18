package org.example.igotthese.data.repository;

import java.util.List;
import org.example.igotthese.data.entity.SupplySeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplySealRepository extends JpaRepository<SupplySeal, Long> {

    List<SupplySeal> findAllByPostId(Long postId);
}
