package com.group.petSitter.domain.user.repository;

import com.group.petSitter.domain.user.User;
import com.group.petSitter.domain.user.UserGrade;
import com.group.petSitter.domain.user.repository.response.UserOrderCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProviderAndProviderId(String provider, String providerId);

   /* @Query("select o.user.userId as userId, count(o) as orderCount from Order o"
            + " where o.createdAt between :start and :end"
            + " group by o.user.userId")
    List<UserOrderCount> getUserOrderCount(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);*/

    @Modifying
    @Query("update User u set u.userGrade = :userGrade"
            + " where u.userId in :userIds")
    void updateUserGrade(
            @Param("userGrade") UserGrade userGrade,
            @Param("userIds") List<Long> userIds);
}
