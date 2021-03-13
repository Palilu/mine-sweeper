package com.palilu.mineSweeper.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author pmendoza
 * @since 2021-03-10
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Move implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cell_id", referencedColumnName = "id", nullable = false)
    private Cell cell;

    @Column(nullable = false)
    private MoveType type;

    @Column(nullable = false)
    private MoveResult result;
}
