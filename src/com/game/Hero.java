package com.game;

/**
 * Hero - spelarens karaktär. Hanterar experience, leveling, utrustning och attacker.
 * Leveling: varje 100 XP => levelup, max level 10. Vid levelup: +20 maxHp och hp fylls.
 */
public class Hero extends Character {
    private int xp;
    private Weapon weapon;
    public static final int XP_PER_LEVEL = 100;
    public static final int MAX_LEVEL = 10;

    public Hero(String name) {
        super(name, 1, 100, 12, 4); // startvärden
        this.xp = 0;
        this.weapon = new Weapon("Basic Sword", 2);
    }

    @Override
    public int attack(Character target) {
        int raw = this.attack + (weapon != null ? weapon.getDamageBonus() : 0);
        int damage = Math.max(1, raw - target.getDefenseStat());
        int actual = target.receiveDamage(damage);
        return actual;
    }

    // Hjälpmetod: erhåll XP och hantera levelups
    public void gainXp(int amount) {
        if (this.level >= MAX_LEVEL) return;
        this.xp += amount;
        System.out.println("Du får " + amount + " XP! (Totalt: " + xp + ")");
        while (this.xp >= XP_PER_LEVEL && this.level < MAX_LEVEL) {
            this.xp -= XP_PER_LEVEL;
            levelUp();
        }
    }

    private void levelUp() {
        this.level += 1;
        this.maxHp += 20;
        this.hp = this.maxHp;
        System.out.println("LEVEL UP! Du är nu level " + this.level + ". Max HP ökades till " + this.maxHp);
        if (this.level >= MAX_LEVEL) {
            System.out.println("Grattis! Du nådde level " + MAX_LEVEL + " och vann spelet!");
        }
    }

    public int getXp() { return xp; }
    public Weapon getWeapon() { return weapon; }
    public void equipWeapon(Weapon w) { this.weapon = w; }

    // Basic heal (t.ex. efter safe place)
    public void healFull() {
        this.hp = this.maxHp;
    }
}
