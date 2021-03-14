package com.palilu.mineSweeper.model;

import com.palilu.mineSweeper.domain.MoveResult;
import com.palilu.mineSweeper.domain.MoveType;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

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
public class MoveResponseAto extends RepresentationModel<GameResponseAto> {

    private Long id;

    private Long gameId;

    private MoveType type;

    private Integer row;

    private Integer column;

    private MoveResult result;

    private BoardResponseAto boardResponseAto;
}
