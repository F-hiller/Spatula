package com.ovg.spatula.repository;

import com.ovg.spatula.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByDeviceId(String deviceId);
}