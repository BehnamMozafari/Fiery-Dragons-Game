package game.chitcards;

import game.entities.Dragon;

/**
 * Represents a Spider Card in the game.
 */
public class SpiderCard extends ChitCard {

  /**
   * Constructs a SpiderCard with specified number of moves.
   *
   * @param numMoves The number of moves the card allows.
   */
  public SpiderCard(Integer numMoves) {
    super(numMoves, "/images/Spider" + numMoves + ".png");
  }

  /**
   * Executes actions specific to this card when played by a dragon.
   *
   * @param dragon The dragon using this card.
   */
  @Override
  public void accept(Dragon dragon) {
    dragon.visit(this);
  }

  /**
   * Converts the state of the implementing object to a JSON string representation.
   *
   * @return A JSON string that represents the current state of the object.
   */
  @Override
  public String saveState() {
    return "Spider_" + getNumMoves();
  }
}
