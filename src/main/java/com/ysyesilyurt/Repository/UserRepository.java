package com.ysyesilyurt.Repository;

import com.ysyesilyurt.EntityModel.UserEntityModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntityModel, Long> {
    Optional<UserEntityModel> findByUsername(String username);
}
