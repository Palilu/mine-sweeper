package com.palilu.mineSweeper.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author pmendoza
 * @since 2021-03-13
 */
public class MineSweeperGameValidator implements ConstraintValidator<MineSweeperGame, GameRequestAto> {

    @Override
    public boolean isValid(GameRequestAto gameRequest,
                           ConstraintValidatorContext cxt) {
        return gameRequest.getMines() <= gameRequest.getRows() * gameRequest.getColumns();
    }

}