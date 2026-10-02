# Dr. Mario

A clone of Dr. Mario built with JavaFX.

**Status:** 🚧 Work in Progress

## About

A classic puzzle game where you match 4+ same-colored pills to clear them.
Built from scratch in Java with JavaFX for learning purposes.

## Progress

### Model ✅
- [x] `PillColor` — enum (RED, BLUE, GREEN)
- [x] `Cell` — single cell (EMPTY, PILL, VIRUS)
- [x] `Board` — 8x16 grid
- [x] `Direction` — enum (HORIZONTAL, VERTICAL)
- [x] `Capsule` — two-colored pill with rotation

### Logic ✅
- [x] `Gravity` — collision detection, movement validation
- [x] `Matcher` — find 4+ same-colored groups
- [x] `VirusSpawner` — generate viruses
- [x] `ScoreManager` — score and level tracking
- [x] `GameEngine` — central game logic

### UI 🚧
- [ ] `GamePanel` — JavaFX Canvas rendering
- [ ] `KeyHandler` — keyboard controls (arrows, space)
- [ ] `GameLoop` — AnimationTimer
- [ ] Animations — falling, clearing

### Gameplay ⏳
- [ ] Levels
- [ ] Score
- [ ] Game Over
- [ ] Win condition

## Version History

### v0.1 — Model ✅
- Model: `PillColor`, `Cell`, `Board`, `Direction`, `Capsule`

### v0.2 — Matcher ✅
- Logic: `Matcher` — find 4+ groups (BFS)
- Logic: `Gravity` — collision detection
- Model: `Position` — immutable coordinates
- Fix: `DIRECTIONS` array (right was duplicated)

### v0.3 — VirusSpawner ✅
- Logic: `VirusSpawner` — random virus placement
- Logic: `VirusSpawner` — viruses per level formula
- Model: `Board.addVirus()` method

### v0.4 — ScoreManager ✅
- Logic: `ScoreManager` — score tracking
- Logic: `ScoreManager` — level progression
- Logic: `ScoreManager` — speed increase per level
- Scoring: +10 per pill, +100 per virus, +1000 per level

### v0.5 — GameEngine ✅
- Logic: `GameEngine` — central class connecting all logic
- Logic: `ScoreManager` — score, levels, speed
- Logic: `VirusSpawner` — virus generation
- Game lifecycle: start, tick, lock, match, level up

### v1.0 — Playable Game ⏳
- JavaFX UI: `GamePanel`, `KeyHandler`, `GameLoop`
- Full game loop with animations
- Game Over screen
- Win condition
- Sound effects

## Technologies

- Java 21
- JavaFX 21
- Gradle
- JUnit 5 (planned)

## How to run

```bash
git clone https://github.com/DANIL-lab1/DrMario.git
cd DrMario
./gradlew run
```

## Author
DANIL-lab1

## License 
MIT License
