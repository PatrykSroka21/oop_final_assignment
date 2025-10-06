package com.game;

import java.util.Random;

/**
 * Encounter - hanterar slumpmässiga möten (70% vanlig, 10% boss, 20% safe place)
 */
public class Encounter {
    private static final Random RNG = new Random();

    public static final int PROB_COMMON = 70;
    public static final int PROB_BOSS = 10; // 10%
    public static final int PROB_SAFE = 20;

    // Returnerar typ: "COMMON", "BOSS", "SAFE"
    public static String rollEncounter() {
        int r = RNG.nextInt(100);
        if (r < PROB_COMMON) return "COMMON";
        r -= PROB_COMMON;
        if (r < PROB_BOSS) return "BOSS";
        return "SAFE";
    }

    // Skapar ett monster baserat på hero level (kan skalas)
    public static Monster createCommonMonster(int heroLevel) {
        // Kan variera med heroLevel - här använder vi Goblin som exempel
        return new Goblin(Math.max(1, heroLevel));
    }

    public static Monster createBoss(int heroLevel) {
        return new Boss("Ominous Overlord", Math.max(1, heroLevel));
    }
}
