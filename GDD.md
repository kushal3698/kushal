# Game Design Document: Chrono-Dharma: The Past Heals

## 1. Game Overview
**Title:** Chrono-Dharma: The Past Heals
**Genre:** 2D Action-RPG
**Core Concept:** A time-travel adventure where the protagonist travels to ancient Indian eras (Treta Yuga, Dvapara Yuga) to collect rare materials to heal a fatal injury in the present day.

## 2. Story & Characters
**Protagonist:** A man from the present day, fatally injured, using a Time Machine as a last resort. He is desperate and willing to disrupt the timeline to save his own life.
**Goal:** Collect specific "curing materials" from the past.
**Villain/Antagonist:** Time itself and the guardians of history who see the protagonist as an anomaly.
**Theme (Villain Hero):** The protagonist is fighting for survival, but his actions threaten the stability of history. Is he the hero of his own story, or the villain of the world's timeline?

## 3. Level Progression & Eras

### Hub: The Present Day Lab
- **Function:** Safe zone, crafting station, Time Machine control.
- **Activities:** Check health, craft cures, select era.

### Era 1: Treta Yuga (Ramayana Era)
- **Setting:** Dandakaranya (Ancient Dense Forest).
- **Visuals:** Lush green, giant trees, ancient ruins, river streams.
- **Objective:** Retrieve the **Sanjeevani Root**.
- **Boss:** **Kumbhakarna's Shadow** (A sleeping giant that wakes up if you make too much noise).

### Era 2: Dvapara Yuga (Mahabharata Era)
- **Setting:** Kurukshetra (Battlefield) / Royal Courts.
- **Visuals:** War-torn lands, chariots, arrows in the ground, grand palaces.
- **Objective:** Retrieve the **Celestial Nectar (Amrit)** residue.
- **Boss:** **Ashwatthama's Curse** (An immortal warrior wandering in pain).

### Failure Level: The Void
- **Condition:** Reached if Time Machine is used with insufficient Flux Energy.
- **Setting:** Abstract, dark, glitchy.
- **Exit:** Survive waves of "Time Eaters" to recharge emergency power.

## 4. Enemies (5 per Era)

### Treta Yuga (Forest)
1.  **Vanara Scout:** Fast, agile, attacks with stones.
2.  **Rakshasa Grunt:** Strong, slow, wields a club.
3.  **Poisonous Naga:** Snake-like, shoots venom.
4.  **Forest Spirit (Yakshini):** Deceptive, lures player into traps.
5.  **Giant Eagle (Jatayu's Kin):** Aerial attacks.

### Dvapara Yuga (Battlefield)
1.  **Fallen Soldier:** Undead warrior with broken armor.
2.  **Charioteer:** Rushes the player with a spear.
3.  **War Elephant:** Tanky, charges at the player.
4.  **Dark Brahmin:** Uses dark mantras (spells) to debuff.
5.  **Gandharva Illusionist:** Creates clones of themselves.

## 5. Core Mechanics

### Time Travel
- **Mechanism:** Player uses the "Time Hub" to select an Era.
- **Cost:** Requires **Flux Energy**.
- **Source:** Flux Energy is collected by defeating enemies and finding anomalies in the past.

### Combat
- **Style:** Real-time hack-and-slash with dodge mechanics.
- **Weapons:**
    - *Present:* Stun Baton (Low damage, high stun).
    - *Past:* Acquired weapons like **Gada (Mace)** or **Dhanush (Bow)**.

### Crafting (The Cure)
- **Logic:** The player needs to combine items in the Lab.
- **Recipe:**
    1.  **Base:** Sanjeevani Root (Heals tissue).
    2.  **Catalyst:** Celestial Nectar (Restores vitality).
    3.  **Stabilizer:** Flux Energy (Harmonizes the timeline).
- **Process:**
    - Collect ingredients.
    - Return to Lab.
    - Play a mini-game to synthesize the cure. Failure results in resource loss.

## 6. Technical Requirements
- **Engine:** Java (LibGDX)
- **Architecture:** Entity Component System (ECS)
- **Data Structures:** 2D Arrays for map generation.
