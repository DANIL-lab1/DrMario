package drmario.model;

/**
 * Represents the color of a pill half or a virus.
 * <p>
 * Dr. Mario uses three colors: RED, BLUE, YELLOW.
 * Matching 4+ of the same color clears them.
 *
 * @author DANIL-lab1
 * @version 1.0
 */
public enum PillColor {
    RED,
    BLUE,
    GREEN;
    
    /**
     * Returns a random color.
     *
     * @return a random PillColor
     */
    public static PillColor random() {
        PillColor[] colors = values();
        return colors[(int) (Math.random() * colors.length)];
    }
}