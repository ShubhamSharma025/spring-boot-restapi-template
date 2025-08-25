package com.example.restapi.repository;

import com.example.restapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
    import java.util.List;
    import java.util.Optional;
 public interface UserRepository  extends JpaRepository<User,Long> {
    
        List<User> findByNameIgnoreCase(String name);

        Optional<User> findByEmail(String email);
    }

