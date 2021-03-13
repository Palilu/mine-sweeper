package com.palilu.mineSweeper.domain.repository;

import com.palilu.mineSweeper.domain.*;
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
public class MoveRepositoryIntegrationTest {

    @Autowired
    private MoveRepository moveRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Transactional
    public void testCreate() {
        Board board = boardRepository.save(Board.builder()
                .rows(10)
                .columns(10)
                .mines(10)
                .build());
        Game game = Game.builder()
                .board(board)
                .build();
        Cell cell = Cell.builder()
                .board(board)
                .rowNumber(0)
                .columnNumber(0)
                .hasFlag(Boolean.FALSE)
                .isVisible(Boolean.FALSE)
                .isMine(Boolean.FALSE)
                .neighbourMines(0)
                .build();
        Move move = Move.builder()
                .game(game)
                .cell(cell)
                .type(MoveType.CHECK_MINE)
                .result(MoveResult.CELL_CLEARED)
                .build();

        Move saved = moveRepository.save(move);

        assertThat(move).isEqualToComparingFieldByField(saved);
    }
}
