package ru.job4j.chess.figures;

import ru.job4j.chess.Board;
/**
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Knight extends Figure {
    private final Cell position;

    public Knight(final Cell position, final Board board) {
        super(board);
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) {
        return new Cell[] {dest};
    }

    @Override
    public Figure copy(Cell dest) {
        return new Knight(dest, board);
    }
}
