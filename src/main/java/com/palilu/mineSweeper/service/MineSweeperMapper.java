package com.palilu.mineSweeper.service;

import com.palilu.mineSweeper.domain.Cell;
import com.palilu.mineSweeper.domain.Game;
import com.palilu.mineSweeper.domain.Move;
import com.palilu.mineSweeper.model.CellAto;
import com.palilu.mineSweeper.model.GameResponseAto;
import com.palilu.mineSweeper.model.MoveResponseAto;
import org.springframework.stereotype.Component;

@Component
public class MineSweeperMapper {

    /**
     * Maps a game to a GameResponseAto.
     *
     * @param game The game.
     */
    public GameResponseAto gameAto(Game game) {
        CellAto[][] cells = new CellAto[game.getRows()][game.getColumns()];
        game.getCells()
                .forEach(cell -> cells[cell.getRowNumber()][cell.getColumnNumber()] = cellAto(cell));
        return GameResponseAto.builder()
                .id(game.getId())
                .createDate(game.getCreateDate())
                .status(game.getStatus())
                .rows(game.getRows())
                .columns(game.getColumns())
                .mines(game.getMines())
                .endDate(game.getEndDate())
                .cells(cells)
                .build();
    }

    /**
     * Maps a cell to a CellAto.
     *
     * @param cell The cell.
     */
    private CellAto cellAto(Cell cell) {
        return CellAto.builder()
                .row(cell.getRowNumber())
                .column(cell.getColumnNumber())
                .value(getCellValue(cell))
                .build();
    }

    /**
     * Returns a cells visible value.
     * ▢ If the cell is not visible.
     * F If the cell was flagged.
     * M If the cell has mine.
     * The amount of neighbour mines, if it has at least one.
     * Empty if it has no neighbour mines.
     *
     * @param cell The cell.
     */
    private String getCellValue(Cell cell) {
        if (cell.getIsVisible()) {
            if (cell.getIsMine()) {
                return "M";
            } else if (cell.getNeighbourMines() == 0) {
                return " ";
            } else {
                return cell.getNeighbourMines().toString();
            }
        } else {
            if (cell.getHasFlag()) {
                return "F";
            } else {
                return "▢";
            }
        }
    }

    /**
     * Maps a move to a MoveResponseAto.
     *
     * @param move The move.
     */
    public MoveResponseAto moveAto(Move move) {
        return MoveResponseAto.builder()
                .id(move.getId())
                .type(move.getType())
                .result(move.getResult())
                .gameId(move.getGame().getId())
                .row(move.getCell().getRowNumber())
                .column(move.getCell().getColumnNumber())
                .build();
    }
}
