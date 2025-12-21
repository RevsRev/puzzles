package com.rev.puzzles.utils.arr;


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
    private Map<CellDirection, BorderVector> borders = linkedSquare();
    private final Map<CellDirection, UnitCell<T>> neighbours = new HashMap<>() {{
        put(CellDirection.UP, null);
        put(CellDirection.RIGHT, null);
        put(CellDirection.DOWN, null);
        put(CellDirection.LEFT, null);
    }};

    private final int i;
    private final int j;
    private final T val;

    public UnitCell(int i, int j, final T val) {
        this.i = i;
        this.j = j;
        this.val = val;
    }

    public void addNeighbour(final UnitCell<T> neighbour, final CellDirection dir) {
        neighbours.put(dir, neighbour);
        neighbour.neighbours.put(CellDirection.opposite(dir), this);
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
            CellDirection dir = nextSide.direction;
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
        for (CellDirection dir : CellDirection.UP) {
            if (!neighbours.containsKey(dir) || neighbours.get(dir) == null || !borders.containsKey(dir)) {
                continue;
                //can't merge / have already merged
            }
            UnitCell<T> neighbour = neighbours.get(dir);
            if (!val.equals(neighbour.val)) {
                continue;
            }

            //remove the border they share in common, and link the other sides...
            CellDirection opposite = CellDirection.opposite(dir);
            BorderVector side = borders.remove(dir);
            BorderVector neighbourOpposite = neighbour.borders.remove(opposite);

            side.getNext().setPrevious(neighbourOpposite.getPrevious());
            neighbourOpposite.getPrevious().setNext(side.getNext());

            side.getPrevious().setNext(neighbourOpposite.getNext());
            neighbourOpposite.getNext().setPrevious(side.getPrevious());
        }
    }

    private static Map<CellDirection, BorderVector> linkedSquare() {
        Map<CellDirection, BorderVector> directions = new HashMap<>();
        directions.put(CellDirection.UP, new BorderVector(CellDirection.UP));
        directions.put(CellDirection.RIGHT, new BorderVector(CellDirection.RIGHT));
        directions.put(CellDirection.DOWN, new BorderVector(CellDirection.DOWN));
        directions.put(CellDirection.LEFT, new BorderVector(CellDirection.LEFT));

        //link directions
        directions.get(CellDirection.UP).setPrevious(directions.get(CellDirection.LEFT));
        directions.get(CellDirection.UP).setNext(directions.get(CellDirection.RIGHT));
        directions.get(CellDirection.RIGHT).setPrevious(directions.get(CellDirection.UP));
        directions.get(CellDirection.RIGHT).setNext(directions.get(CellDirection.DOWN));
        directions.get(CellDirection.DOWN).setPrevious(directions.get(CellDirection.RIGHT));
        directions.get(CellDirection.DOWN).setNext(directions.get(CellDirection.LEFT));
        directions.get(CellDirection.LEFT).setPrevious(directions.get(CellDirection.DOWN));
        directions.get(CellDirection.LEFT).setNext(directions.get(CellDirection.UP));
        return directions;
    }

    public static final class BorderVector {
        private final CellDirection direction;

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

        public BorderVector(final CellDirection direction) {
            this.direction = direction;
        }
    }

}

