package com.xyvo.defi.repository.api;

import com.xyvo.defi.domain.profile.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Modifying
    @Query(value = "update profile.user u set u.USER_NAME = :username where u.id = :id", nativeQuery = true)
    void update(@Param("id") long id, @Param("username") String userName);

    @Query(value = "select id, user_name, created, updated from profile.user where user_name = :username", nativeQuery = true)
    User findByName(@Param("username") String userName);
}
