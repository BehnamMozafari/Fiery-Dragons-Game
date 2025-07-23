package game.tiles;

import game.chitcards.BabyDragonCard;
import game.chitcards.BatCard;
import game.chitcards.SalamanderCard;
import game.chitcards.SpiderCard;
import game.entities.Dragon;
import java.awt.Color;

/**
 * SpiderCave is a type of cave square with a specific icon and color.
 */
public class SpiderCave extends Cave {

  /**
   * Constructs a SpiderCave with a default icon and color.
   */
  public SpiderCave() {
    super("/images/SpiderCave.png", Color.ORANGE);
  }

  /**
   * Interacts with a dragon using a BatCard.
   *
   * @param dragon The dragon to interact with.
   * @param card   The BatCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, BatCard card) {
    dragon.endTurn();
  }

  /**
   * Interacts with a dragon using a SpiderCard. Moves the dragon based on the number of moves
   * specified by the card.
   *
   * @param dragon The dragon to interact with.
   * @param card   The SpiderCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, SpiderCard card) {
    dragon.move(card.getNumMoves());
  }

  /**
   * Interacts with a dragon using a SalamanderCard.
   *
   * @param dragon The dragon to interact with.
   * @param card   The SalamanderCard used for interaction.
   */
  @Override
  public void interact(Dragon dragon, SalamanderCard card) {
    dragon.endTurn();
  }

  /**
   * Interacts with a dragon using a BabyDragonCard.   SpiderCave.
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
    return "SpiderCave";
  }
}
