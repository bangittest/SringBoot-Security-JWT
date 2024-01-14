package com.ra.repository;

import com.ra.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
    User findByUserName(String userName);
    User findByEmail(String email);
    User findByToken(String token);
    @Transactional
    @Modifying
    @Query("UPDATE User c SET c.password = :password WHERE c.id = :id")
    void updateUserPassword(@Param("id") Long id,@Param("password") String password);
}
