package com.programmerpagi.toko_online.repository;

import com.programmerpagi.toko_online.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
