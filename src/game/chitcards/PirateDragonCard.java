package game.chitcards;

import game.entities.Dragon;

/**
 * Represents a Pirate Dragon Card in the game, a specific type of ChitCard.
 */
public class PirateDragonCard extends ChitCard {

  /**
   * Constructs a new PirateDragonCard with the specified number of moves.
   *
   * @param numMoves The number of moves the card allows when played, which also determines the
   *                 card's image.
   */
  public PirateDragonCard(Integer numMoves) {
    super(numMoves, "/images/PirateDragon" + Math.abs(numMoves) + ".png");
  }

  /**
   * Executes actions specific to this card's type when played by a dragon.
   *
   * @param dragon The dragon using this card, expected to perform actions as defined by the game's
   *               rules for a Pirate Dragon Card.
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
    return "PirateDragon_" + getNumMoves();
  }
}
