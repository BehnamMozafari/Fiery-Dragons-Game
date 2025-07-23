package game.engine;

import static game.utils.GameUtils.BOARD_SIZE;

import game.chitcards.ChitCard;
import game.entities.Dragon;
import game.tiles.Square;
import game.tiles.VolcanoCard;
import game.tiles.VolcanoCardIterator;
import game.utils.GameUtils;
import game.view.GameFrame;
import game.view.LoadSquareBoardStrategy;
import game.view.SquareBoardStrategy;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 * Manages the main game logic and state.
 */
public class GameEngine {

  private static GameEngine instance;
  private final ArrayList<ChitCard> flippedChitCards;
  private List<Dragon> dragons;
  private GameFrame viewFacade;
  private int currentDragon;
  private Dragon winner;
  private int boardSize;

  /**
   * Constructs a new GameEngine and initializes the game interface.
   */
  public GameEngine() {
    this.dragons = new ArrayList<>();
    this.viewFacade = new GameFrame();
    this.viewFacade.setVisible(true);
    this.currentDragon = -1;
    this.winner = null;
    this.flippedChitCards = new ArrayList<>();
  }

  /**
   * Provides a global access point to the singleton instance of the GameEngine.
   *
   * @return the singleton instance.
   */
  public static GameEngine getInstance() {
    if (instance == null) {
      instance = new GameEngine();
    }
    return instance;
  }

  /**
   * Returns the current dragon taking a turn.
   *
   * @return the current active dragon.
   */
  public Dragon getCurrentDragon() {
    return this.dragons.get(this.currentDragon);
  }

  /**
   * Initializes the game with the specified number of players.
   *
   * @param numPlayers The number of players to start the game with.
   */
  public void initialiseGame(int numPlayers) {
    GameGenerator generator = new GameGenerator(numPlayers);
    this.dragons = generator.getDragons();
    List<ChitCard> chitCards = generator.getChitCards();
    List<VolcanoCard> volcanoCards = generator.getVolcanoCards();
    this.viewFacade.switchScreen(chitCards, volcanoCards, new SquareBoardStrategy());
    System.out.println("Game initialised with " + numPlayers + " players.");
    this.boardSize = BOARD_SIZE;
    playGame();
  }

  /**
   * load the last game
   *
   * @param filePath string path of the load file.
   */
  public void loadGame(String filePath) {
    LoadGameGenerator generator = new LoadGameGenerator(filePath);
    this.dragons = generator.getDragons();
    this.currentDragon = generator.getSavedState().getCurrentDragon();
    List<ChitCard> chitCards = generator.getChitCards();

    for (Integer integer : generator.getSavedState().getFlippedChitCards()) {
      this.flippedChitCards.add(chitCards.get(integer));
    }

    List<VolcanoCard> volcanoCards = generator.getVolcanoCards();
    this.viewFacade.switchScreen(chitCards, volcanoCards, new LoadSquareBoardStrategy());
    System.out.println("Last game has been loaded");
    this.boardSize = BOARD_SIZE;
    restartGame();
  }

  /**
   * Restarts and manages the game loop.
   */
  public void restartGame() {
    this.viewFacade.pauseFrame();
    ChitCard.toggleAllowFlippingFalse();
    javax.swing.Timer timer = new Timer(2000, evt -> {
      ChitCard.toggleAllowFlippingTrue();
      this.viewFacade.startTurn(this.getCurrentDragon().getColourObject());
    });
    timer.setRepeats(false);
    timer.start();
  }

  /**
   * Starts and manages the game loop.
   */
  public void playGame() {
    this.viewFacade.pauseFrame();
    ChitCard.toggleAllowFlippingFalse();
    javax.swing.Timer timer = new Timer(2000, evt -> {
      this.flipBackChitCards();
      ChitCard.toggleAllowFlippingTrue();
      this.currentDragon = (this.currentDragon + 1) % this.dragons.size();
      this.viewFacade.startTurn(this.getCurrentDragon().getColourObject());
    });
    timer.setRepeats(false);
    timer.start();
  }

  /**
   * flips back all chitcards
   */
  private void flipBackChitCards() {
    ChitCard.toggleAllowFlippingFalse();
    for (ChitCard chitCard : this.flippedChitCards) {
      ChitCard.toggleAllowFlippingFalse();
      chitCard.flip();
    }
    this.flippedChitCards.clear();
  }

  /**
   * Adds ChitCard to flipped ChitCards list
   *
   * @param chitCard The chitcard to add
   */
  public void addChitCard(ChitCard chitCard) {
    this.flippedChitCards.add(chitCard);
  }

  /**
   * Checks if the current player has won the game based on their move count, shows winner screen
   * and resets the game if so.
   *
   * @param colour The color representing the current player.
   */
  private void checkWin(VolcanoCardIterator iterator, Color colour, int spaces) {
    if (iterator.onCave(colour, spaces)) {
      this.winner = this.getCurrentDragon();
      this.winner.moveToCave();
      this.viewFacade.winScreen(GameUtils.colorToString(colour));
      this.resetGame();
    }
  }

  /**
   * Resets the game to its initial state, clearing all game components and showing the setup menu.
   */
  private void resetGame() {
    this.viewFacade.reset();
    this.dragons = new ArrayList<>();
    this.viewFacade = new GameFrame();
    this.viewFacade.setVisible(true);
    this.currentDragon = -1;
    this.winner = null;
    this.viewFacade.showSetupMenu();
  }

  /**
   * Validates if a move is valid based on the game's rules.
   *
   * @param iterator The iterator used to traverse volcano cards.
   * @param spaces   The number of spaces to move.
   * @param colour   The color of the dragon making the move.
   * @return true if the move is valid, false otherwise.
   */
  public boolean checkValidMove(VolcanoCardIterator iterator, int spaces, Color colour) {
    // check the square to move to is empty
    Square curSquare = iterator.peek(spaces);
    if (curSquare.isOccupied()) {
      return false;
    }
    // check if dragon is passing cave
    if (iterator.passesCave(colour, spaces)) {
      return false;
    }
    // check if dragon is moving back when on cave
    if (iterator.getCurrentIndex() == -1 && spaces < 0) {
      return false;
    }
    this.checkWin(iterator, colour, spaces);
    return true;
  }

  /**
   * Returns the list of flipped ChitCards.
   *
   * @return The list of flipped ChitCards.
   */
  public ArrayList<ChitCard> getFlippedChitCards() {
    return flippedChitCards;
  }

  /**
   * Returns the list of dragons in the game.
   *
   * @return The list of dragons.
   */
  public List<Dragon> getDragons() {
    return dragons;
  }

  /**
   * Returns the size of the board.
   *
   * @return The size of the board.
   */
  public int getBoardSize() {
    return boardSize;
  }
}
