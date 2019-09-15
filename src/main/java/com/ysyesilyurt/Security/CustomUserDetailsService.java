package com.ysyesilyurt.Security;

import com.ysyesilyurt.EntityModel.UserEntityModel;
import com.ysyesilyurt.Enum.UserRole;
import com.ysyesilyurt.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserEntityModel> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(String.format("User not found by name: %s", username));
        }
        return toUserDetails(user.get());
    }

    private UserDetails toUserDetails(UserEntityModel user) {
        /* withDefaultPasswordEncoder hashes the password, creates authority list and returns a valid User */
        return User.withDefaultPasswordEncoder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();

            /* a trick to return all values inside list to a variable parameter function -- using toArray() */
            // .roles(user.getRoles().toArray(new String[user.getRoles().size()]))
    }
}
