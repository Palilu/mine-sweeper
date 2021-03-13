package com.palilu.mineSweeper.service;

import com.palilu.mineSweeper.domain.*;
import com.palilu.mineSweeper.model.BoardResponseAto;

public interface BoardService {

    /**
     * Creates a board of columns x rows size, puts random mines on it and
     * calculates the amount of neighbor mines on each cell.
     *
     * @param rows The board's number of rows.
     * @param columns The board's number of columns.
     * @param mines The number of mines in the board;
     */
    Board createBoard(Integer columns, Integer rows, Integer mines);

    /**
     * Creates a move.
     *
     * @param game The game.
     * @param cell The cell.
     * @param type The move's type.
     */
    Move createMove(Game game, Cell cell, MoveType type);

    /**
     * Returns the board state of a game passed as parameter.
     *
     * @param game The game.
     */
    BoardResponseAto getBoard(Game game);
}
