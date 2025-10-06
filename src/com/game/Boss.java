package com.game;

/**
 * Boss - en kraftfullare monster-klass. Har 30% chans att göra dubbel damage som special.
 */
public class Boss extends Monster {

    public Boss(String name, int level) {
        super(name, level, 80 + (level-1)*15, 18 + (level-1)*4, 5 + (level-1)*2);
    }

    @Override
    public int specialMove(Character target) {
        // 30% chans för dubbel damage (enligt krav)
        int roll = RNG.nextInt(100);
        if (roll < 30) {
            int damage = Math.max(1, (this.attack * 2) - target.getDefenseStat());
            int actual = target.receiveDamage(damage);
            System.out.println("[BOSS SPECIAL] " + this.name + " använder dubbelattack! Gör " + actual + " skada!");
            return actual;
        } else {
            return this.attack(target);
        }
    }
}
