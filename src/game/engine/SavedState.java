package game.engine;

import game.chitcards.ChitCard;
import game.entities.Dragon;
import game.tiles.VolcanoCard;
import game.utils.GameUtils;
import game.view.GameBoard;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration class representing the structure of the configuration JSON file. This class is used
 * for deserialisation with Gson.
 */
public class SavedState {

  private List<String> chitCards;
  private List<String> volcanoCards;
  private List<String> dragons;
  private List<Integer> flippedChitCards;
  private int currentDragon;
  private Map<String, Integer> dragonCaves;

  /**
   * Default constructor initializing empty lists and map for chit cards, volcano cards, dragons,
   * flipped chit cards, and dragon caves.
   */
  public SavedState() {
    this.chitCards = new ArrayList<>();
    this.volcanoCards = new ArrayList<>();
    this.dragons = new ArrayList<>();
    this.flippedChitCards = new ArrayList<>();
    this.dragonCaves = new HashMap<>();
  }

  /**
   * Saves the current game state to a file.
   *
   * @param board The game board containing the current state of the game.
   */
  public static void saveState(GameBoard board) {
    // initialisation
    SavedState savedState = new SavedState();
    GameEngine engine = GameEngine.getInstance();

    // save chitcards
    for (ChitCard card : board.getChitCards()) {
      savedState.chitCards.add(card.saveState());
    }

    // save index of flipped cards
    for (ChitCard card : engine.getFlippedChitCards()) {
      savedState.flippedChitCards.add(board.getChitCards().indexOf(card));
    }

    // save volcano cards and caves with dragon in it
    for (VolcanoCard card : board.getVolcanoCards()) {
      savedState.volcanoCards.add(card.saveState());

      if (card.getCave() != null) {
        Color color = card.getCave().getColour();
        for (Dragon dragon : engine.getDragons()) {
          if (color == dragon.getColourObject()) {
            // save dragon's cave
            savedState.dragonCaves.put(dragon.saveState(), board.getVolcanoCards().indexOf(card));

          }
        }
      }
    }

    for (Dragon dragon : engine.getDragons()) {
      // save dragon's position
      int tmpVolIndex = board.getVolcanoCards()
          .indexOf(dragon.getVolcanoCardIterator().getCurrentCard());
      int tmpSquareIndex = dragon.getVolcanoCardIterator().getCurrentIndex();
      savedState.dragons.add(dragon.saveState() + "_" + tmpVolIndex + "_" + tmpSquareIndex);
    }

    //save current dragon
    savedState.currentDragon = engine.getDragons().indexOf(engine.getCurrentDragon());

    // Create a Gson instance and save it as json file
    GameUtils.writeJsonFile(savedState, GameUtils.getSavePath());
  }

  /**
   * Returns the list of chit cards.
   *
   * @return The list of chit cards.
   */
  public List<String> getChitCards() {
    return chitCards;
  }

  /**
   * Sets the list of chit cards.
   *
   * @param chitCards The list of chit cards.
   */
  public void setChitCards(List<String> chitCards) {
    this.chitCards = chitCards;
  }

  /**
   * Returns the list of volcano cards.
   *
   * @return The list of volcano cards.
   */
  public List<String> getVolcanoCards() {
    return volcanoCards;
  }

  /**
   * Sets the list of volcano cards.
   *
   * @param volcanoCards The list of volcano cards.
   */
  public void setVolcanoCards(List<String> volcanoCards) {
    this.volcanoCards = volcanoCards;
  }

  /**
   * Returns the list of dragons.
   *
   * @return The list of dragons.
   */
  public List<String> getDragons() {
    return dragons;
  }

  /**
   * Sets the list of dragons.
   *
   * @param dragons The list of dragons.
   */
  public void setDragons(List<String> dragons) {
    this.dragons = dragons;
  }

  /**
   * Returns the list of flipped chit cards.
   *
   * @return The list of flipped chit cards.
   */
  public List<Integer> getFlippedChitCards() {
    return flippedChitCards;
  }

  /**
   * Sets the list of flipped chit cards.
   *
   * @param flippedChitCards The list of flipped chit cards.
   */
  public void setFlippedChitCards(List<Integer> flippedChitCards) {
    this.flippedChitCards = flippedChitCards;
  }

  /**
   * Returns the index of the current dragon.
   *
   * @return The index of the current dragon.
   */
  public int getCurrentDragon() {
    return currentDragon;
  }

  /**
   * Sets the current dragon index.
   *
   * @param currentDragon The index of the current dragon.
   */
  public void setCurrentDragon(int currentDragon) {
    this.currentDragon = currentDragon;
  }

  /**
   * Returns the map of dragon caves.
   *
   * @return The map of dragon caves.
   */
  public Map<String, Integer> getDragonCaves() {
    return dragonCaves;
  }

  /**
   * Sets the map of dragon caves.
   *
   * @param dragonCaves The map of dragon caves.
   */
  public void setDragonCaves(Map<String, Integer> dragonCaves) {
    this.dragonCaves = dragonCaves;
  }
}
