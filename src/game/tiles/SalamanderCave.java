package game.tiles;

import game.chitcards.BabyDragonCard;
import game.chitcards.BatCard;
import game.chitcards.SalamanderCard;
import game.chitcards.SpiderCard;
import game.entities.Dragon;
import java.awt.Color;

/**
 * SalamanderCave is a type of cave square with a specific icon and color.
 */
public class SalamanderCave extends Cave {

  /**
   * Constructs a SalamanderCave with a default icon and color.
   */
  public SalamanderCave() {
    super("/images/SalamanderCave.png", Color.WHITE);
  }

  /**
   * Interacts with a dragon using a BatCard.  SalamanderCave.
   *
   * @param dragon The dragon to interact with.
   * @param card   The BatCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, BatCard card) {
    dragon.endTurn();
  }

  /**
   * Interacts with a dragon using a SpiderCard.  SalamanderCave.
   *
   * @param dragon The dragon to interact with.
   * @param card   The SpiderCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, SpiderCard card) {
    dragon.endTurn();
  }

  /**
   * Interacts with a dragon using a SalamanderCard. Moves the dragon based on the number of moves
   * specified by the card.
   *
   * @param dragon The dragon to interact with.
   * @param card   The SalamanderCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, SalamanderCard card) {
    dragon.move(card.getNumMoves());
  }

  /**
   * Interacts with a dragon using a BabyDragonCard. This method is not implemented for
   * SalamanderCave.
   *
   * @param dragon The dragon to interact with.
   * @param card   The BabyDragonCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, BabyDragonCard card) {
    dragon.endTurn();
  }

  /**
   * Converts the state of the implementing object to a JSON string representation.
   *
   * @return A JSON string that represents the current state of the object.
   */
  @Override
  public String saveState() {
    return "SalamanderCave";
  }
}
