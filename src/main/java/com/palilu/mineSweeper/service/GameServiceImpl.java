package com.palilu.mineSweeper.service;

import com.palilu.mineSweeper.domain.*;
import com.palilu.mineSweeper.domain.repository.CellRepository;
import com.palilu.mineSweeper.domain.repository.GameRepository;
import com.palilu.mineSweeper.domain.repository.MoveRepository;
import com.palilu.mineSweeper.exceptions.GameNotFoundException;
import com.palilu.mineSweeper.exceptions.InvalidPositionException;
import com.palilu.mineSweeper.model.GameResponseAto;
import com.palilu.mineSweeper.model.MoveResponseAto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import static java.util.function.Predicate.not;

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
    private MoveRepository moveRepository;

    @Autowired
    private MineSweeperMapper mapper;

    @Override
    @Transactional
    public GameResponseAto createGame(Integer rows, Integer columns, Integer mines) {
        log.info("Saving game for rows={}, columns={}, mines={}", rows, columns, mines);
        Game game = gameRepository.save(Game.builder()
                .status(GameStatus.ONGOING)
                .rows(rows)
                .columns(columns)
                .mines(mines)
                .cells(new ArrayList<>())
                .build());
        Cell[][] boardState = createCells(game, rows, columns);
        fillMines(boardState, rows, columns, mines);
        log.info("Successfully saved game for gameId={}", game.getId());

        return mapper.gameAto(game);
    }

    /**
     * Creates the cells for a game
     *
     * @param game The game.
     * @param rows The number of rows.
     * @param columns The number of columns.
     */
    private Cell[][] createCells(Game game, Integer rows, Integer columns) {
        Cell[][] board = new Cell[rows][columns];
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                Cell cell = Cell.builder()
                        .game(game)
                        .rowNumber(row)
                        .columnNumber(column)
                        .isMine(Boolean.FALSE)
                        .hasFlag(Boolean.FALSE)
                        .isVisible(Boolean.FALSE)
                        .neighbourMines(0)
                        .build();
                board[row][column] = cell;
                game.getCells().add(cell);
            }
        }
        return board;
    }

    /**
     * Fills a number of mines on board.
     *
     * @param board The board.
     * @param rows The number of rows.
     * @param columns The number of columns.
     * @param mines The number of mines.
     */
    private void fillMines(Cell[][] board, Integer rows, Integer columns, Integer mines) {
        for (int i = 0; i < mines; i++) {
            putRandomMine(board, rows, columns);
        }
    }

    /**
     * Puts a mine on a random location on a board.
     *
     * @param board The board.
     * @param rows The number of rows.
     * @param columns The number of columns.
     */
    private void putRandomMine(Cell[][] board, Integer rows, Integer columns) {
        // Get a random position to place the mine
        Random random = new Random();
        int row;
        int column;
        do {
            column = random.nextInt(columns);
            row = random.nextInt(rows);
        } while (board[row][column].getIsMine()); // Check that there's not mine already there.
        // Put a mine on the position
        board[row][column].setIsMine(Boolean.TRUE);
        // Add +1 mine on it's neighbours
        for (int r = Math.max(0, row - 1); r <= Math.min(row + 1, rows - 1); r ++) {
            for (int c = Math.max(0, column - 1); c <= Math.min(column + 1, columns - 1); c ++) {
                board[r][c].setNeighbourMines(board[r][c].getNeighbourMines() + 1);
            }
        }
    }

    @Override
    @Transactional
    public MoveResponseAto makeMove(Long gameId,
                                    MoveType type,
                                    Integer row,
                                    Integer column) {
        log.info("Making move for gameId={}, type={}, row={}, column={}.", gameId, type, row, column);
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found for gameId={}.", gameId));
        Cell cell = cellRepository.findByGameAndRowNumberAndColumnNumber(game, row, column)
                .orElseThrow(() -> new InvalidPositionException("Invalid position for row={}, column={}", row, column));
        Move move = this.createMove(game, cell, type);
        log.info("Successfully made move for gameId={}, type={}, row={}, column={}.", gameId, type, row, column);
        return mapper.moveAto(move);
    }

    /**
     * Performs a move on a game. This can be either
     *
     * @param game The game.
     * @param cell The cell.
     * @param type The move type, either checking a mine or toggling a flag.
     */
    public Move createMove(Game game, Cell cell, MoveType type) {
        MoveResult result = null;
        switch (type) {
            case PUT_FLAG:
                if (cell.getHasFlag()) {
                    cell.setHasFlag(Boolean.FALSE);
                    result = MoveResult.FLAG_REMOVED;
                } else {
                    cell.setHasFlag(Boolean.TRUE);
                    result = MoveResult.FLAG_ADDED;
                }
                break;
            case CHECK_MINE:
                if (cell.getIsMine()) {
                    showAllMines(game);
                    result = MoveResult.GAME_LOST;
                    game.setStatus(GameStatus.LOST);
                    game.setEndDate(LocalDateTime.now());
                    gameRepository.save(game);
                } else {
                    clearCell(board(game), cell.getRowNumber(), cell.getColumnNumber());
                    result = gameWon(game) ? MoveResult.GAME_WON : MoveResult.CELL_CLEARED;
                }
                break;
        }

        cell = cellRepository.save(cell);

        return moveRepository.save(Move.builder()
                .game(game)
                .cell(cell)
                .type(type)
                .result(result)
                .build());
    }

    /**
     * Returns true when the game has been won.
     * A game is won when every cell either is visible or contains a mine.
     *
     * @param game The game.
     */
    private boolean gameWon(Game game) {
        boolean gameWon = game.getCells().stream()
                .filter(not(Cell::getIsMine))
                .filter(not(Cell::getIsVisible))
                .count() == 0;
        if (gameWon) {
            game.setStatus(GameStatus.WON);
            game.setEndDate(LocalDateTime.now());
        }
        return gameWon;
    }

    /**
     * Clears a cell. If the cell has 0 mines around, this also means clearing all the
     * empty space around it.
     *
     * @param board The board.
     * @param row The row number.
     * @param column The columnNumber.
     */
    private void clearCell(Cell[][] board,
                           Integer row,
                           Integer column) {

        Cell cell = board[row][column];
        cell.setIsVisible(Boolean.TRUE);
        cellRepository.save(cell);
        if (board[row][column].getNeighbourMines() != 0) {
            return;
        }
        // In the square around the cell, checking that I do not go overboard.
        for (int r = Math.max(0, row - 1); r <= Math.min(row + 1, board.length - 1); r++) {
            for (int c = Math.max(0, column - 1); c <= Math.min(column + 1, board[0].length - 1); c++) {
                // And skipping the cell itself
                if (!(row == r && column == c) && !board[r][c].getIsVisible()) {
                    // I keep clearing
                    clearCell(board, r, c);
                }
            }
        }
    }

    /**
     * Returns the cells of a game as a matrix.
     *
     * @param game The game.
     */
    private Cell[][] board(Game game) {
        Cell[][] board = new Cell[game.getRows()][game.getColumns()];
        game.getCells().forEach(cell -> board[cell.getRowNumber()][cell.getColumnNumber()] = cell);
        return board;
    }

    /**
     * Sets all the mines of a game as visible.
     *
     * @param game The game.
     */
    private void showAllMines(Game game) {
        game.getCells().stream()
                .filter(Cell::getIsMine)
                .forEach(this::setVisible);
    }

    /**
     * Sets a cell as visible.
     *
     * @param cell The cell.
     */
    private void setVisible(Cell cell) {
        cell.setIsVisible(Boolean.TRUE);
        cellRepository.save(cell);
    }

    @Override
    @Transactional(readOnly = true)
    public GameResponseAto getGame(Long gameId) {
        log.info("Retrieving game for gameId={}.", gameId);
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException("Game not found for gameId={}.", gameId));
        log.info("Successfully retrieved game for gameId={}.", gameId);
        return mapper.gameAto(game);
    }
}
