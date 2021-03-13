package com.palilu.mineSweeper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
public class GameResponseAto {

    private Long id;

    private Integer rows;

    private Integer columns;

    private Integer mines;
}
