package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Long>{

	Optional<UserInfo> findByAccount(String account);

}
