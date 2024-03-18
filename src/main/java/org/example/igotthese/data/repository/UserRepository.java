package org.example.igotthese.data.repository;

import java.util.List;
import java.util.Optional;
import org.example.igotthese.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByPostId(Long postId);
    @Query(value = "select u from User u join fetch HoldingSeal h on u.id = h.user.id group by u.id order by count(u.id) desc limit 10")
    List<User> findTop10OrderByHoldingSeals();
}