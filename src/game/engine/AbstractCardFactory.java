package game.engine;

import game.chitcards.ChitCard;
import game.tiles.Cave;
import game.tiles.Square;
import game.tiles.VolcanoCard;


/**
 * Interface for creating various types of game cards and entities.
 */
public interface AbstractCardFactory {

  /**
   * Creates a ChitCard based on the specified card type and number of moves.
   *
   * @param card     the type of chit card to create
   * @param numMoves the number of moves for the chit card
   * @return the created ChitCard, or null if the card type is unknown
   */
  ChitCard createChitCard(String card, int numMoves);

  /**
   * Creates a VolcanoCard based on the specified card description, number of rows, and columns.
   *
   * @param card the description of the volcano card, specifying square types separated by
   *             underscores
   * @param rows the number of rows in the volcano card
   * @param cols the number of columns in the volcano card
   * @return the created VolcanoCard
   */
  VolcanoCard createVolcanoCard(String card, int rows, int cols);

  /**
   * Creates a Square based on the specified square type.
   *
   * @param square the type of square to create
   * @return the created Square, or null if the square type is unknown
   */
  Square createSquare(String square);

  /**
   * Creates a Cave based on the specified cave type.
   *
   * @param cave the type of cave to create
   * @return the created Cave, or null if the cave type is unknown
   */
  Cave createCave(String cave);

}
