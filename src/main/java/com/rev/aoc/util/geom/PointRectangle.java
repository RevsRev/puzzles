package com.rev.aoc.util.geom;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Represents a rectangle consisting of discrete integer points.
 * Useful for problems involving overlapping regions on e.g. an array, when you don't want to load the whole array into
 * memory.
 */
public final class PointRectangle {
    public final int x;
    public final int y;
    public final int w;
    public final int h;

    public PointRectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public static boolean overlapping(final PointRectangle a, final PointRectangle b) {
        return     a.x <= b.x + b.w
                && b.x <= a.x + a.w
                && a.y <= b.y + b.h
                && b.y <= a.y + a.h;
    }

    public static Optional<PointRectangle> getOverlappingRegion(final PointRectangle a, final PointRectangle b) {
        if (!overlapping(a,b)) {
            return Optional.empty();
        }
        int x = Math.max(a.x, b.x);
        int w = Math.min((a.x + a.w) - x, (b.x + b.w) - x);
        int y = Math.max(a.y, b.y);
        int h = Math.min((a.y + a.h) - y, (b.y + b.h) - y);

        return Optional.of(new PointRectangle(x, y, w, h));
    }

    /**
     * Get sup ^ !sub
     * @param sup
     * @param sub
     * @return compliment of sub in sup.
     */
    public static Set<PointRectangle> getCompliment(final PointRectangle sup, final PointRectangle sub) {
        Optional<PointRectangle> supInSubOpt = getOverlappingRegion(sup, sub);
        if (supInSubOpt.isEmpty()) {
            return Set.of(sup);
        }

        final PointRectangle subInSup = supInSubOpt.get();
        final Set<PointRectangle> compliment = new HashSet<>();

        //deal with left split
        if (sup.x < subInSup.x) {
            //deal with top split
            int w = subInSup.x - 1 - sup.x;
            if (sup.y < subInSup.y) {
                compliment.add(new PointRectangle(sup.x, sup.y, w, subInSup.y - 1 - sup.y));
            }
            //deal with bottom split
            if (subInSup.y + subInSup.h < sup.y + sup.h) {
                compliment.add(new PointRectangle(sup.x,
                        subInSup.y + subInSup.h + 1,
                        w,
                        (sup.y + sup.h) - (subInSup.y + subInSup.h + 1)));
            }
            //deal with the (vertical) middle
            compliment.add(new PointRectangle(sup.x, subInSup.y, w, subInSup.h));
        }

        //deal with right split
        if (subInSup.x + subInSup.w < sup.x + sup.w) {
            //deal with top split
            int w = (sup.x + sup.w) - (subInSup.x + subInSup.w) - 1;
            if (sup.y < subInSup.y) {
                compliment.add(new PointRectangle(subInSup.x + subInSup.w + 1, sup.y, w, subInSup.y - 1 - sup.y));
            }
            //deal with bottom split
            if (subInSup.y + subInSup.h < sup.y + sup.h) {
                compliment.add(new PointRectangle(subInSup.x + subInSup.w + 1,
                        subInSup.y + subInSup.h + 1,
                        w,
                        (sup.y + sup.h) - (subInSup.y + subInSup.h + 1)));
            }
            //deal with the (vertical) middle
            compliment.add(new PointRectangle(subInSup.x + subInSup.w + 1, subInSup.y, w, subInSup.h));
        }

        //deal with the (horizontal) middle split
        int w = subInSup.w;
        if (sup.y < subInSup.y) {
            compliment.add(new PointRectangle(subInSup.x, sup.y, w, subInSup.y - 1 - sup.y));
        }
        //deal with bottom split
        if (subInSup.y + subInSup.h < sup.y + sup.h) {
            compliment.add(new PointRectangle(subInSup.x,
                    subInSup.y + subInSup.h + 1,
                    w,
                    (sup.y + sup.h) - (subInSup.y + subInSup.h + 1)));
        }
        //(ignore the (vertical) middle)

        return compliment;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PointRectangle pointRectangle = (PointRectangle) o;
        return x == pointRectangle.x && y == pointRectangle.y && w == pointRectangle.w && h == pointRectangle.h;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, w, h);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                '}';
    }
}
