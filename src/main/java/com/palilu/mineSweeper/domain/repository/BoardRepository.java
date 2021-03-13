package com.palilu.mineSweeper.domain.repository;

import com.palilu.mineSweeper.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author pmendoza
 * @since 2021-03-08
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
