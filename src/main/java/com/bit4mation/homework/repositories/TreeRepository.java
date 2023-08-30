package com.bit4mation.homework.repositories;

import com.bit4mation.homework.models.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long> {
}