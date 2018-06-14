package ru.job4j.chess.figures;

import ru.job4j.chess.Board;
import ru.job4j.chess.ImpossibleMoveException;
import ru.job4j.chess.OccupiedFieldException;


/**
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Bishop extends Figure {
    private Cell position = null;

    public Bishop(Board board) {
         super(board);
    }

    public Bishop(Cell position, Board board) {
        super(board);
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException, OccupiedFieldException {
        Cell[] result = diagonalMove(source, dest);
        for (Cell cell : result) {
            if (board.findBy(cell) != -1) {
                throw new OccupiedFieldException("Field is Occupied!");
            }
        }
        return result;
    }

    @Override
    public Figure copy(Cell dest) {
        return new Bishop(dest, board);
    }
}
