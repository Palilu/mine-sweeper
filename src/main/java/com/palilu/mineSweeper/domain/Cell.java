package com.palilu.mineSweeper.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cell implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private Integer rowNumber;

    @Column(nullable = false)
    private Integer columnNumber;

    @Column(nullable = false)
    private Boolean isMine;

    @Column(nullable = false)
    private Boolean hasFlag;

    @Column(nullable = false)
    private Boolean isVisible;

    @Column(nullable = false)
    private Integer neighbourMines;
}
