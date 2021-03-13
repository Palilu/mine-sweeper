package com.palilu.mineSweeper.model;

import lombok.*;

/**
 * Board response API transfer object.
 *
 * @author pmendoza
 * @since 2021-03-13
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseAto {

    private String[] cells;
}
