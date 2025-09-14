package com.rev.puzzles.aoc.problems.y2015;

import com.rev.puzzles.aoc.framework.AocProblemI;
import com.rev.puzzles.framework.framework.problem.ProblemExecutionException;
import com.rev.puzzles.framework.framework.problem.ResourceLoader;
import com.rev.puzzles.framework.util.set.SetUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public final class D21 {

    @AocProblemI(year = 2015, day = 21, part = 1)
    public Integer partOneImpl(final ResourceLoader resourceLoader) {
        final Player boss = loadBoss(resourceLoader);
        final List<Attributes> weapons = loadWeapons();
        final List<Attributes> armors = loadArmors();
        final List<Attributes> rings = loadRings();

        TreeMap<Integer, Set<Attributes>> loadouts = getLoadouts(weapons, armors, rings);
        for (Set<Attributes> loadoutOfCost : loadouts.values()) {
            for (Attributes loadout : loadoutOfCost) {
                final Player player = new Player(100, loadout.damage, loadout.armor);
                if (wins(player, boss)) {
                    return loadout.cost;
                }
            }
        }
        throw new ProblemExecutionException("No solution could be found");
    }

    @AocProblemI(year = 2015, day = 21, part = 2)
    public Integer partTwoImpl(final ResourceLoader resourceLoader) {
        final Player boss = loadBoss(resourceLoader);
        final List<Attributes> weapons = loadWeapons();
        final List<Attributes> armors = loadArmors();
        final List<Attributes> rings = loadRings();

        Map<Integer, Set<Attributes>> loadouts = getLoadouts(weapons, armors, rings).descendingMap();
        for (Set<Attributes> loadoutOfCost : loadouts.values()) {
            for (Attributes loadout : loadoutOfCost) {
                final Player player = new Player(100, loadout.damage, loadout.armor);
                if (!wins(player, boss)) {
                    return loadout.cost;
                }
            }
        }
        throw new ProblemExecutionException("No solution could be found");
    }

    private boolean wins(final Player player, final Player boss) {
        int playerHp = player.hp;
        int bossHp = boss.hp;

        final int playerDamage = Math.max(1, player.damage - boss.armor);
        final int bossDamage = Math.max(1, boss.damage - player.armor);

        while (playerHp > 0 && bossHp > 0) {
            bossHp -= playerDamage;
            playerHp -= bossDamage;
        }

        return bossHp <= 0;
    }

    private TreeMap<Integer, Set<Attributes>> getLoadouts(
            final List<Attributes> weapons,
            final List<Attributes> armors,
            final List<Attributes> rings) {

        final TreeMap<Integer, Set<Attributes>> costAndLoadout = new TreeMap<>();

        Attributes[] weaponsArr = weapons.toArray(new Attributes[weapons.size()]);
        Attributes[] armorsArr = armors.toArray(new Attributes[armors.size()]);
        Attributes[] ringsArr = rings.toArray(new Attributes[rings.size()]);

        Attributes[] weaponsSubset = new Attributes[1];
        Attributes[] armorsSubset = new Attributes[1];
        Attributes[] ringsSubset = new Attributes[2];

        List<Attributes[]> weaponChoices = SetUtils.subsetsOfSizeLeqN(weaponsArr, weaponsSubset);
        List<Attributes[]> armorChoices = SetUtils.subsetsOfSizeLeqN(armorsArr, armorsSubset);
        List<Attributes[]> ringChoices = SetUtils.subsetsOfSizeLeqN(ringsArr, ringsSubset);

        for (int i = 0; i < weaponChoices.size(); i++) {

            //You must buy a weapon!
            if (weaponChoices.get(i).length == 0) {
                continue;
            }

            int weaponCost = 0;
            int weaponDamage = 0;
            int weaponArmor = 0;

            for (int a = 0; a < weaponChoices.get(i).length; a++) {
                weaponCost += weaponChoices.get(i)[a].cost;
                weaponDamage += weaponChoices.get(i)[a].damage;
                weaponArmor += weaponChoices.get(i)[a].armor;
            }

            for (int j = 0; j < armorChoices.size(); j++) {

                int armorCost = 0;
                int armorDamage = 0;
                int armorArmor = 0;

                for (int a = 0; a < armorChoices.get(j).length; a++) {
                    armorCost += armorChoices.get(j)[a].cost;
                    armorDamage += armorChoices.get(j)[a].damage;
                    armorArmor += armorChoices.get(j)[a].armor;
                }

                for (int k = 0; k < ringChoices.size(); k++) {

                    int ringCost = 0;
                    int ringDamage = 0;
                    int ringArmor = 0;

                    for (int a = 0; a < ringChoices.get(k).length; a++) {
                        ringCost += ringChoices.get(k)[a].cost;
                        ringDamage += ringChoices.get(k)[a].damage;
                        ringArmor += ringChoices.get(k)[a].armor;
                    }
                    Attributes aggregate = new Attributes(
                            "aggregate",
                            weaponCost + armorCost + ringCost,
                            weaponDamage + armorDamage + ringDamage,
                            weaponArmor + armorArmor + ringArmor);
                    costAndLoadout.computeIfAbsent(aggregate.cost, key -> new HashSet<>()).add(aggregate);
                }
            }
        }
        return costAndLoadout;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private List<Attributes> loadRings() {
        return List.of(
                new Attributes("Damage +1", 25, 1, 0),
                new Attributes("Damage +2", 50, 2, 0),
                new Attributes("Damage +3", 100, 3, 0),
                new Attributes("Defense +1", 20, 0, 1),
                new Attributes("Defense +2", 40, 0, 2),
                new Attributes("Defense +3", 80, 0, 3)
        );
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private List<Attributes> loadArmors() {
        return List.of(
                new Attributes("Leather", 13, 0, 1),
                new Attributes("Chainmail", 31, 0, 2),
                new Attributes("Splintmail", 53, 0, 3),
                new Attributes("Bandedmail", 75, 0, 4),
                new Attributes("Platemail", 102, 0, 5)
        );
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private List<Attributes> loadWeapons() {
        return List.of(
                new Attributes("Dagger", 8, 4, 0),
                new Attributes("Shortsword", 10, 5, 0),
                new Attributes("Warhammer", 25, 6, 0),
                new Attributes("Longsword", 40, 7, 0),
                new Attributes("Greataxe", 74, 8, 0)
        );
    }

    private Player loadBoss(final ResourceLoader resourceLoader) {
        final List<String> lines = resourceLoader.resources();
        final Map<String, Integer> attributes = new HashMap<>();
        for (final String line : lines) {
            String[] split = line.replaceAll("\\s+", "").split(":");
            attributes.put(split[0], Integer.parseInt(split[1]));
        }
        return new Player(attributes.get("HitPoints"), attributes.get("Damage"), attributes.get("Armor"));
    }

    private static final class Player {
        private final int hp;
        private final int damage;
        private final int armor;

        private Player(int hp, int damage, int armor) {
            this.hp = hp;
            this.damage = damage;
            this.armor = armor;
        }
    }

    private static final class Attributes {
        private final String name;
        private final int cost;
        private final int damage;
        private final int armor;

        private Attributes(final String name, final int cost, final int damage, final int armor) {
            this.name = name;
            this.cost = cost;
            this.damage = damage;
            this.armor = armor;
        }

        @Override
        public boolean equals(final Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Attributes that = (Attributes) o;
            return cost == that.cost && damage == that.damage && armor == that.armor && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, cost, damage, armor);
        }
    }
}
