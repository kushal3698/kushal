## Copilot / AI agent instructions for Chrono-Dharma (molten-galileo)

This file gives concise, actionable guidance for an AI code assistant to be productive in this repository.

Detected environment (from recent VS Code/Gradle server output):
- Gradle server configured: Gradle Version 9.2.0 (via VS Code Java/Gradle integration).
- Java Home seen by Gradle: C:\\Users\\kushw\\.vscode\\extensions\\redhat.java-1.50.0-win32-x64\\jre\\21.0.9-win32-x86_64 (JRE 21).
- Note: The interactive shell may not have the `gradle` CLI on PATH (running `gradle run` in PowerShell returned "gradle: command not found"). Prefer using the IDE Gradle integration or adding a Gradle wrapper for reproducible CLI runs.

1) Big picture (why & structure)
- This is a small Java/LibGDX 2D action-RPG prototype using a simple ECS-like model.
- Major components:
  - `MainGame` (entry, holds `SpriteBatch` and `ScreenManager`) — src/main/java/com/chronodharma/MainGame.java
  - `ScreenManager` (caches screens; switch with `setScreen(ScreenType)`) — src/main/java/com/chronodharma/screens/ScreenManager.java
  - Levels: `RamayanaLevel`, `MahabharataLevel`, `VoidLevel`, `PresentDayHub` (under `screens/`) — runtime state and gameplay lives here
  - `LevelGenerator` (2D int grid map generator; TILE_* constants) — src/main/java/com/chronodharma/level/LevelGenerator.java
  - Lightweight ECS: `ecs.Entity` used as a simple entity abstraction (not a full ECS framework) — src/main/java/com/chronodharma/ecs/
  - Gameplay systems: `systems/` (InventoryManager, TimeMachine, CraftingStation, etc.)
  - Desktop launcher: `DesktopLauncher` (LWJGL3) — src/main/java/com/chronodharma/desktop/DesktopLauncher.java

2) Developer workflows (how to build/run/debug)
- Prerequisite: JDK 17+ (README_FIX_JAVA.txt explicitly instructs installing Temurin/Adoptium and setting JAVA_HOME). Verify with `java -version`.
- Build/run:
  - There is a `build.gradle` using the `application` plugin and `mainClass` set to `com.chronodharma.desktop.DesktopLauncher`.
  - This project currently has no Gradle wrapper; run with a local Gradle installation: `gradle run` from the repo root to launch the desktop app.
  - Alternatively, run `com.chronodharma.desktop.DesktopLauncher` directly from your IDE (IntelliJ/VS Code Java debug) to set breakpoints.
- Debug tips:
  - Break into `MainGame.create()` and `ScreenManager.setScreen(...)` to trace screen initialization. `ScreenManager` caches screens (except `VoidLevel`) — be mindful of persistent state.
  - Many systems use `System.out.println` for simple logging (e.g., `InventoryManager.addItem`); grepping for println is a fast way to find observable behavior.

3) Project-specific conventions & patterns
- Screen caching: `ScreenManager` caches PresentDayHub, RamayanaLevel, MahabharataLevel to avoid re-creating them; `VoidLevel` is intentionally created each time. When changing screen state, consider whether cached instances need reset logic.
- Level representation: `LevelGenerator` uses an `int[][]` with TILE_* constants (0=PATH,1=WALL,2=ENEMY,3=OBJECTIVE). Functions often mutate and return the grid. When adding features to levels, follow the same tile constants and `placeEnemies` pattern.
- ECS approach: `ecs.Entity` implements a simple object-based entity with a Vector2 position and Type enum. There is not a separate Component system — add fields carefully and keep updates contained to `systems/` classes.
- Inventory: `systems.InventoryManager` stores `GameItem` objects in a List and uses `itemID` for lookup/removal. Look for `getItemID()` and name usage when adding or removing items.

4) Integration points & external resources
- Assets are in the `assets/` directory (e.g., `uiskin.atlas`, `uiskin.json`) and `src/main/resources/` — coordinate UI skins and texture atlas paths with LibGDX asset loaders.
- Desktop runtime uses LWJGL3 backend (see `DesktopLauncher`). Mobile or web backends are not configured in this repo.

5) Known repo realities (found from files)
- `build.gradle` has no external dependencies declared; LibGDX libs may be expected but are not listed. If builds fail due to missing dependencies, add LibGDX artifacts in `dependencies` or use an IDE that adds them.
- No Gradle wrapper present; use local Gradle or add a wrapper (`gradle wrapper`) if you need reproducible builds.

6) Quick examples to reference in patches/edits
- To change startup screen: modify `MainGame.create()` where `screenManager.setScreen(ScreenManager.ScreenType.PRESENT_DAY_HUB)` is called.
- To add a new tile type: add a constant in `LevelGenerator` and update `generateForestLevel()` and any rendering code in the level screens.
- To reset a cached screen: add a `reset()` method to the screen (e.g., `RamayanaLevel.reset()`) and call it before reusing cached instances in `ScreenManager.setScreen()`.

7) When making PRs / automated edits
- Keep changes localized: screens are stateful and often cached; tests (if added) should instantiate fresh screen objects to avoid test pollution.
- Prefer minimal, compile-safe edits (fix imports / small refactors). If introducing third-party libs (LibGDX, logging), update `build.gradle` accordingly and document why.

If any area is unclear (missing dependencies, intended rendering pipeline, or desired behavior for cached screens), tell me which part you'd like expanded and I will update this file.
