package com.palilu.mineSweeper.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private LocalDateTime createDate;

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

    @PrePersist
    public void onCreate() {
        this.createDate = LocalDateTime.now();
    }
}
