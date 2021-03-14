package com.palilu.mineSweeper.service;

import com.palilu.mineSweeper.domain.MoveType;
import com.palilu.mineSweeper.model.BoardResponseAto;
import com.palilu.mineSweeper.model.GameResponseAto;
import com.palilu.mineSweeper.model.MoveResponseAto;

/**
 * @author pmendoza
 * @since 2021-03-08
 */
public interface GameService {

    /**
     * Creates a game for the given parameters.
     *
     * @param rows The number of rows for the game.
     * @param columns The number of columns for the game.
     * @param mines The number of mines in the board;
     */
    GameResponseAto createGame(Integer rows, Integer columns, Integer mines);

    /**
     * Makes a move in a mine sweeper game
     *
     * @param gameId The game's ID.
     * @param type The move's type.
     * @param row The move's row.
     * @param column The move's column;
     */
    MoveResponseAto makeMove(Long gameId, MoveType type, Integer row, Integer column);

    /**
     * Returns the state of the board.
     *
     * @param gameId The game's ID.
     */
    BoardResponseAto getBoard(Long gameId);
}
