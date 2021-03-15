package com.palilu.mineSweeper.service;

import com.palilu.mineSweeper.domain.*;
import com.palilu.mineSweeper.domain.repository.CellRepository;
import com.palilu.mineSweeper.domain.repository.GameRepository;

import com.palilu.mineSweeper.domain.repository.MoveRepository;
import com.palilu.mineSweeper.model.GameResponseAto;

import com.palilu.mineSweeper.model.MoveResponseAto;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for GameServiceImpl.
 *
 * @author pmendoza
 * @since 2021-03-08
 */
@SpringBootTest(classes = {GameServiceImpl.class})
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private CellRepository cellRepository;

    @MockBean
    private MoveRepository moveRepository;

    @SpyBean
    private MineSweeperMapper mapper;

    @Captor
    private ArgumentCaptor<Game> gameArgumentCaptor;

    @Test
    public void createGame() {
        when(gameRepository.save(Mockito.any(Game.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        GameResponseAto result = gameService.createGame(3, 2, 1);

        assertThat(result.getRows()).isEqualTo(3);
        assertThat(result.getColumns()).isEqualTo(2);
        assertThat(result.getMines()).isEqualTo(1);

        verify(gameRepository, times(1)).save(gameArgumentCaptor.capture());
        Game saved = gameArgumentCaptor.getValue();
        assertThat(saved).isNotNull();
        assertThat(saved.getCells()).isNotNull();
        assertThat(saved.getCells().size()).isEqualTo(6);
        assertThat(saved.getCells().stream().filter(Cell::getIsMine).count()).isEqualTo(1);
        assertThat(saved.getCells().stream().filter(Cell::getIsVisible).count()).isEqualTo(0);
        assertThat(saved.getCells().stream().filter(Cell::getHasFlag).count()).isEqualTo(0);
    }

    @Test
    public void makeMove() {
        Game game = game();
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(moveRepository.save(Mockito.any(Move.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(cellRepository.save(Mockito.any(Cell.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        MoveResponseAto move1 = gameService.makeMove(1L, MoveType.PUT_FLAG, 0, 0);

        assertThat(move1.getRow()).isEqualTo(0);
        assertThat(move1.getColumn()).isEqualTo(0);
        assertThat(move1.getResult()).isEqualTo(MoveResult.FLAG_ADDED);
        assertThat(move1.getType()).isEqualTo(MoveType.PUT_FLAG);

        MoveResponseAto move2 = gameService.makeMove(1L, MoveType.PUT_FLAG, 0, 0);

        assertThat(move2.getRow()).isEqualTo(0);
        assertThat(move2.getColumn()).isEqualTo(0);
        assertThat(move2.getResult()).isEqualTo(MoveResult.FLAG_REMOVED);
        assertThat(move2.getType()).isEqualTo(MoveType.PUT_FLAG);

        MoveResponseAto move3 = gameService.makeMove(1L, MoveType.CHECK_MINE, 0, 0);

        assertThat(move3.getRow()).isEqualTo(0);
        assertThat(move3.getColumn()).isEqualTo(0);
        assertThat(move3.getResult()).isEqualTo(MoveResult.CELL_CLEARED);
        assertThat(move3.getType()).isEqualTo(MoveType.CHECK_MINE);

        MoveResponseAto move4 = gameService.makeMove(1L, MoveType.CHECK_MINE, 1, 1);

        assertThat(move4.getRow()).isEqualTo(1);
        assertThat(move4.getColumn()).isEqualTo(1);
        assertThat(move4.getResult()).isEqualTo(MoveResult.GAME_LOST);
        assertThat(move4.getType()).isEqualTo(MoveType.CHECK_MINE);

        MoveResponseAto move5 = gameService.makeMove(1L, MoveType.CHECK_MINE, 0, 1);

        assertThat(move5.getRow()).isEqualTo(0);
        assertThat(move5.getColumn()).isEqualTo(1);
        assertThat(move5.getResult()).isEqualTo(MoveResult.CELL_CLEARED);
        assertThat(move5.getType()).isEqualTo(MoveType.CHECK_MINE);

        MoveResponseAto move6 = gameService.makeMove(1L, MoveType.CHECK_MINE, 1, 0);

        assertThat(move6.getRow()).isEqualTo(1);
        assertThat(move6.getColumn()).isEqualTo(0);
        assertThat(move6.getResult()).isEqualTo(MoveResult.GAME_WON);
        assertThat(move6.getType()).isEqualTo(MoveType.CHECK_MINE);
    }

    private Game game() {
        Cell cell1 = cell(0, 0, Boolean.FALSE);
        Cell cell2 = cell(0, 1, Boolean.FALSE);
        Cell cell3 = cell(1, 0, Boolean.FALSE);
        Cell cell4 = cell(1, 1, Boolean.TRUE);
        return Game.builder()
                .rows(2)
                .columns(2)
                .mines(1)
                .cells(List.of(cell1, cell2, cell3, cell4))
                .build();
    }

    private Cell cell(Integer rowNumber, Integer columnNumber, Boolean isMine) {
        Cell cell =  Cell.builder()
                .isMine(isMine)
                .isVisible(Boolean.FALSE)
                .hasFlag(Boolean.FALSE)
                .columnNumber(columnNumber)
                .rowNumber(rowNumber)
                .neighbourMines(1)
                .build();
        when(cellRepository.findByGameAndRowNumberAndColumnNumber(any(), eq(rowNumber), eq(columnNumber)))
                .thenReturn(Optional.of(cell));
        return cell;
    }
}
