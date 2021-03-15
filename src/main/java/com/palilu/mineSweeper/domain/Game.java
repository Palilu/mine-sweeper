package com.palilu.mineSweeper.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author pmendoza
 * @since 2021-03-08
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private GameStatus status;

    @Column(nullable = false)
    private Integer columns;

    @Column(nullable = false)
    private Integer rows;

    @Column(nullable = false)
    private Integer mines;

    @Column
    private LocalDateTime endDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game", cascade = CascadeType.ALL)
    private List<Cell> cells;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game")
    private List<Move> moves;

    @PrePersist
    public void onCreate() {
        this.createDate = LocalDateTime.now();
    }
}
