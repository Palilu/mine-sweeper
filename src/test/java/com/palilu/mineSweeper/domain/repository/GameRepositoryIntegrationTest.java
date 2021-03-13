package com.palilu.mineSweeper.domain.repository;

import com.palilu.mineSweeper.domain.Board;
import com.palilu.mineSweeper.domain.Game;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author pmendoza
 * @since 2021-03-08
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameRepositoryIntegrationTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    @Transactional
    public void testCreate() {
        Board board = Board.builder()
                .rows(10)
                .columns(10)
                .mines(10)
                .build();
        Game game = Game.builder()
                .board(board)
                .build();

        game = gameRepository.save(game);

        assertThat(game.getId()).isNotNull();
        assertThat(board).isEqualToComparingFieldByField(game.getBoard());
    }
}
