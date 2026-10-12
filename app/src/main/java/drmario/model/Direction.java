package drmario.model;

/**
 * Represents the orientation of a capsule.
 * <p>
 * A capsule consists of two halves:
 * <ul>
 *     <li>HORIZONTAL: [A][B] — A left, B right</li>
 *     <li>HORIZONTAL_REVERSED: [B][A] — B left, A right</li>
 *     <li>VERTICAL: [A] on top, [B] below</li>
 *     <li>VERTICAL_REVERSED: [B] on top, [A] below</li>
 * </ul>
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public enum Direction {
    HORIZONTAL,
    VERTICAL,
    HORIZONTAL_REVERSED,
    VERTICAL_REVERSED
}