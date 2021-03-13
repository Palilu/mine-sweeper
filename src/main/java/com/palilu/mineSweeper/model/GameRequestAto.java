package com.palilu.mineSweeper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Game request API transfer object.
 *
 * @author pmendoza
 * @since 2021-03-08
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@MineSweeperGame
public class GameRequestAto {

    @NotNull
    @Min(value = 1, message = "The game must have at least 1 row.")
    private Integer rows;

    @NotNull
    @Min(value = 1, message = "The game must have at least 1 column.")
    private Integer columns;

    @NotNull
    @Min(value = 1, message = "The game must have at least 1 mine.")
    private Integer mines;
}
