package game.entities;

import game.chitcards.BabyDragonCard;
import game.chitcards.BatCard;
import game.chitcards.PirateDragonCard;
import game.chitcards.SalamanderCard;
import game.chitcards.SpiderCard;
import game.chitcards.SwapCard;
import game.engine.GameEngine;
import game.engine.Savable;
import game.tiles.Cave;
import game.tiles.Square;
import game.tiles.VolcanoCard;
import game.tiles.VolcanoCardIterator;
import game.utils.GameUtils;
import java.awt.Color;
import java.util.Objects;
import javax.swing.ImageIcon;


/**
 * Represents a dragon in the game, capable of interacting with various game elements and moving
 * around the game board through volcano cards.
 */
public class Dragon implements Visitor, Savable {

  private final Cave cave;
  private final Color colour;
  private final ImageIcon icon;
  private VolcanoCardIterator volcanoCardIterator;

  /**
   * Constructs a new Dragon with a starting position and cave.
   *
   * @param volcanoCard The initial volcano card where the dragon starts.
   * @param cave        The cave associated with the dragon's home.
   */
  public Dragon(VolcanoCard volcanoCard, Cave cave) {
    this.volcanoCardIterator = volcanoCard.createIterator();
    this.cave = cave;
    this.colour = cave.getColour();
    this.icon = new ImageIcon(
        Objects.requireNonNull(getClass().getResource("/images/" + this.getColour().toUpperCase()
            + "Dragon.png")));
  }

  /**
   * Gets the volcano card iterator of the dragon.
   *
   * @return VolcanoCardIterator of the dragon.
   */
  public VolcanoCardIterator getVolcanoCardIterator() {
    return volcanoCardIterator;
  }

  /**
   * Sets the volcano card iterator of the dragon.
   *
   * @param volcanoCardIterator replacement volcanoCardIterator
   */
  public void setVolcanoCardIterator(VolcanoCardIterator volcanoCardIterator) {
    this.volcanoCardIterator = volcanoCardIterator;
  }

  /**
   * Gets the icon of the dragon.
   *
   * @return ImageIcon representing the dragon.
   */
  public ImageIcon getIcon() {
    return icon;
  }

  /**
   * Returns the color name of the dragon.
   *
   * @return The name of the dragon's color.
   */
  public String getColour() {
    return GameUtils.colorToString(colour);
  }

  /**
   * Retrieves the Color object representing the dragon's colour.
   *
   * @return The Color object associated with the dragon's colour.
   */
  public Color getColourObject() {
    return this.colour;
  }


  /**
   * Ends the turn of the dragon, calling the game engine to play the next turn.
   */
  public void endTurn() {
    // next dragons turn
    GameEngine.getInstance().playGame();
  }

  /**
   * Moves the dragon a specified number of spaces on the board.
   *
   * @param spaces The number of spaces to move the dragon.
   */
  public void move(Integer spaces) {
    if (GameEngine.getInstance()
        .checkValidMove(this.volcanoCardIterator, spaces, this.getColourObject())) {
      Square curSquare = this.volcanoCardIterator.getSquare();
      curSquare.clearOccupied();
      this.volcanoCardIterator.iterate(spaces).setOccupied(this);
    } else {
      this.endTurn();
    }
  }

  /**
   * Moves the dragon to its corresponding cave
   */
  public void moveToCave() {
    this.volcanoCardIterator.getSquare().clearOccupied();
    this.cave.setOccupied(this);
  }

  /**
   * Swaps the position of the dragon with the closest Dragon
   */
  private void swapClosest() {
    // look for closest dragon
    Dragon closest = null;
    Square forward = this.volcanoCardIterator.peek(1);
    Square backward = this.volcanoCardIterator.peek(-1);
    int i = 1;
    while (forward != backward) {
      if (forward.isOccupied()) {
        closest = forward.getOccupied();
        break;
      } else if (backward.isOccupied()) {
        closest = backward.getOccupied();
        break;
      }
      forward = this.volcanoCardIterator.peek(1 + i);
      if (forward == backward) {
        break;
      }
      backward = this.volcanoCardIterator.peek(-1 - i);
      i++;
    }
    if (forward.isOccupied()) {
      closest = forward.getOccupied();
    } else if (backward.isOccupied()) {
      closest = backward.getOccupied();
    }
    if (closest != null) {
      // swap iterators and change positions
      VolcanoCardIterator newIterator = closest.getVolcanoCardIterator();
      newIterator.getSquare().setOccupied(this);
      this.volcanoCardIterator.getSquare().setOccupied(closest);
      closest.setVolcanoCardIterator(this.volcanoCardIterator);
      this.volcanoCardIterator = newIterator;
    }
  }

  /**
   * Defines the action to take when a Bat Card is visited.
   *
   * @param card The bat card being visited.
   */
  @Override
  public void visit(BatCard card) {
    this.volcanoCardIterator.getSquare().interact(this, card);
  }

  /**
   * Defines the action to take when a Spider Card is visited.
   *
   * @param card The spider card being visited.
   */
  @Override
  public void visit(SpiderCard card) {
    this.volcanoCardIterator.getSquare().interact(this, card);
  }

  /**
   * Defines the action to take when a Salamander Card is visited.
   *
   * @param card The salamander card being visited.
   */
  @Override
  public void visit(SalamanderCard card) {
    this.volcanoCardIterator.getSquare().interact(this, card);
  }

  /**
   * Defines the action to take when a Baby Dragon Card is visited.
   *
   * @param card The baby dragon card being visited.
   */
  @Override
  public void visit(BabyDragonCard card) {
    this.volcanoCardIterator.getSquare().interact(this, card);
  }

  /**
   * Defines the action to take when a Pirate Dragon card is visited.
   *
   * @param card The pirate dragon card being visited.
   */
  @Override
  public void visit(PirateDragonCard card) {
    this.move(card.getNumMoves());
  }

  /**
   * Converts the state of the implementing object to a JSON string representation.
   *
   * @return A JSON string that represents the current state of the object.
   */
  @Override
  public String saveState() {
    return getColour();
  }

  /**
   * Defines the action to take when a Swap Card is visited.
   *
   * @param card The swap card being visited.
   */
  @Override
  public void visit(SwapCard card) {
    this.swapClosest();
    this.endTurn();
  }
}
