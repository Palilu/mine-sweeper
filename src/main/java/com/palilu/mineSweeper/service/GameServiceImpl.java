package com.palilu.mineSweeper.service;

import com.palilu.mineSweeper.domain.*;
import com.palilu.mineSweeper.domain.repository.CellRepository;
import com.palilu.mineSweeper.domain.repository.GameRepository;

import com.palilu.mineSweeper.exceptions.GameNotFoundException;
import com.palilu.mineSweeper.exceptions.InvalidPositionException;
import com.palilu.mineSweeper.model.BoardResponseAto;
import com.palilu.mineSweeper.model.GameResponseAto;
import com.palilu.mineSweeper.model.MoveResponseAto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Game service implementation.
 *
 * @author pmendoza
 * @since 2021-03-08
 */
@Service
@Slf4j
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CellRepository cellRepository;

    @Autowired
    private BoardService boardService;

    @Override
    @Transactional
    public GameResponseAto createGame(Integer rows, Integer columns, Integer mines) {
        log.info("Saving game for rows={}, columns={}, mines={}", rows, columns, mines);
        Game game = gameRepository.save(Game.builder()
                .board(boardService.createBoard(columns, rows, mines))
                .build());
        log.info("Successfully saved game for gameId={}", game.getId());

        return GameResponseAto.builder()
                .id(game.getId())
                .rows(rows)
                .columns(columns)
                .mines(mines)
                .build();
    }

    @Override
    @Transactional
    public MoveResponseAto makeMove(Long gameId,
                                    MoveType type,
                                    Integer row,
                                    Integer column) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found for gameId={}.", gameId));

        Board board = game.getBoard();

        Cell cell = cellRepository.findByBoardAndRowNumberAndColumnNumber(board, row, column)
                .orElseThrow(() -> new InvalidPositionException("Invalid position for row={}, column={}", row, column));

        Move move = boardService.createMove(game, cell, type);

        return MoveResponseAto.builder()
                .id(move.getId())
                .result(move.getResult())
                .row(row)
                .column(column)
                .type(type)
                .boardResponseAto(boardService.getBoard(game))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponseAto getBoard(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found for gameId={}.", gameId));
        return boardService.getBoard(game);
    }
}
