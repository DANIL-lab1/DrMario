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

### Logic 🚧
- [x] `Gravity` — collision detection, movement validation
- [x] `Matcher` — find 4+ same-colored groups
- [x] `VirusSpawner` — generate viruses
- [x] `ScoreManager` — score and level tracking
- [ ] `GameEngine` — central game logic

### UI ⏳
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
- Logic: `Matcher` — find group of 4+ (BFS)
- Logic: `Gravity` — collision check
- Model: `Position` — coordinates

### v0.3 — GameEngine 🚧
- Logic: `GameEngine` — central class connecting all logic
- Logic: `ScoreManager` — score, levels, speed
- Logic: `VirusSpawner` — virus generation

### v1.0 — Playable Game ⏳
- JavaFX UI
- Full game loop
- Levels, score, game over

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
