package com.palilu.mineSweeper.domain.repository;

import com.palilu.mineSweeper.domain.Board;
import com.palilu.mineSweeper.domain.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author pmendoza
 * @since 2021-03-08
 */
@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {

    Optional<Cell> findByBoardAndRowNumberAndColumnNumber(Board board,
                                                          Integer rowNumber,
                                                          Integer columnNumber);
}
