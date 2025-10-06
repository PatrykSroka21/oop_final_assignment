# Dokumentation – JavaRPG

## 🏹 Översikt
Detta projekt är ett textbaserat rollspel (RPG) skrivet i **Java**, skapat för att demonstrera förståelse för **objektorienterad programmering (OOP)**.  
Spelet låter spelaren styra en hjälte som möter olika monster i slumpmässiga encounters, levlar upp och kan vinna spelet genom att nå **level 10**.

Koden är strukturerad enligt god programmeringspraxis: arv, abstraktion, inkapsling och tydlig ansvarsfördelning mellan klasser.

---

## ⚙️ Syfte
Projektet skapades med följande mål:
- Visa korrekt användning av **arv, abstrakta klasser och inkapsling**.
- Implementera ett fungerande **leveling- och stridssystem**.
- Visa hur man bygger upp ett strukturerat, läsbart **Java-projekt** med flera filer och paket.
- Ge spelaren ett interaktivt textbaserat äventyr där val och slump avgör utfallet.

---

## 🧩 Projektstruktur
```text
JavaRPG/
└─ src/
   └─ com/
      └─ game/
         ├─ Character.java
         ├─ Hero.java
         ├─ Monster.java
         ├─ Goblin.java
         ├─ Boss.java
         ├─ Weapon.java
         ├─ Encounter.java
         └─ GameMain.java
