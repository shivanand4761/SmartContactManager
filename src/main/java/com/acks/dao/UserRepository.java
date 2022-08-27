package com.acks.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.acks.model.Users;

@Component
public interface UserRepository extends JpaRepository<Users, Integer> {
	
	@Query("select u from Users u where u.email =:email")
	public Users getUsersByUserName(@Param("email") String email);

}
