package com.game;

/**
 * Goblin - konkret monster-typ.
 * Enkel fiende; ingen avancerad specialmove utöver ett litet kritiskt slag ibland.
 */
public class Goblin extends Monster {

    public Goblin(int level) {
        // Konstruktor anropar super()
        super("Goblin", level, 30 + (level-1)*5, 8 + (level-1)*2, 2 + (level-1));
    }

    @Override
    public int specialMove(Character target) {
        // Goblins special: 20% chans att göra 2x attack (kritiskt)
        int roll = RNG.nextInt(100);
        if (roll < 20) {
            int damage = Math.max(1, (this.attack * 2) - target.getDefenseStat());
            int actual = target.receiveDamage(damage);
            System.out.println("[Goblin special] Goblin performs a vicious strike! Damage: " + actual);
            return actual;
        } else {
            // annars vanlig attack
            return this.attack(target);
        }
    }
}
