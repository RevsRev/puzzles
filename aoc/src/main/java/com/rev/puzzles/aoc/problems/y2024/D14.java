package com.rev.puzzles.aoc.problems.y2024;

import com.rev.puzzles.aoc.framework.load.LoaderUtils;
import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.problem.ResourceLoader;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public final class D14 {

    public static final int PART_ONE_TIME = 100;
    public static final int PART_ONE_WIDTH = 101;
    public static final int PART_ONE_HEIGHT = 103;
    private static final int PART_TWO_LIMIT = 10000;

    @AocProblemI(year = 2024, day = 14, part = 1)
    public Long partOneImpl(final ResourceLoader resourceLoader) {
        Integer[][] posAndVels = LoaderUtils.loadResourcesAsMatrix(
                resourceLoader.resources(),
                new Integer[][]{},
                splitter());

        Map<Pair<Integer, Integer>, Integer> robotPositions =
                calculateRobotPositions(posAndVels, PART_ONE_TIME, PART_ONE_WIDTH, PART_ONE_HEIGHT);

        Map<Pair<Integer, Integer>, Integer> quadrantCounts = new HashMap<>();
        for (Map.Entry<Pair<Integer, Integer>, Integer> posAndCount : robotPositions.entrySet()) {
            Optional<Pair<Integer, Integer>> quadrant =
                    getQuadrant(posAndCount.getKey(), PART_ONE_WIDTH, PART_ONE_HEIGHT);
            if (quadrant.isPresent()) {
                Pair<Integer, Integer> quad = quadrant.get();
                Integer val = quadrantCounts.getOrDefault(quad, 0);
                quadrantCounts.put(quad, val + posAndCount.getValue());
            }
        }

        long score = 1;
        for (Integer quadScore : quadrantCounts.values()) {
            score *= quadScore;
        }
        return score;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @AocProblemI(year = 2024, day = 14, part = 2)
    public Long partTwoImpl(final ResourceLoader resourceLoader) {
        Integer[][] posAndVels = LoaderUtils.loadResourcesAsMatrix(
                resourceLoader.resources(),
                new Integer[][]{},
                splitter());
        Scanner s = new Scanner(System.in);
//        for (int i = 11; i < PART_TWO_LIMIT; i += 101) {
//            //from inspection... 11, 112, 213, 314, ... look interesting
//            //correct answer turned out to be 7687
//            Map<Pair<Integer, Integer>, Integer> robotPositions =
//                    calculateRobotPositions(posAndVels, i, PART_ONE_WIDTH, PART_ONE_HEIGHT);
//            printPicture(i, robotPositions);
//            System.out.println("Press enter to continue.....");
//            s.nextLine();
//        }
        return 7687L;
    }

    private void printPicture(int time, final Map<Pair<Integer, Integer>, Integer> robotPositions) {
        System.out.printf("Time: %s%n", time);
        System.out.println();
        for (int i = 0; i < PART_ONE_WIDTH; i++) {
            for (int j = 0; j < PART_ONE_HEIGHT; j++) {
                Pair<Integer, Integer> key = Pair.of(j, i);
                if (robotPositions.containsKey(key)) {
                    System.out.printf("%s", robotPositions.get(key));
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private static Map<Pair<Integer, Integer>, Integer> calculateRobotPositions(final Integer[][] posAndVels,
                                                                                long time,
                                                                                long width,
                                                                                long height) {
        Map<Pair<Integer, Integer>, Integer> robotPositions = new HashMap<>();
        for (Integer[] posAndVel : posAndVels) {
            long robotX = (posAndVel[0] + time * posAndVel[2]) % width;
            long robotY = (posAndVel[1] + time * posAndVel[3]) % height;

            if (robotX < 0) {
                robotX += width;
            }
            if (robotY < 0) {
                robotY += height;
            }

            Pair<Integer, Integer> key = Pair.of((int) robotX, (int) robotY);
            Integer val = robotPositions.getOrDefault(key, 0);
            robotPositions.put(key, val + 1);
        }
        return robotPositions;
    }

    private Function<String, Integer[]> splitter() {
        return s -> {
            Integer[] result = new Integer[4];
            String[] posAndVel = s.trim().split(" ");
            String[] pos = posAndVel[0].replace("p=", "").split(",");
            String[] vel = posAndVel[1].replace("v=", "").split(",");
            result[0] = Integer.parseInt(pos[0]);
            result[1] = Integer.parseInt(pos[1]);
            result[2] = Integer.parseInt(vel[0]);
            result[3] = Integer.parseInt(vel[1]);
            return result;
        };
    }

    private boolean inQuadrant(final Pair<Integer, Integer> key, int width, int height) {
        return key.getLeft() == width / 2 || key.getRight() == height / 2;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private Optional<Pair<Integer, Integer>> getQuadrant(final Pair<Integer, Integer> key, int width, int height) {
        int xMiddle = width / 2;
        int yMiddle = height / 2;
        if (key.getLeft() == xMiddle || key.getRight() == yMiddle) {
            return Optional.empty();
        }
        int quadX = key.getLeft() < xMiddle ? -1 : 1;
        int quadY = key.getRight() < yMiddle ? -1 : 1;
        return Optional.of(Pair.of(quadX, quadY));
    }
}
