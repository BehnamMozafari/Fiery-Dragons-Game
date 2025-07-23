package game.engine;

import game.entities.Dragon;
import game.tiles.Cave;
import game.tiles.VolcanoCard;
import game.utils.GameUtils;
import java.util.List;
import java.util.Map;

/**
 * LoadGameGenerator is responsible for generating the game state from a saved file. It initializes
 * dragons, chit cards, and volcano cards based on the saved state.
 */
public class LoadGameGenerator extends AbstractGameGenerator {

  private final SavedState savedState;

  /**
   * Constructs a LoadGameGenerator with the specified path to the saved state.
   *
   * @param path The path to the saved state file.
   */
  public LoadGameGenerator(String path) {
    this.savedState = GameUtils.initialiseSavedState(path);
    this.cardFactory = new LoadGameCardFactory();
    createDragons(4);
    createChitCards();
  }

  /**
   * Creates dragons based on the saved state. Each dragon is associated with a volcano card and a
   * cave.
   *
   * @param count The number of dragons to create.
   */
  @Override
  void createDragons(int count) {
    // get strings of caves from config file
    Map<String, Integer> dragonCaveStr = this.savedState.getDragonCaves();
    List<String> dragonStr = savedState.getDragons();

    createVolcanoCards();

    // assign dragons to volcano card
    int tmpVolIndex, tmpSquareIndex;
    String dragonColour;
    Dragon newDragon;
    Cave newCave;

    List<String> dragonInfo;
    for (String dragon : dragonStr) {

      // eg: White_1_2, which means the white dragon is at the 2nd square of the 1st volcano card
      dragonInfo = List.of(dragon.split("_"));

      dragonColour = dragonInfo.get(0);
      tmpVolIndex = Integer.parseInt(dragonInfo.get(1));
      tmpSquareIndex = Integer.parseInt(dragonInfo.get(2));

      // cave is created with volcano cards
      newCave = volcanoCards.get(dragonCaveStr.get(dragonColour)).getCave();

      // create dragon
      newDragon = new Dragon(volcanoCards.get(tmpVolIndex), newCave);

      // set square index
      newDragon.getVolcanoCardIterator().setCurrentIndex(tmpSquareIndex);

      // repaint
        if (tmpSquareIndex == -1) {
            volcanoCards.get(tmpVolIndex).getCave().setOccupied(newDragon);
        } else {
            volcanoCards.get(tmpVolIndex).getSquares().get(tmpSquareIndex).setOccupied(newDragon);
        }

      // add dragon to list
      dragons.add(newDragon);

    }

  }

  /**
   * Creates volcano cards based on the saved state. Volcano cards are initialized and set with
   * neighbors.
   */
  @Override
  protected void createVolcanoCards() {
    // get strings of volcanoes from config file
    List<String> volcanoStrings = this.savedState.getVolcanoCards();

    // initialisation local variable to control creation of volcano card
    int j = 1;
    int rows = 2;
    int cols = 3;
    boolean reverse;
    VolcanoCard tempCard;

    for (int i = 0; i < volcanoStrings.size(); i++) {
      // exchange row and column every two card
      if (j <= 2) {
        tempCard = cardFactory.createVolcanoCard(volcanoStrings.get(i), rows, cols);
      } else {
        j = 1;
        int temp = rows;
        rows = cols;
        cols = temp;
        tempCard = cardFactory.createVolcanoCard(volcanoStrings.get(i), rows, cols);
      }
      j += 1;

      // reverse card
      reverse = i >= 4;

      // set card
      tempCard.initialiseVolcano(reverse, rows, cols);
      volcanoCards.add(tempCard);
    }

    // Set neighbors
    for (int i = 0; i < volcanoCards.size(); i++) {
      VolcanoCard leftNeighbor =
          i == 0 ? volcanoCards.get(volcanoCards.size() - 1) : volcanoCards.get(i - 1);
      VolcanoCard rightNeighbor =
          i == volcanoCards.size() - 1 ? volcanoCards.get(0) : volcanoCards.get(i + 1);

      volcanoCards.get(i).setLeftNeighbour(leftNeighbor);
      volcanoCards.get(i).setRightNeighbour(rightNeighbor);
    }
  }

  /**
   * Creates chit cards based on the saved state.
   */
  @Override
  void createChitCards() {
    // the list of all the chit cards
    List<String> chitStrings = this.savedState.getChitCards();

    List<String> eachChit;
    // the list of move count of each chit card
    for (String chitStr : chitStrings) {
      eachChit = List.of(chitStr.split("_"));
      chitCards.add(cardFactory.createChitCard(eachChit.get(0), Integer.parseInt(eachChit.get(1))));
    }
  }

  /**
   * Returns the saved state associated with this game generator.
   *
   * @return the saved state.
   */
  public SavedState getSavedState() {
    return savedState;
  }

}
