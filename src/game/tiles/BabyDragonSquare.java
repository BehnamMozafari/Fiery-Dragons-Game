package game.tiles;

import game.chitcards.BabyDragonCard;
import game.chitcards.BatCard;
import game.chitcards.SalamanderCard;
import game.chitcards.SpiderCard;
import game.entities.Dragon;

/**
 * BabyDragonSquare is a type of square with a specific icon.
 */
public class BabyDragonSquare extends Square {

  /**
   * Constructs a BabyDragonSquare with a default icon.
   */
  public BabyDragonSquare() {
    super("/images/BabyDragon.png");
  }

  /**
   * Interacts with a dragon using a BatCard.  BabyDragonSquare.
   *
   * @param dragon The dragon to interact with.
   * @param card   The BatCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, BatCard card) {
    dragon.endTurn();
  }

  /**
   * Interacts with a dragon using a SpiderCard. This method is not implemented for
   * BabyDragonSquare.
   *
   * @param dragon The dragon to interact with.
   * @param card   The SpiderCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, SpiderCard card) {
    dragon.endTurn();
  }

  /**
   * Interacts with a dragon using a SalamanderCard. This method is not implemented for
   * BabyDragonSquare.
   *
   * @param dragon The dragon to interact with.
   * @param card   The SalamanderCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, SalamanderCard card) {
    dragon.endTurn();
  }

  /**
   * Interacts with a dragon using a BabyDragonCard. Moves the dragon based on the number of moves
   * specified by the card.
   *
   * @param dragon The dragon to interact with.
   * @param card   The BabyDragonCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, BabyDragonCard card) {
    dragon.move(card.getNumMoves());
  }


  /**
   * Converts the state of the implementing object to a JSON string representation.
   *
   * @return A JSON string that represents the current state of the object.
   */
  @Override
  public String saveState() {
    return "BabyDragon";
  }
}
