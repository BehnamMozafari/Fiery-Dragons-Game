package game.engine;

import game.chitcards.BabyDragonCard;
import game.chitcards.BatCard;
import game.chitcards.ChitCard;
import game.chitcards.PirateDragonCard;
import game.chitcards.SalamanderCard;
import game.chitcards.SpiderCard;
import game.chitcards.SwapCard;
import game.tiles.BabyDragonCave;
import game.tiles.BabyDragonSquare;
import game.tiles.BatCave;
import game.tiles.BatSquare;
import game.tiles.Cave;
import game.tiles.SalamanderCave;
import game.tiles.SalamanderSquare;
import game.tiles.SpiderCave;
import game.tiles.SpiderSquare;
import game.tiles.Square;
import game.tiles.VolcanoCard;
import java.util.List;

public class LoadGameCardFactory implements AbstractCardFactory {

  private final VolcanoCardBuilder builder;

  /**
   * Constructs a new DefaultCardFactory and initializes the configuration and builder.
   */
  public LoadGameCardFactory() {
    this.builder = new VolcanoCardBuilder();
  }

  /**
   * Creates a ChitCard based on the specified card type and number of moves.
   *
   * @param card     the type of chit card to create
   * @param numMoves the number of moves for the chit card
   * @return the created ChitCard or null if the card type is unknown
   */
  @Override
  public ChitCard createChitCard(String card, int numMoves) {
    return switch (card) {
      case "BabyDragon" -> new BabyDragonCard(numMoves);
      case "Bat" -> new BatCard(numMoves);
      case "Salamander" -> new SalamanderCard(numMoves);
      case "Spider" -> new SpiderCard(numMoves);
      case "PirateDragon" -> new PirateDragonCard(numMoves);
      case "SwapCard" -> new SwapCard();
      default -> null;
    };
  }

  /**
   * Creates a VolcanoCard based on the specified card description, number of rows, and columns.
   *
   * @param card the description of the volcano card, specifying square types separated by
   *             underscores
   * @param rows the number of rows in the volcano card
   * @param cols the number of columns in the volcano card
   * @return the created VolcanoCard
   */
  @Override
  public VolcanoCard createVolcanoCard(String card, int rows, int cols) {
    List<String> strings = List.of(card.split("_"));
    int numSquares = Integer.parseInt(strings.get(strings.size() - 1));
    int caveIndex = Integer.parseInt(strings.get(strings.size() - 2));
    VolcanoCard volCard;

    for (int i = 0; i < numSquares; i++) {
      builder.setSquare(this.createSquare(strings.get(i)));
    }

    if (caveIndex == -1) {
      volCard = builder.getVolcanoCard(rows, cols);
    } else {
      volCard = builder.getVolcanoCard(caveIndex, rows, cols);
      volCard.setCave(this.createCave(strings.get(numSquares)));
    }

    return volCard;
  }

  /**
   * Creates a Square based on the specified square type.
   *
   * @param square the type of square to create
   * @return the created Square or null if the square type is unknown
   */
  @Override
  public Square createSquare(String square) {
    return switch (square) {
      case "BabyDragon" -> new BabyDragonSquare();
      case "Bat" -> new BatSquare();
      case "Salamander" -> new SalamanderSquare();
      case "Spider" -> new SpiderSquare();
      default -> null;
    };
  }

  /**
   * Creates a Cave based on the specified cave type.
   *
   * @param cave the type of cave to create
   * @return the created Cave or null if the cave type is unknown
   */
  @Override
  public Cave createCave(String cave) {
    return switch (cave) {
      case "BabyDragonCave" -> new BabyDragonCave();
      case "BatCave" -> new BatCave();
      case "SalamanderCave" -> new SalamanderCave();
      case "SpiderCave" -> new SpiderCave();
      default -> null;
    };
  }
}
