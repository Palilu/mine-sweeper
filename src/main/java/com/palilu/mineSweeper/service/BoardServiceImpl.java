package com.palilu.mineSweeper.service;

import com.palilu.mineSweeper.domain.*;
import com.palilu.mineSweeper.domain.repository.BoardRepository;
import com.palilu.mineSweeper.domain.repository.CellRepository;
import com.palilu.mineSweeper.domain.repository.MoveRepository;
import com.palilu.mineSweeper.model.BoardResponseAto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;


/**
 * @author pmendoza
 * @since 2021-03-09
 */
@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CellRepository cellRepository;

    @Autowired
    private MoveRepository moveRepository;

    public Board createBoard(Integer columns, Integer rows, Integer mines) {
        Board board = Board.builder()
                .columns(columns)
                .rows(rows)
                .mines(mines)
                .cells(new ArrayList<>())
                .build();
        Cell[][] boardState = createCells(board, columns, rows);
        fillMines(boardState, columns, rows, mines);

        return boardRepository.save(board);
    }

    private Cell[][] createCells(Board board, Integer columns, Integer rows) {
        Cell[][] boardState = new Cell[rows][columns];
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                Cell cell = Cell.builder()
                        .board(board)
                        .rowNumber(row)
                        .columnNumber(column)
                        .isMine(Boolean.FALSE)
                        .hasFlag(Boolean.FALSE)
                        .isVisible(Boolean.FALSE)
                        .neighbourMines(0)
                        .build();
                boardState[row][column] = cell;
                board.getCells().add(cell);
            }
        }
        return boardState;
    }

    private void fillMines(Cell[][] boardState, Integer length, Integer width, Integer mines) {
        for (int i = 0; i < mines; i++) {
            putRandomMine(boardState, length, width);
        }
    }

    private void putRandomMine(Cell[][] boardState, Integer columns, Integer rows) {
        // Get a random position to place the mine
        Random random = new Random();
        int row;
        int column;
        do {
            column = random.nextInt(columns);
            row = random.nextInt(rows);
        } while (boardState[row][column].getIsMine()); // Check that there's not mine already there.
        // Put a mine on the position
        boardState[row][column].setIsMine(Boolean.TRUE);
        // Add +1 mine on it's neighbours
        for (int r = Math.max(0, row - 1); r <= Math.min(row + 1, rows - 1); r ++) {
            for (int c = Math.max(0, column - 1); c <= Math.min(column + 1, columns - 1); c ++) {
                boardState[r][c].setNeighbourMines(boardState[r][c].getNeighbourMines() + 1);
            }
        }
    }

    @Override
    public BoardResponseAto getBoard(Game game) {
        Board board = game.getBoard();
        String[][] cellsView = new String[board.getRows()][board.getColumns()];
        board.getCells().forEach(cell -> putCell(cellsView, cell));
        String[] flatView = flatView(cellsView);
        return BoardResponseAto.builder()
                .cells(flatView)
                .build();
    }

    private String[] flatView(String[][] cellsView) {
        String[] flatView = new String[cellsView.length];
        for (int i = 0; i < cellsView.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 0; j < cellsView[0].length; j++) {
                stringBuilder.append(cellsView[i][j]);
            }
            flatView[i] = stringBuilder.toString();
        }
        return flatView;
    }

    private void putCell(String[][] cellsView, Cell cell) {
        cellsView[cell.getRowNumber()][cell.getColumnNumber()] = getView(cell);
    }

    private String getView(Cell cell) {
        if (cell.getIsVisible()) {
            if (cell.getIsMine()) {
                return "M";
            } else if (cell.getNeighbourMines() == 0) {
                return " ";
            } else {
                return cell.getNeighbourMines().toString();
            }
        } else {
            if (cell.getHasFlag()) {
                return "F";
            } else {
                return "▢";
            }
        }
    }

    @Override
    public Move createMove(Game game, Cell cell, MoveType type) {
        MoveResult result = null;
        Board board = game.getBoard();
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
                    showAllMines(board);
                    result = MoveResult.GAME_LOST;
                } else {
                    clearCell(boardView(board), cell.getRowNumber(), cell.getColumnNumber());
                    result = gameWon(board.getCells()) ? MoveResult.GAME_WON : MoveResult.CELL_CLEARED;
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
     */
    private boolean gameWon(List<Cell> cells) {
        return cells.stream()
                .filter(not(Cell::getIsMine))
                .filter(not(Cell::getIsVisible))
                .count() == 0;
    }

    private void clearCell(Cell[][] boardView,
                           Integer rowNumber,
                           Integer columnNumber) {

        Cell cell = boardView[rowNumber][columnNumber];
        cell.setIsVisible(Boolean.TRUE);
        cellRepository.save(cell);
        if (boardView[rowNumber][columnNumber].getNeighbourMines() != 0) {
            return;
        }
        for (int r = Math.max(0, rowNumber - 1); r <= Math.min(rowNumber + 1, boardView.length - 1); r++) {
            for (int c = Math.max(0, columnNumber - 1); c <= Math.min(columnNumber + 1, boardView[0].length - 1); c++) {
                if (!(rowNumber == r && columnNumber == c) && !boardView[r][c].getIsVisible()) {
                    clearCell(boardView, r, c);
                }
            }
        }
    }

    private Cell[][] boardView(Board board) {
        Cell[][] cellsView = new Cell[board.getRows()][board.getColumns()];
        board.getCells().forEach(cell -> cellsView[cell.getRowNumber()][cell.getColumnNumber()] = cell);
        return cellsView;
    }

    private void showAllMines(Board board) {
        board.getCells().stream()
                .filter(Cell::getIsMine)
                .forEach(this::setVisible);
    }

    private void setVisible(Cell cell) {
        cell.setIsVisible(Boolean.TRUE);
        cellRepository.save(cell);
    }
}
