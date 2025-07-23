package game.chitcards;

import game.entities.Dragon;

/**
 * Represents a Bat Card in the game, a specific type of ChitCard.
 */
public class BatCard extends ChitCard {

  /**
   * Constructs a new BatCard with the specified number of moves.
   *
   * @param numMoves The number of moves the card will allow when played. This number also
   *                 determines which image is used for the card.
   */
  public BatCard(Integer numMoves) {
    super(numMoves, "/images/Bat" + numMoves + ".png");
  }

  /**
   * Accepts a dragon to perform a move based on this card's logic. This method is part of the
   * visitor pattern implementation.
   *
   * @param dragon The dragon playing this card. The dragon will be moved according to the rules
   *               defined for a BatCard.
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
    return "Bat_" + getNumMoves();
  }
}
