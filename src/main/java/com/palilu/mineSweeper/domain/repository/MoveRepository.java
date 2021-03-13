package com.palilu.mineSweeper.domain.repository;

import com.palilu.mineSweeper.domain.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pmendoza
 * @since 2021-03-10
 */
@Repository
public interface MoveRepository extends JpaRepository<Move, Long> {
}
