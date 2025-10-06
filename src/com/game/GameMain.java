package com.game;

import java.util.Scanner;

/**
 * GameMain - huvudklass som visar menyn och hanterar spelets loop.
 * Meny: 1) Venture (möten) 2) Visa hjälte 3) Byt vapen 4) Exit
 * Efter varje äventyr återvänder spelet till menyn.
 */
public class GameMain {
    private static Hero hero;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        hero = new Hero("Player");
        System.out.println("Välkommen till JavaRPG!");
        mainMenuLoop();
        System.out.println("Tack för att du spelade!");
    }

    private static void mainMenuLoop() {
        while (true) {
            System.out.println("\n--- HUVUDMENY ---");
            System.out.println("1) Venture ut i äventyret");
            System.out.println("2) Visa hjälte");
            System.out.println("3) Byt vapen");
            System.out.println("4) Avsluta");
            System.out.print("Välj: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    startAdventure();
                    if (hero.getLevel() >= Hero.MAX_LEVEL) {
                        // spel slut - vinn
                        System.out.println("Spelet avslutas eftersom du vann!");
                        return;
                    }
                    break;
                case "2":
                    showHero();
                    break;
                case "3":
                    changeWeaponMenu();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Ogiltigt val, försök igen.");
            }
        }
    }

    private static void showHero() {
        System.out.println("\n--- HERO STATUS ---");
        System.out.println("Namn: " + hero.getName());
        System.out.println("Level: " + hero.getLevel());
        System.out.println("HP: " + hero.getHp() + "/" + hero.getMaxHp());
        System.out.println("Attack: " + hero.getAttackStat() + " + " + hero.getWeapon().getDamageBonus());
        System.out.println("Defense: " + hero.getDefenseStat());
        System.out.println("XP: " + hero.getXp() + "/" + Hero.XP_PER_LEVEL);
        System.out.println("Vapen: " + hero.getWeapon());
    }

    private static void changeWeaponMenu() {
        System.out.println("\n--- BYT VAPEN ---");
        System.out.println("1) Short Sword (+2)");
        System.out.println("2) Long Sword (+5)");
        System.out.println("3) Great Axe (+8)");
        System.out.println("4) Behåll nuvarande");
        System.out.print("Välj: ");
        String c = scanner.nextLine().trim();
        switch (c) {
            case "1": hero.equipWeapon(new Weapon("Short Sword", 2)); break;
            case "2": hero.equipWeapon(new Weapon("Long Sword", 5)); break;
            case "3": hero.equipWeapon(new Weapon("Great Axe", 8)); break;
            case "4": System.out.println("Behåller nuvarande vapen."); return;
            default: System.out.println("Ogiltigt val."); return;
        }
        System.out.println("Utrustad: " + hero.getWeapon());
    }

    private static void startAdventure() {
        System.out.println("\nDu ger dig ut...");
        String type = Encounter.rollEncounter();
        switch (type) {
            case "COMMON":
                System.out.println("Ett monster dyker upp!");
                Monster m = Encounter.createCommonMonster(hero.getLevel());
                runBattle(m);
                break;
            case "BOSS":
                System.out.println("En boss dyker upp!");
                Monster boss = Encounter.createBoss(hero.getLevel());
                runBattle(boss);
                break;
            case "SAFE":
                System.out.println("Du hittade en säker plats! Du återhämtar dig fullständigt.");
                hero.healFull();
                // belöning lite xp för safe exploration
                hero.gainXp(10);
                break;
        }
    }

    private static void runBattle(Monster monster) {
        System.out.println("Möter: " + monster.getName() + " (HP: " + monster.getHp() + ")");
        boolean heroTurn = true; // hero attackerar först
        while (hero.isAlive() && monster.isAlive()) {
            if (heroTurn) {
                System.out.println("\nDin tur! (HP: " + hero.getHp() + ")");
                System.out.println("1) Attack  2) Stryk (flee - 50% chans att fly)");
                System.out.print("Val: ");
                String input = scanner.nextLine().trim();
                if (input.equals("1")) {
                    int dmg = hero.attack(monster);
                    System.out.println("Du attackerade och gjorde " + dmg + " skada!");
                } else if (input.equals("2")) {
                    if (Math.random() < 0.5) {
                        System.out.println("Du lyckades fly! Återvänder till menyn.");
                        return;
                    } else {
                        System.out.println("Flykt misslyckades!");
                    }
                } else {
                    System.out.println("Ogiltigt val, du förlorar din tur.");
                }
            } else {
                // Monster's turn - ibland använder specialMove
                if (monster instanceof Boss) {
                    // Boss har 30% chans för special dubbel attack (handled i specialMove)
                    int dmg = monster.specialMove(hero);
                    System.out.println(monster.getName() + " attackerar och gör " + dmg + " skada!");
                } else {
                    // Vanligt monster: 25% chans att använda specialMove (om definierad)
                    if (Math.random() < 0.25) {
                        int dmg = monster.specialMove(hero);
                        System.out.println(monster.getName() + " använder special och gör " + dmg + " skada!");
                    } else {
                        int dmg = monster.attack(hero);
                        System.out.println(monster.getName() + " attackerar och gör " + dmg + " skada!");
                    }
                }
            }

            // Visa båda HP efter tur
            System.out.println("Status -> " + hero.getName() + ": " + hero.getHp() + "/" + hero.getMaxHp()
                    + " | " + monster.getName() + ": " + monster.getHp() + "/" + monster.getMaxHp());

            heroTurn = !heroTurn;
        }

        if (hero.isAlive()) {
            System.out.println("\nDu besegrade " + monster.getName() + "!");
            // Belöning: xp beroende på monster nivå
            int xpReward = 20 + (monster.getLevel() - 1) * 10;
            hero.gainXp(xpReward);
        } else {
            System.out.println("\nDu dog... Du återhämtar dig men förlorar hälften av din XP som straff.");
            // Straff: förlora halva XP (men inte under 0)
            int currentXp = hero.getXp();
            hero.gainXp(- (currentXp/2)); // vi använder negativt för enkelhet (hanterar ej underflöde)
            // Återställ hjälten till full HP men level kvar
            hero.healFull();
        }
    }
}
