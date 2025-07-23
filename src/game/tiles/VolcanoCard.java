package game.tiles;

import static game.utils.GameUtils.SQUARE_SIZE;

import game.engine.Savable;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JPanel;

/**
 * Represents volcano card on the game board Each card contains a grid of squares and optionally a
 * cave. It has neighboring cards to the left and right.
 */
public class VolcanoCard implements Savable {

  private final List<Square> squares;
  private Cave cave;
  private VolcanoCard rightNeighbour;
  private VolcanoCard leftNeighbour;
  private JPanel panel; // Main panel for composition
  private int caveIndex = 1;
  private final int numSquares;

  /**
   * Constructs a VolcanoCard with the specified squares, cave, neighbors, rows, columns, and
   * orientation.
   *
   * @param squares        The list of squares to populate the card.
   * @param cave           The cave square to be placed on the card (can be null if no cave).
   * @param rightNeighbour The card to the right of this card (can be null if no right neighbor).
   * @param leftNeighbour  The card to the left of this card (can be null if no left neighbor).
   * @param rows           The number of rows in the grid layout.
   * @param cols           The number of columns in the grid layout.
   * @param reverse        Indicates whether to reverse the order of squares and cave placement.
   */
  public VolcanoCard(List<Square> squares, Cave cave, VolcanoCard rightNeighbour,
      VolcanoCard leftNeighbour, int rows, int cols, boolean reverse) {
    this.squares = squares;
    this.numSquares = squares.size();
    this.cave = cave;
    this.rightNeighbour = rightNeighbour;
    this.leftNeighbour = leftNeighbour;
    addSquaresToCard(reverse, rows, cols);
  }

  /**
   * Constructs a VolcanoCard with the specified squares rows, columns
   *
   * @param squares The list of squares to populate the card.
   * @param rows    The number of rows in the grid layout.
   * @param cols    The number of columns in the grid layout.
   */
  public VolcanoCard(List<Square> squares, int rows, int cols) {
    this.panel = new JPanel(new GridLayout(rows, cols));
    this.squares = squares;
    this.numSquares = squares.size();
  }

  /**
   * Constructs a VolcanoCard with the specified squares rows, columns
   *
   * @param squares The list of squares to populate the card.
   * @param rows    The number of rows in the grid layout.
   * @param cols    The number of columns in the grid layout.
   */
  public VolcanoCard(List<Square> squares, int caveIndex, int rows, int cols) {
    this.panel = new JPanel(new GridLayout(rows, cols));
    this.caveIndex = caveIndex;
    this.squares = squares;
    this.numSquares = squares.size();
  }

  /**
   * Creates an iterator for the VolcanoCard to iterate over its squares.
   *
   * @return A VolcanoCardIterator initialised to start from position -1.
   */
  public VolcanoCardIterator createIterator() {
    return new VolcanoCardIterator(this, -1);
  }

  public Color getCaveColour() {
    if (this.cave != null) {
      return this.cave.getColour();
    }
    return null;
  }

  /**
   * Gets the index of the cave.
   *
   * @return the index of the cave
   */
  public int getCaveIndex() {
    return caveIndex;
  }

  /**
   * Gets the number of squares on the volcano card
   *
   * @return number of squares on volcano card
   */
  public int getNumSquares() {
    return numSquares;
  }

  /**
   * Sets a VolcanoCard with the specified cave ,rows ,columns , and orientation.
   *
   * @param cave    The cave square to be placed on the card (can be null if no cave).
   * @param rows    The number of rows in the grid layout.
   * @param cols    The number of columns in the grid layout.
   * @param reverse Indicates whether to reverse the order of squares and cave placement.
   */
  public void initialiseVolcano(Cave cave, boolean reverse, int rows, int cols) {
    this.cave = cave;
    addSquaresToCard(reverse, rows, cols);
  }

  /**
   * Sets a VolcanoCard with the specified cave ,rows ,columns , and orientation.
   *
   * @param rows    The number of rows in the grid layout.
   * @param cols    The number of columns in the grid layout.
   * @param reverse Indicates whether to reverse the order of squares and cave placement.
   */
  public void initialiseVolcano(boolean reverse, int rows, int cols) {
    addSquaresToCard(reverse, rows, cols);
  }

  /**
   * Retrieves the square at the specified index.
   *
   * @param index The index of the square to retrieve.
   * @return The square at the specified index.
   */
  public Square getSquare(int index) {
    return index > -1 ? this.squares.get(index) : this.cave;
  }

  /**
   * Retrieves the card to the right of this card.
   *
   * @return The card to the right of this card.
   */
  public VolcanoCard getRightNeighbour() {
    return rightNeighbour;
  }

  /**
   * Sets the card to the right of this card.
   *
   * @param rightNeighbour The card to set as the right neighbor.
   */
  public void setRightNeighbour(VolcanoCard rightNeighbour) {
    this.rightNeighbour = rightNeighbour;
  }

  /**
   * Retrieves the card to the left of this card.
   *
   * @return The card to the left of this card.
   */
  public VolcanoCard getLeftNeighbour() {
    return leftNeighbour;
  }

  /**
   * Sets the card to the left of this card.
   *
   * @param leftNeighbour The card to set as the left neighbor.
   */
  public void setLeftNeighbour(VolcanoCard leftNeighbour) {
    this.leftNeighbour = leftNeighbour;
  }

  /**
   * Retrieves the cave square on this card.
   *
   * @return The cave square on this card.
   */
  public Cave getCave() {
    return cave;
  }

  /**
   * Sets the cave square on this card.
   *
   * @param cave The cave square to set.
   */
  public void setCave(Cave cave) {
    this.cave = cave;
  }

  /**
   * Adds Squares to panel for a vertical reversed Volcano Card.
   */
  public void addSquaresWest() {
    this.panel = new JPanel(new GridLayout(this.numSquares, 2));
    for (int i = numSquares - 1; i > -1; i--) {
      if (i == (this.numSquares - this.caveIndex - 1) && cave != null) {
        this.panel.add(cave.getPanel());
      } else {
        JPanel blankPanel = new JPanel();
        blankPanel.setPreferredSize(SQUARE_SIZE);
        this.panel.add(blankPanel);
      }
      this.panel.add(squares.get(i).getPanel());
    }
  }

  /**
   * Adds Squares to panel for a vertical Volcano Card.
   */
  public void addSquaresEast() {
    this.panel = new JPanel(new GridLayout(this.numSquares, 2));
    for (int i = 0; i < numSquares; i++) {
      this.panel.add(squares.get(i).getPanel());
      if (i == this.caveIndex && cave != null) {
        this.panel.add(cave.getPanel());
      } else {
        JPanel blankPanel = new JPanel();
        blankPanel.setPreferredSize(SQUARE_SIZE);
        this.panel.add(blankPanel);
      }
    }
  }

  /**
   * Adds Squares to panel for a reversed horizontal Volcano Card.
   */
  public void addSquaresSouth() {
    this.panel = new JPanel(new GridLayout(2, this.numSquares));
    for (int i = numSquares - 1; i > -1; i--) {
      this.panel.add(squares.get(i).getPanel());
    }
    for (int i = 0; i < numSquares; i++) {
      if (i == (this.numSquares - this.caveIndex - 1) && cave != null) {
        this.panel.add(cave.getPanel());
      } else {
        JPanel blankPanel = new JPanel();
        blankPanel.setPreferredSize(SQUARE_SIZE);
        this.panel.add(blankPanel);
      }
    }
  }

  /**
   * Adds Squares to panel for a normal horizontal Volcano Card.
   */
  public void addSquaresNorth() {
    this.panel = new JPanel(new GridLayout(2, this.numSquares));
    for (int i = 0; i < numSquares; i++) {
      if (i == this.caveIndex && cave != null) {
        this.panel.add(cave.getPanel());
      } else {
        JPanel blankPanel = new JPanel();
        blankPanel.setPreferredSize(SQUARE_SIZE);
        this.panel.add(blankPanel);
      }
    }
    for (int i = 0; i < numSquares; i++) {
      this.panel.add(squares.get(i).getPanel());
    }
  }

  /**
   * Populates the visual panel with squares and cave based on the specified orientation.
   *
   * @param reverse Indicates whether to reverse the order of squares and cave placement.
   * @param rows    The number of rows in the grid layout.
   * @param cols    The number of columns in the grid layout.
   */
  private void addSquaresToCard(boolean reverse, int rows, int cols) {
    if (reverse && rows > cols) {
      addSquaresWest();
    } else if (reverse) {
      addSquaresSouth();
    } else if (rows > cols) {
      addSquaresEast();
    } else {
      addSquaresNorth();
    }
  }

  /**
   * Returns the JPanel associated with the volcano card.
   *
   * @return The JPanel associated with the volcano card.
   */
  public JPanel getPanel() {
    return panel;
  }

  @Override
  public String saveState() {
    String result = "";
    for (Square square : this.squares) {
      result += square.saveState() + "_";
    }

    if (this.cave != null) {
      result += this.cave.saveState();
      result += "_" + getCaveIndex() + "_" + getNumSquares();
    } else {
      result += -1 + "_" + getNumSquares();
    }
    return result;
  }

  /**
   * Gets the list of squares
   *
   * @return list of squares
   */
  public List<Square> getSquares() {
    return squares;
  }
}
