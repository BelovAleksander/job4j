package ru.job4j.chess.figures;


import ru.job4j.chess.Board;

/**
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Queen extends Figure {
    private final Cell position;

    public Queen(final Cell position, Board board) {
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
        return new Queen(dest, board);
    }
}
