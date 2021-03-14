package com.palilu.mineSweeper.controller;

import com.palilu.mineSweeper.model.*;
import com.palilu.mineSweeper.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * A controller for mine sweeper API.
 *
 * @author pmendoza
 * @since 2021-03-08
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/games/")
public class MineSweeperAPIController {

    @Autowired
    private GameService gameService;

    /**
     * A controller for mine sweeper API.
     *
     * @author pmendoza
     * @since 2021-03-08
     */
    @PostMapping
    public GameResponseAto postGame(@RequestBody @Valid GameRequestAto gameRequest) {
        GameResponseAto response = gameService.createGame(gameRequest.getRows(), gameRequest.getColumns(), gameRequest.getMines());
        return response;

    }

    @GetMapping("{gameId}")
    public GameResponseAto getBoard(@PathVariable("gameId") Long gameId) {
        return gameService.getGame(gameId);
    }

    @PostMapping("{gameId}/moves")
    public MoveResponseAto postMove(@PathVariable("gameId") Long gameId,
                                    @RequestBody @Valid MoveRequestAto moveRequest) {
        MoveResponseAto response = gameService.makeMove(gameId, moveRequest.getType(), moveRequest.getRow(), moveRequest.getColumn());
        return response;
    }
}
