package game.chitcards;

import game.entities.Dragon;

/**
 * Represents a Baby Dragon Card in the game, a specific type of ChitCard.
 */
public class BabyDragonCard extends ChitCard {

  /**
   * Constructs a new BabyDragonCard with the specified number of moves.
   *
   * @param numMoves The number of moves the card allows when played. This number also determines
   *                 which image is used for the card.
   */
  public BabyDragonCard(Integer numMoves) {
    super(numMoves, "/images/BabyDragon" + numMoves + ".png");
  }

  /**
   * Accepts a dragon to execute a move based on this card's mechanics. This method is part of the
   * visitor pattern implementation.
   *
   * @param dragon The dragon playing this card. The exact behavior (like moving the dragon) will
   *               depend on the game rules associated with a Baby Dragon Card.
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
    return "BabyDragon_" + getNumMoves();
  }
}
