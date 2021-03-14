package com.palilu.mineSweeper.domain.repository;

import com.palilu.mineSweeper.domain.Cell;
import com.palilu.mineSweeper.domain.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author pmendoza
 * @since 2021-03-08
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CellRepositoryIntegrationTest {

    @Autowired
    private CellRepository cellRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    @Transactional
    public void testCreate() {
        Game game = gameRepository.save(Game.builder()
                .rows(2)
                .columns(2)
                .mines(10)
                .build());

        Cell cell = Cell.builder()
                .game(game)
                .rowNumber(0)
                .columnNumber(0)
                .hasFlag(Boolean.FALSE)
                .isVisible(Boolean.FALSE)
                .isMine(Boolean.FALSE)
                .neighbourMines(0)
                .build();

        Cell saved = cellRepository.save(cell);

        assertThat(cell).isEqualToComparingFieldByField(saved);
    }
}
