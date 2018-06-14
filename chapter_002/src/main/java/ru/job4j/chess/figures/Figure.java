package ru.job4j.chess.figures;

import ru.job4j.chess.Board;
import ru.job4j.chess.ImpossibleMoveException;

public abstract class Figure {
    public abstract Cell position();

    protected final Board board;

    public Figure(Board board) {
        this.board = board;
    }

    public abstract Cell[] way(Cell source, Cell dest);

    public Cell[] diagonalMove(Cell source, Cell dest) throws ImpossibleMoveException {
        Cell[] steps;
        if ((Math.abs(dest.x - source.x)) != (Math.abs(dest.y - source.y))) {
            throw new ImpossibleMoveException("Impossible move!");
        }
        int x = source.x;
        int y = source.y;
        steps = new Cell[Math.abs(dest.x - source.x)];
        for (int index = 0; index != steps.length; index++) {
            if ((x < dest.x) && (y < dest.y)) {
                x++;
                y++;
            } else if ((x > dest.x) && (y > dest.y)) {
                x--;
                y--;
            } else if ((x < dest.x) && (y > dest.y)) {
                x++;
                y--;
            } else {
                x--;
                y++;
            }
            for (Cell cell : Cell.values()) {
                if ((cell.x == x) && (cell.y == y)) {
                    steps[index] = cell;
                    break;
                }
            }
        }


        return steps;
    }

    public String icon() {
        return String.format(
                "%s.png", this.getClass().getSimpleName()
        );

    }

    public abstract Figure copy(Cell dest);
}
