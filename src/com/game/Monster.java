package com.game;

import java.util.Random;

/**
 * Monster - abstrakt klass för monster. Innehåller grundläggande beteenden
 * och en abstrakt specialMove som konkreta monster måste implementera.
 */
public abstract class Monster extends Character {
    protected static final Random RNG = new Random();

    public Monster(String name, int level, int maxHp, int attack, int defense) {
        super(name, level, maxHp, attack, defense);
    }

    // Monster utför sin attack mot ett target
    @Override
    public int attack(Character target) {
        // Bas-damage: monster attack minus target defense
        int base = this.attack;
        int damage = Math.max(1, base - target.getDefenseStat());
        int actual = target.receiveDamage(damage);
        return actual;
    }

    // Varje monster kan ha en special attack/aktion - konkret klass implementerar detta
    public abstract int specialMove(Character target);
}
