package com.palilu.mineSweeper.domain.repository;

import com.palilu.mineSweeper.domain.Board;
import com.palilu.mineSweeper.domain.Cell;
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
public class BoardRepositoryIntegrationTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Transactional
    public void testCreate() {
        Board board = Board.builder()
                .rows(10)
                .columns(10)
                .mines(10)
                .build();

        Cell cell1 = cell(board, 0, 0);
        Cell cell2 = cell(board, 0, 1);
        Cell cell3 = cell(board, 1, 0);
        Cell cell4 = cell(board, 1, 1);

        board.setCells(List.of(cell1, cell2, cell3, cell4));

        board = boardRepository.save(board);

        assertThat(board.getId()).isNotNull();
        assertThat(board.getColumns()).isEqualTo(10L);
        assertThat(board.getRows()).isEqualTo(10L);
        assertThat(board.getMines()).isEqualTo(10L);
        List<Cell> cells = board.getCells();
        assertThat(cells).isNotNull();
        assertThat(cells.size()).isEqualTo(4);
        assertThat(cells.get(0)).isEqualToComparingFieldByField(cell1);
        assertThat(cells.get(1)).isEqualToComparingFieldByField(cell2);
        assertThat(cells.get(2)).isEqualToComparingFieldByField(cell3);
        assertThat(cells.get(3)).isEqualToComparingFieldByField(cell4);
    }

    private Cell cell(Board board, Integer row, Integer column) {
        return Cell.builder()
                .board(board)
                .rowNumber(row)
                .columnNumber(column)
                .hasFlag(Boolean.FALSE)
                .isVisible(Boolean.FALSE)
                .isMine(Boolean.FALSE)
                .neighbourMines(0)
                .build();
    }
}
