package com.palilu.mineSweeper.domain.repository;

import com.palilu.mineSweeper.domain.Cell;
import com.palilu.mineSweeper.domain.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author pmendoza
 * @since 2021-03-08
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameRepositoryIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    @Transactional
    public void testCreate() {
        Game game = Game.builder()
                .rows(10)
                .columns(10)
                .mines(10)
                .build();

        Cell cell1 = cell(game, 0, 0);
        Cell cell2 = cell(game, 0, 1);
        Cell cell3 = cell(game, 1, 0);
        Cell cell4 = cell(game, 1, 1);
        game.setCells(List.of(cell1, cell2, cell3, cell4));

        Game saved = gameRepository.save(game);

        assertThat(game.getId()).isNotNull();
        List<Cell> cells = game.getCells();
        assertThat(cells).isNotNull();
        assertThat(cells.size()).isEqualTo(4);
        assertThat(cells.get(0)).isEqualToComparingFieldByField(cell1);
        assertThat(cells.get(1)).isEqualToComparingFieldByField(cell2);
        assertThat(cells.get(2)).isEqualToComparingFieldByField(cell3);
        assertThat(cells.get(3)).isEqualToComparingFieldByField(cell4);
        assertThat(saved).isEqualToComparingFieldByField(game);
    }

    private Cell cell(Game game, Integer row, Integer column) {
        return Cell.builder()
                .game(game)
                .rowNumber(row)
                .columnNumber(column)
                .hasFlag(Boolean.FALSE)
                .isVisible(Boolean.FALSE)
                .isMine(Boolean.FALSE)
                .neighbourMines(0)
                .build();
    }
}
