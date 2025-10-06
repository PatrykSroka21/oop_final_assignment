package com.game;

/**
 * Character - abstrakt basklass för alla karaktärer i spelet.
 * Innehåller gemensamma egenskaper och ett abstrakt attack-ramverk.
 */
public abstract class Character {
    protected String name;
    protected int level;
    protected int maxHp;
    protected int hp;
    protected int attack;
    protected int defense;

    public Character(String name, int level, int maxHp, int attack, int defense) {
        this.name = name;
        this.level = level;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
    }

    // Abstrakt metod: varje karaktär implementerar sin attack mot en mål-karaktär.
    public abstract int attack(Character target);

    // Tar skada och returnerar faktiskt skada applicerad
    public int receiveDamage(int dmg) {
        int actual = Math.max(0, dmg - this.defense);
        this.hp = Math.max(0, this.hp - actual);
        return actual;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    // Getters / Setters (inkapsling)
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getMaxHp() { return maxHp; }
    public int getHp() { return hp; }
    public int getAttackStat() { return attack; }
    public int getDefenseStat() { return defense; }

    protected void setHp(int hp) { this.hp = hp; }
    protected void setMaxHp(int maxHp) { this.maxHp = maxHp; }
}
