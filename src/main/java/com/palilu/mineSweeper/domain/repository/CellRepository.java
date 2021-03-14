package com.palilu.mineSweeper.domain.repository;

import com.palilu.mineSweeper.domain.Cell;
import com.palilu.mineSweeper.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author pmendoza
 * @since 2021-03-08
 */
@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {

    Optional<Cell> findByGameAndRowNumberAndColumnNumber(Game game,
                                                         Integer rowNumber,
                                                         Integer columnNumber);
}
