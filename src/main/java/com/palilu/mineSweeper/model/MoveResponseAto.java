package com.palilu.mineSweeper.model;

import com.palilu.mineSweeper.domain.MoveResult;
import com.palilu.mineSweeper.domain.MoveType;
import lombok.*;

/**
 * Move response API transfer object.
 *
 * @author pmendoza
 * @since 2021-03-10
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoveResponseAto {

    private Long id;

    private MoveType type;

    private Integer row;

    private Integer column;

    private MoveResult result;

    private BoardResponseAto boardResponseAto;
}
