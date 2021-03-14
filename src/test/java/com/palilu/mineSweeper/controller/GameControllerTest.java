package com.palilu.mineSweeper.controller;

import com.palilu.mineSweeper.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author pmendoza
 * @since 2021-03-08
 */
@SpringBootTest(classes = {MineSweeperController.class})
public class GameControllerTest {

    private static final String DATA = "data";

    @Autowired
    private MineSweeperController mineSweeperController;

    @MockBean
    private GameService diffService;


}
