# Dr. Mario

A clone of Dr. Mario built with JavaFX.

![Version](https://img.shields.io/badge/version-1.0-blue)
![Java](https://img.shields.io/badge/Java-21-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21-green)
![Progress](https://img.shields.io/badge/progress-100%25-green)

**Status:** 🚧 Work in Progress

## About

A classic puzzle game where you match 4+ same-colored pills to clear them.
Built from scratch in Java with JavaFX for learning purposes.

## Progress

### Model ✅
- [x] `PillColor` — enum (RED, BLUE, GREEN)
- [x] `Cell` — single cell (EMPTY, PILL, VIRUS)
- [x] `Board` — 8x16 grid
- [x] `Direction` — enum (HORIZONTAL, VERTICAL, HORIZONTAL_REVERSED, VERTICAL_REVERSED)
- [x] `Capsule` — two-colored pill with 360° rotation
- [x] `Position` — immutable coordinates

### Logic ✅
- [x] `Gravity` — collision detection, movement validation
- [x] `Matcher` — find 4+ same-colored groups (BFS)
- [x] `VirusSpawner` — generate viruses per level
- [x] `ScoreManager` — score, level, and speed tracking
- [x] `GameEngine` — central game logic with states

### UI ✅
- [x] `GamePanel` — JavaFX Canvas rendering
- [x] `KeyHandler` — keyboard controls (arrows, space)
- [x] `GameLoop` — AnimationTimer

### Gameplay ✅
- [x] Falling capsules
- [x] Rotation (4 states, 360°)
- [x] Match detection (4+)
- [x] Viruses + score
- [x] Level complete screen
- [x] Game Over screen
- [x] Multiple levels

### Improvements 🚧
- [ ] **Gravity bug fix** (planned for v1.1)
- [ ] Win condition
- [ ] Virus disappearance animation
- [ ] Sound effects
- [ ] Pause menu

## Known Issues

- [ ] **Gravity bug**: Blocks below a match also fall (planned for v1.1)
- [ ] No animation when viruses disappear

## Version History

### v1.0 — Playable Game 🎉
- Complete Dr. Mario clone
- All core gameplay implemented
- Known issue: gravity bug (planned for v1.1)
- First release!

### v0.6 — Game Over + Level Complete Fix ✅
- Add `GAME OVER` screen with `R` to restart
- Fix `LEVEL COMPLETE` appears immediately after last virus
- Replace `stepGravity` with `applyGravity` (partial fix)
- Improve game state management: `PLAYING`, `ANIMATING`, `LEVEL_COMPLETE`, `GAME_OVER`
- Known issue: blocks below match still fall (planned for v1.1)

### v0.5 — Level Complete + Smooth Gravity ✅
- Add `State.LEVEL_COMPLETE` for level transition
- Add `LEVEL COMPLETE!` screen with SPACE prompt
- Add 4-state capsule rotation (360°)
- Add `ANIMATING` state for smooth gravity
- Add `stepGravity` for falling blocks
- Remove `FALLING...` debug message

### v0.4 — JavaFX UI ✅
- Add `GamePanel` — Canvas rendering
- Add `KeyHandler` — keyboard controls
- Add `AnimationTimer` game loop
- Add `Launcher` — JavaFX workaround
- Game is now playable!

### v0.3 — GameEngine ✅
- Add `GameEngine` — central game logic
- Game lifecycle: start, tick, lock, match, level up
- Add `ScoreManager` — score, levels, speed
- Add `VirusSpawner` — virus generation

### v0.2 — Matcher ✅
- Add `Matcher` — find 4+ groups (BFS)
- Add `Gravity` — collision detection
- Add `Position` — immutable coordinates
- Fix: `DIRECTIONS` array (right was duplicated)

### v0.1 — Model ✅
- Add `PillColor`, `Cell`, `Board`, `Direction`, `Capsule`

### v1.0 — Playable Game ⏳
- Gravity bug fix
- Win condition
- Sound effects
- All known issues fixed

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

[DANIL-lab1](https://github.com/DANIL-lab1)

## License

MIT License
