package com.palilu.mineSweeper.model;

import com.palilu.mineSweeper.domain.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

/**
 * Game response API transfer object.
 *
 * @author pmendoza
 * @since 2021-03-08
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class GameResponseAto extends RepresentationModel<GameResponseAto> {

    private Long id;

    private LocalDateTime createDate;

    private GameStatus status;

    private Integer rows;

    private Integer columns;

    private Integer mines;

    private LocalDateTime endDate;

    private CellAto[][] cells;
}
