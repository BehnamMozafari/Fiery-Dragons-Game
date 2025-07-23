package game.entities;

import game.chitcards.BabyDragonCard;
import game.chitcards.BatCard;
import game.chitcards.PirateDragonCard;
import game.chitcards.SalamanderCard;
import game.chitcards.SpiderCard;
import game.chitcards.SwapCard;

/**
 * Defines the visitor interface for implementing the Visitor design pattern. This interface allows
 * objects to be "visited" by carrying out specific actions depending on the type of chit card
 * played in the game.
 */
public interface Visitor {

  /**
   * Defines the action to take when a Bat Card is visited.
   *
   * @param card The bat card being visited.
   */
  void visit(BatCard card);

  /**
   * Defines the action to take when a Spider Card is visited.
   *
   * @param card The spider card being visited.
   */
  void visit(SpiderCard card);

  /**
   * Defines the action to take when a Salamander Card is visited.
   *
   * @param card The salamander card being visited.
   */
  void visit(SalamanderCard card);

  /**
   * Defines the action to take when a Baby Dragon Card is visited.
   *
   * @param card The baby dragon card being visited.
   */
  void visit(BabyDragonCard card);

  /**
   * Defines the action to take when a Pirate Dragon Card is visited.
   *
   * @param card The pirate dragon card being visited.
   */
  void visit(PirateDragonCard card);

  /**
   * Defines the action to take when a Swap Card is visited.
   *
   * @param card The swap card being visited.
   */
  void visit(SwapCard card);
}
