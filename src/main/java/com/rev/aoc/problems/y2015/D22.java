package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.framework.problem.AocProblemI;
import com.rev.aoc.framework.problem.ProblemExecutionException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public final class D22 extends AocProblem<Integer, Integer> {

    private static final int[] SPELL_DURATIONS = new int[]{1, 1, 6, 6, 5};
    private static final int[] SPELL_COSTS = new int[]{53, 73, 113, 173, 229};
    public static final int NUM_EFFECTS = 5;
    public static final int MAGIC_MISSILE = 0;
    public static final int DRAIN = 1;
    public static final int SHIELD = 2;
    public static final int POISON = 3;
    public static final int ARMOR_BOOST = 7;
    public static final int POISON_DAMAGE = 3;
    public static final int MANA_RECHARGE = 101;
    public static final int STARTING_HP = 50;
    public static final int STARTING_MANA = 500;

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 22);
    }

    @AocProblemI(year = 2015, day = 22, part = 1)
    @Override
    protected Integer partOneImpl() {
        return solve(false);
    }

    @AocProblemI(year = 2015, day = 22, part = 2)
    @Override
    protected Integer partTwoImpl() {
        return solve(true);
    }

    private int solve(boolean hardMode) {
        PriorityQueue<GameState> heap = new PriorityQueue<>(Comparator.comparingInt(GameState::getPlayerSpentMana));
        heap.add(loadGameState());

        while (!heap.isEmpty()) {
            GameState gameState = heap.remove();

            gameState.startTurn(hardMode);

            //In hard mode, starting a turn can kill you, so need to check here.
            if (gameState.playerHasLost()) {
                continue;
            }

            if (gameState.playersTurn()) {
                Collection<Integer> spellsToCast = gameState.playerSpellChoices();
                for (final int spell : spellsToCast) {
                    GameState copy = new GameState(gameState);
                    copy.castSpell(spell);
                    copy.applyEffectsAndDealDamage();
                    copy.endTurn();

                    if (copy.playerHasWon()) {
                        return copy.playerSpentMana;
                    }
                    if (!gameState.playerHasLost()) {
                        heap.add(copy);
                    }
                }
            } else {
                gameState.applyEffectsAndDealDamage();
                gameState.endTurn();

                if (gameState.playerHasWon()) {
                    return gameState.playerSpentMana;
                }
                if (!gameState.playerHasLost()) {
                    heap.add(gameState);
                }
            }
        }

        throw new ProblemExecutionException("Solution not found");
    }

    private GameState loadGameState() {
        final List<String> lines = loadResources();
        final Map<String, Integer> attributes = new HashMap<>();
        for (final String line : lines) {
            String[] split = line.replaceAll("\\s+", "").split(":");
            attributes.put(split[0], Integer.parseInt(split[1]));
        }
        return new GameState(STARTING_HP, STARTING_MANA, 0, attributes.get("HitPoints"), attributes.get("Damage"));
    }

    private static final class GameState {
        private int turn = 0;
        private int playerHp;
        private int playerMana;
        private int playerSpentMana;
        private int bossHp;
        private int bossDamage;

        private final int[] effects;

        private GameState(
                final int playerHp,
                final int playerMana,
                final int playerSpentMana,
                final int bossHp,
                final int bossDamage) {
            this.playerHp = playerHp;
            this.playerMana = playerMana;
            this.playerSpentMana = playerSpentMana;
            this.bossHp = bossHp;
            this.bossDamage = bossDamage;
            this.effects = new int[NUM_EFFECTS];
        }

        GameState(final GameState gameState) {
            this.turn = gameState.turn;
            this.playerHp = gameState.playerHp;
            this.playerMana = gameState.playerMana;
            this.playerSpentMana = gameState.playerSpentMana;
            this.bossHp = gameState.bossHp;
            this.bossDamage = gameState.bossDamage;
            this.effects = Arrays.copyOf(gameState.effects, gameState.effects.length);
        }

        public void castSpell(final int spell) {
            final int spellDuration = SPELL_DURATIONS[spell];
            effects[spell] = spellDuration;
            playerMana -= SPELL_COSTS[spell];
            playerSpentMana += SPELL_COSTS[spell];

            if (spell == MAGIC_MISSILE) {
                bossHp -= 4;
            }
            if (spell == DRAIN) {
                playerHp += 2;
                bossHp -= 2;
            }
        }

        public void applyEffectsAndDealDamage() {
            int playerArmor = 0;
            int playerManaIncrease = 0;
            if (effects[SHIELD] != 0) {
                playerArmor += ARMOR_BOOST;
            }
            if (effects[POISON] != 0) {
                bossHp -= POISON_DAMAGE;
            }
            if (effects[4] != 0) {
                playerManaIncrease += MANA_RECHARGE;
            }

            playerMana += playerManaIncrease;

            if (!playersTurn()) {
                playerHp -= Math.max(1, bossDamage - playerArmor);
            }

            for (int i = 0; i < effects.length; i++) {
                effects[i] = Math.max(0, effects[i] - 1);
            }
        }

        public void endTurn() {
            turn++;
        }

        private boolean playersTurn() {
            return turn % 2 == 0;
        }

        public void startTurn(boolean hardMode) {
            if (hardMode && playersTurn()) {
                playerHp -= 1;
            }
        }

        public int getPlayerSpentMana() {
            return playerSpentMana;
        }

        public boolean playerHasWon() {
            return bossHp <= 0;
        }

        public boolean playerHasLost() {
            return playerHp <= 0;
        }

        public Collection<Integer> playerSpellChoices() {
            final Collection<Integer> spells = new HashSet<>();
            for (int i = 0; i < effects.length; i++) {
                if (effects[i] == 0 && playerMana >= SPELL_COSTS[i]) {
                    spells.add(i);
                }
            }
            return spells;
        }
    }
}
