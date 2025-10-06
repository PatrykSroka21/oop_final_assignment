package com.game;

/**
 * Weapon - konkret klass för vapen. Enkelt attribut: namn och damageBonus.
 * Hjälper till i hjältens damage-beräkningar.
 */
public class Weapon {
    private String name;
    private int damageBonus;

    public Weapon(String name, int damageBonus) {
        this.name = name;
        this.damageBonus = damageBonus;
    }

    public String getName() { return name; }
    public int getDamageBonus() { return damageBonus; }

    @Override
    public String toString() {
        return name + " (+" + damageBonus + " atk)";
    }
}
