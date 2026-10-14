# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.0.0] - 2026-10-14

### Added
- Complete Dr. Mario clone built with JavaFX
- 8x16 game board
- Two-colored capsules with 360° rotation (4 states)
- Falling capsule mechanics with collision detection
- Match detection for 4+ same-colored pills/viruses (BFS algorithm)
- Virus spawning per level (`4 * level`, max 84)
- Score system:
  - +10 points per pill
  - +100 points per virus
  - +1000 bonus per level
- Level progression with increasing speed
- Game Over screen with `R` to restart
- Level Complete screen with `SPACE` to continue
- JavaFX Canvas rendering
- Keyboard controls (arrows, space)
- AnimationTimer game loop

### Known Issues
- Blocks below a match also fall (gravity bug) — planned for v1.1
- No animation when viruses disappear

### Technologies
- Java 21
- JavaFX 21
- Gradle

## [0.6.0] - 2026-10-13

### Added
- `GAME OVER` screen with `R` to restart

### Fixed
- `LEVEL COMPLETE` appears immediately after last virus
- Replace `stepGravity` with `applyGravity` (partial fix)

## [0.5.0] - 2026-10-13

### Added
- `State.LEVEL_COMPLETE` for level transition
- `LEVEL COMPLETE!` screen with SPACE prompt
- 4-state capsule rotation (360°)
- `ANIMATING` state for smooth gravity
- `stepGravity` for falling blocks

### Changed
- Remove `FALLING...` debug message

## [0.4.0] - 2026-10-12

### Added
- `GamePanel` — JavaFX Canvas rendering
- `KeyHandler` — keyboard controls
- `AnimationTimer` game loop
- `Launcher` — JavaFX workaround
- Game is now playable!

## [0.3.0] - 2026-10-12

### Added
- `GameEngine` — central game logic
- Game lifecycle: start, tick, lock, match, level up
- `ScoreManager` — score, levels, speed
- `VirusSpawner` — virus generation

## [0.2.0] - 2026-10-11

### Added
- `Matcher` — find 4+ groups (BFS)
- `Gravity` — collision detection
- `Position` — immutable coordinates

### Fixed
- `DIRECTIONS` array (right was duplicated)

## [0.1.0] - 2026-10-10

### Added
- `PillColor`, `Cell`, `Board`, `Direction`, `Capsule`
