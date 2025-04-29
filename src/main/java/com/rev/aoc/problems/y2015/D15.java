package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class D15 extends AocProblem<Long, Long> {

    private static final Pattern NUMBERS_PATTERN = Pattern.compile("-?\\d+");
    public static final int NO_CALORIE_RESTRICTION = -1;
    public static final int PART_TWO_CALORIE_RESTRICTION = 500;
    public static final int TOTAL_SPOONFULLS = 100;

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 15);
    }

    @Override
    protected Long partOneImpl() {
        final List<Ingredient> ingredients = loadIngredients();
        return computeScore(ingredients, NO_CALORIE_RESTRICTION);
    }

    @Override
    protected Long partTwoImpl() {
        final List<Ingredient> ingredients = loadIngredients();
        return computeScore(ingredients, PART_TWO_CALORIE_RESTRICTION);
    }

    private long computeScore(final List<Ingredient> ingredients, final int calorieRestriction) {
        final long[] propertyScores = new long[5];
        return computeScore(ingredients, 0, 0, propertyScores, calorieRestriction);
    }

    private long computeScore(
            final List<Ingredient> ingredients,
            final int spoonsUsed,
            final int ingredientsIndex,
            final long[] propertyScores,
            final int calorieRestriction) {
        if (ingredientsIndex == ingredients.size()) {

            if (spoonsUsed != TOTAL_SPOONFULLS) {
                return 0;
            }

            if (calorieRestriction != NO_CALORIE_RESTRICTION
                    && propertyScores[propertyScores.length - 1] != calorieRestriction) {
                return 0;
            }

            long score = 1;
            for (int i = 0; i < propertyScores.length - 1; i++) { //ignore calories!
                if (propertyScores[i] < 0) {
                    return 0;
                }
                score *= propertyScores[i];
            }
            return score;
        }

        if (spoonsUsed == TOTAL_SPOONFULLS) {
            return 0;
        }

        final Ingredient ingredient = ingredients.get(ingredientsIndex);
        final long[] ingredientScores = new long[propertyScores.length];

        long score = 0;
        for (int i = 0; i <= TOTAL_SPOONFULLS - spoonsUsed; i++) {
            ingredientScores[0] = ingredient.capacity * i;
            ingredientScores[1] = ingredient.durability * i;
            ingredientScores[2] = ingredient.flavor * i;
            ingredientScores[3] = ingredient.texture * i;
            ingredientScores[4] = ingredient.calories * i;

            for (int j = 0; j < ingredientScores.length; j++) {
                propertyScores[j] += ingredientScores[j];
            }
            final long newScore = computeScore(
                    ingredients,
                    spoonsUsed + i,
                    ingredientsIndex + 1,
                    propertyScores,
                    calorieRestriction);

            if (newScore > score) {
                score = newScore;
            }
            for (int j = 0; j < ingredientScores.length; j++) {
                propertyScores[j] -= ingredientScores[j];
            }
        }
        return score;
    }

    private List<Ingredient> loadIngredients() {
        final List<String> lines = loadResources();
        final List<Ingredient> ingredients = new ArrayList<>(lines.size());

        for (final String line : lines) {
            ingredients.add(parseIngredient(line));
        }

        return ingredients;
    }

    private Ingredient parseIngredient(final String line) {
        String[] split = line.split(":");
        final String name = split[0];
        final String[] properties = split[1].split(",");
        return new Ingredient(
                name,
                Long.parseLong(NUMBERS_PATTERN.matcher(properties[0]).results().findFirst().orElseThrow().group()),
                Long.parseLong(NUMBERS_PATTERN.matcher(properties[1]).results().findFirst().orElseThrow().group()),
                Long.parseLong(NUMBERS_PATTERN.matcher(properties[2]).results().findFirst().orElseThrow().group()),
                Long.parseLong(NUMBERS_PATTERN.matcher(properties[3]).results().findFirst().orElseThrow().group()),
                Long.parseLong(NUMBERS_PATTERN.matcher(properties[4]).results().findFirst().orElseThrow().group())
        );
    }

    private static final class Ingredient {
        private final String name;
        private final long capacity;
        private final long durability;
        private final long flavor;
        private final long texture;
        private final long calories;

        private Ingredient(
                final String name,
                final long capacity,
                final long durability,
                final long flavor,
                final long texture,
                final long calories) {
            this.name = name;
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }
    }
}
