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
     * Creates a game
     *
     * @param length The game's board length.
     * @param width The game's board width.
     * @param mines The number of mines in the board;
     */
    GameResponseAto createGame(Integer length, Integer width, Integer mines);

    /**
     * makes a move a un mine sweeper game
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
