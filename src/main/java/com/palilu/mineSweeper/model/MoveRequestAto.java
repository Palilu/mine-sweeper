package com.palilu.mineSweeper.model;

import com.palilu.mineSweeper.domain.MoveType;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Move request API transfer object.
 *
 * @author pmendoza
 * @since 2021-03-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveRequestAto {

    @NotNull
    private MoveType type;

    @NotNull
    @Min(value = 0, message = "Row has to be a positive integer.")
    private Integer row;

    @NotNull
    @Min(value = 0, message = "Column has to be a positive integer.")
    private Integer column;
}
