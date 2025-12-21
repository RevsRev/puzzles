package com.rev.puzzles.math.geom;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class UnitCell<T> {

    /**
     * Border vectors go clockwise around the unit cell
     * # -> #
     * ^    |
     * |    V
     * # <- #
     */
    private Map<Direction, BorderVector> borders = linkedSquare();
    private final Map<Direction, UnitCell<T>> neighbours = new HashMap<>() {{
        put(Direction.UP, null);
        put(Direction.RIGHT, null);
        put(Direction.DOWN, null);
        put(Direction.LEFT, null);
    }};

    private final int i;
    private final int j;
    private final T val;

    public UnitCell(int i, int j, final T val) {
        this.i = i;
        this.j = j;
        this.val = val;
    }

    public void addNeighbour(final UnitCell<T> neighbour, final Direction dir) {
        neighbours.put(dir, neighbour);
        neighbour.neighbours.put(Direction.opposite(dir), this);
    }

    public long area(final Set<UnitCell<T>> visited) {
        if (visited.contains(this)) {
            return 0;
        }

        visited.add(this);
        long area = 1;
        for (UnitCell<T> neighbour : neighbours.values()) {
            if (neighbour == null) {
                continue;
            }
            if (val.equals(neighbour.val) && !visited.contains(neighbour)) {
                area += neighbour.area(visited);
            }
        }
        return area;
    }

    public long perimeter(final Set<UnitCell<T>> visited) {
        if (visited.contains(this)) {
            return 0;
        }

        visited.add(this);
        long perimeter = 0;
        for (UnitCell<T> neighbour : neighbours.values()) {
            if (neighbour == null) {
                perimeter += 1;
                continue;
            }
            if (val.equals(neighbour.val) && !visited.contains(neighbour)) {
                perimeter += neighbour.perimeter(visited);
            } else if (!val.equals(neighbour.val)) {
                perimeter++;
            }
        }
        return perimeter;
    }

    public long edges(final Set<Pair<Integer, T>> visitedSides, final Set<UnitCell<T>> visitedCells) {
        return edges(this, visitedSides, visitedCells);
    }

    private static <T> long edges(final UnitCell<T> cell, final Set<Pair<Integer, T>> visitedSides,
                                  final Set<UnitCell<T>> visitedCells) {
        if (visitedCells.contains(cell)) {
            return 0;
        }
        visitedCells.add(cell);

        long edges = 0;
        for (BorderVector side : cell.borders.values()) {
            if (side == null) {
                continue;
            }
            BorderVector nextSide = side;
            Direction dir = nextSide.direction;
            while (!visitedSides.contains(Pair.of(nextSide.uid, cell.val))) {
                visitedSides.add(Pair.of(nextSide.uid, cell.val));
                nextSide = nextSide.next;
                if (nextSide.direction != dir) {
                    edges++;
                }
                dir = nextSide.direction;
            }
        }

        for (UnitCell<T> neighbour : cell.neighbours.values()) {
            if (neighbour == null || neighbour.val != cell.val || visitedCells.contains(neighbour)) {
                continue;
            }
            edges += edges(neighbour, visitedSides, visitedCells);
        }
        return edges;
    }

    public void mergeBorders() {
        for (Direction dir : Direction.UP) {
            if (!neighbours.containsKey(dir) || neighbours.get(dir) == null || !borders.containsKey(dir)) {
                continue;
                //can't merge / have already merged
            }
            UnitCell<T> neighbour = neighbours.get(dir);
            if (!val.equals(neighbour.val)) {
                continue;
            }

            //remove the border they share in common, and link the other sides...
            Direction opposite = Direction.opposite(dir);
            BorderVector side = borders.remove(dir);
            BorderVector neighbourOpposite = neighbour.borders.remove(opposite);

            side.getNext().setPrevious(neighbourOpposite.getPrevious());
            neighbourOpposite.getPrevious().setNext(side.getNext());

            side.getPrevious().setNext(neighbourOpposite.getNext());
            neighbourOpposite.getNext().setPrevious(side.getPrevious());
        }
    }

    private static Map<Direction, BorderVector> linkedSquare() {
        Map<Direction, BorderVector> directions = new HashMap<>();
        directions.put(Direction.UP, new BorderVector(Direction.UP));
        directions.put(Direction.RIGHT, new BorderVector(Direction.RIGHT));
        directions.put(Direction.DOWN, new BorderVector(Direction.DOWN));
        directions.put(Direction.LEFT, new BorderVector(Direction.LEFT));

        //link directions
        directions.get(Direction.UP).setPrevious(directions.get(Direction.LEFT));
        directions.get(Direction.UP).setNext(directions.get(Direction.RIGHT));
        directions.get(Direction.RIGHT).setPrevious(directions.get(Direction.UP));
        directions.get(Direction.RIGHT).setNext(directions.get(Direction.DOWN));
        directions.get(Direction.DOWN).setPrevious(directions.get(Direction.RIGHT));
        directions.get(Direction.DOWN).setNext(directions.get(Direction.LEFT));
        directions.get(Direction.LEFT).setPrevious(directions.get(Direction.DOWN));
        directions.get(Direction.LEFT).setNext(directions.get(Direction.UP));
        return directions;
    }

    public static final class BorderVector {
        private final Direction direction;

        @SuppressWarnings("checkstyle:StaticVariableName")
        private static int UID = 0;

        @Getter
        private int uid = UID++;

        @Getter
        @Setter
        private BorderVector previous = null;
        @Getter
        @Setter
        private BorderVector next = null; //link vectors to get a border in the processing phase

        public BorderVector(final Direction direction) {
            this.direction = direction;
        }
    }

}
