package game.tiles;

import java.awt.Color;

/**
 * Abstract class representing a cave on the game board.
 */
public abstract class Cave extends Square {

  private final Color colour;

  /**
   * Constructs a cave square with the specified icon and color.
   *
   * @param icon   The path to the icon file representing the cave square.
   * @param colour The color of the cave square.
   */
  public Cave(String icon, Color colour) {
    super(icon);
    this.colour = colour;
  }

  /**
   * Gets the color of the cave square.
   *
   * @return The color of the cave square.
   */
  public Color getColour() {
    return colour;
  }
}
