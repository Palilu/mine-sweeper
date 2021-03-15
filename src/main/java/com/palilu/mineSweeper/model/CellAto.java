package com.palilu.mineSweeper.model;

import lombok.*;

/**
 * Cell response API transfer object.
 *
 * @author pmendoza
 * @since 2021-03-13
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CellAto {

    private Integer row;

    private Integer column;

    private String value;
}
