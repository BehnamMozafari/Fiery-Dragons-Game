package game.chitcards;

import game.engine.Savable;
import game.entities.Dragon;

public class SwapCard extends ChitCard implements Savable {

  /**
   * Constructs a new SwapCard chit card
   */
  public SwapCard() {
    super(0, "/images/SwapCard.png");
  }

  /**
   * Processes the action when the card is clicked on. This method must be implemented by concrete
   * card classes to define specific actions.
   *
   * @param dragon The dragon playing the card.
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
    return "SwapCard_" + getNumMoves();
  }
}
