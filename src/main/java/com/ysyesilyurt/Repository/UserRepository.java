package com.ysyesilyurt.Repository;

import com.ysyesilyurt.EntityModel.UserEntityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntityModel, Long> {
    UserEntityModel findByUsername(String username);
}
