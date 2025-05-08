package com.cns.security.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cns.security.entity.OurUser;

@Repository
public interface UserRepository extends JpaRepository<OurUser, UUID> {

	@Query("SELECT u FROM OurUser u WHERE u.userName = :userName")
	Optional<OurUser> findByUserName(@Param("userName") String userName);

	@Query("SELECT u FROM OurUser u WHERE u.email = :email")
	Optional<OurUser> findByEmail(@Param("email") String email);
}
