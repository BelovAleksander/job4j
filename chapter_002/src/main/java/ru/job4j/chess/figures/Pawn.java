package ru.job4j.chess.figures;

import ru.job4j.chess.Board;
/**
 *
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 13.06.18
 */
public class Pawn extends Figure {
    private final Cell position;

    public Pawn(final Cell position, final Board board) {
        super(board);
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) {
        Cell[] steps = new Cell[0];
        if (source.y == dest.y + 1 && source.x == dest.x) {
            steps = new Cell[] {dest};
        }
        return steps;
    }

    @Override
    public Figure copy(Cell dest) {
        return new Pawn(dest, board);
    }
}
