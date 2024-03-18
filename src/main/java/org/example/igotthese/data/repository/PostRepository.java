package org.example.igotthese.data.repository;

import java.util.List;
import java.util.Optional;
import org.example.igotthese.data.entity.Post;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostQuerydslRepository {

    /**
     * deprecated
     * @param pokemon_id
     * @param point
     * @return
     */
    @Query(value = "select post.post_id, latitude, longitude, location, post.created_at, post.modified_at "
            + "from post "
            + "join supply_seal "
            + "on post.post_id = supply_seal.post_id "
            + "where supply_seal.pokemon_id = :pokemon_id "
            + "order by st_distance_sphere(st_pointfromtext(:point, 4326), post.location), post.modified_at", nativeQuery = true)
    List<Post> findPostBySearchOld(@Param("pokemon_id") Long pokemon_id, @Param("point") String point);

    @Query(value = "select post.post_id, latitude, longitude, location, post.created_at, post.modified_at "
            + "from post "
            + "join supply_seal "
            + "on post.post_id = supply_seal.post_id "
            + "where supply_seal.pokemon_id = :pokemon_id "
            + "and st_contains(st_buffer(st_pointfromtext(:point, 4326), 500000), post.location) "
            + "order by st_distance_sphere(st_pointfromtext(:point, 4326), post.location), post.modified_at", nativeQuery = true)
    List<Post> findPostBySearch(@Param("pokemon_id") Long pokemon_id, @Param("point") String point);

    @Query(value = "select * "
            + "from post "
            + "where st_contains(st_buffer(st_pointfromtext(:point, 4326), 5000), post.location) "
            + "order by st_distance_sphere(st_pointfromtext(:point, 4326), post.location), post.modified_at", nativeQuery = true)
    List<Post> findPostsByPoint(@Param("point") String point);

//    @Modifying
//    @Query(value = "insert into post (latitude, longitude, location, created_at, modified_at) values (:latitude, :longitude, (st_pointfromtext(:point, 4326)), now(), now())", nativeQuery = true)
//    void savePostWithPoint(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("point") String point);
}