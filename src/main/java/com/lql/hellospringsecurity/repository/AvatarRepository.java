package com.lql.hellospringsecurity.repository;

import com.lql.hellospringsecurity.model.AvatarImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<AvatarImage, Long> {
}
