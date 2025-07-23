package game.engine;

import game.tiles.Square;
import game.tiles.VolcanoCard;
import java.util.ArrayList;

/**
 * Builder class for creating a VolcanoCard object.
 */
public class VolcanoCardBuilder {

  /**
   * The list of squares for the  VolcanoCard.
   */
  private final ArrayList<Square> squares = new ArrayList<>();

  /**
   * Constructs a new VolcanoCardBuilder.
   */
  public VolcanoCardBuilder() {
  }

  /**
   * Adds a Square to the list of squares for the  VolcanoCard.
   *
   * @param square the Square to be added
   * @return this VolcanoCardBuilder instance, allowing for method chaining
   */
  public VolcanoCardBuilder setSquare(Square square) {
    squares.add(square);
    return this;
  }

  /**
   * Builds and returns a new VolcanoCard object with the specified number of rows and columns. This
   * method clears the list of squares after the VolcanoCard is created.
   *
   * @param rows the number of rows in the VolcanoCard
   * @param cols the number of columns in the VolcanoCard
   * @return a new VolcanoCard object containing the current list of squares
   */
  public VolcanoCard getVolcanoCard(int rows, int cols) {
    ArrayList<Square> copy = (ArrayList<Square>) squares.clone();
    squares.clear();
    return new VolcanoCard(copy, rows, cols);
  }

  /**
   * Builds and returns a new VolcanoCard object with the specified number of rows and columns. This
   * method clears the list of squares after the VolcanoCard is created.
   *
   * @param caveIndex the index of cave on the volcano
   * @param rows      the number of rows in the VolcanoCard
   * @param cols      the number of columns in the VolcanoCard
   * @return a new VolcanoCard object containing the current list of squares
   */
  public VolcanoCard getVolcanoCard(int caveIndex, int rows, int cols) {
    ArrayList<Square> copy = (ArrayList<Square>) squares.clone();
    squares.clear();
    return new VolcanoCard(copy, caveIndex, rows, cols);
  }

}
