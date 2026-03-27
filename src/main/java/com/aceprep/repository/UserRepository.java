package com.aceprep.repository;

import com.aceprep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //jpaRepository already have .findById(), .save(), .delete()
    Optional<User> findByEmail(String email);

}
