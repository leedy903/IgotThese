package org.example.igotthese.data.repository;

import org.example.igotthese.data.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Transactional
    @Modifying
    @Query(value = "insert into location (point) values (st_pointfromtext(:point, 4326))", nativeQuery = true)
    void savedWithPoint(@Param("point") String point);
}
