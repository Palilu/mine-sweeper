package com.palilu.mineSweeper.controller;

import com.palilu.mineSweeper.model.GameResponseAto;
import com.palilu.mineSweeper.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MineSweeperWebController {

    @Autowired
    private GameService gameService;

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/games/{gameId}")
    public String games(@PathVariable(name = "gameId") Long gameId, Model model) {
        GameResponseAto gameResponseAto = gameService.getGame(gameId);
        model.addAttribute("game", gameResponseAto);
        return "game";
    }
}