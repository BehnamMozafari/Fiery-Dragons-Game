package game.chitcards;

import game.entities.Dragon;

/**
 * Represents a Salamander Card in the game.
 */
public class SalamanderCard extends ChitCard {

  /**
   * Constructs a SalamanderCard with the specified number of moves.
   *
   * @param numMoves The number of moves the card allows.
   */
  public SalamanderCard(Integer numMoves) {
    super(numMoves, "/images/Salamander" + numMoves + ".png");
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
    return "Salamander_" + getNumMoves();
  }
}
