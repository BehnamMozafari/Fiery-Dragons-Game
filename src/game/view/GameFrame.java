package game.view;

import static game.utils.GameUtils.GAMEFRAME_SIZE;

import game.chitcards.ChitCard;
import game.engine.GameEngine;
import game.engine.SavedState;
import game.tiles.VolcanoCard;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Represents the main frame of the game application. It contains methods to set up the UI, switch
 * screens, start a turn, display a win screen, and update positions.
 */
public class GameFrame extends JFrame {

  private static final int TURN_TIME_LIMIT = 30; // Turn time limit in seconds
  private static final int PAUSE_DURATION = 2000; // Pause duration in milliseconds
  private static final int SIZE_PER_SQUARE = 30;

  private SetupMenu setupMenu;
  private GameBoard gameBoard;
  private JPanel glassPane;
  private Timer turnTimer;
  private int timeRemaining;

  /**
   * Constructs a GameFrame with default properties. Sets up the frame's title, size, and default
   * close operation, and initializes the UI.
   */
  public GameFrame() {
    setTitle("Fiery Dragons");
    setSize(GAMEFRAME_SIZE, GAMEFRAME_SIZE);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    showSetupMenu();
  }

  /**
   * Sets up the UI components for the setup menu.
   */
  public void showSetupMenu() {
    ActionListener startGameListener = e -> {
      if (setupMenu.getSelectedFilePath() != null) {
        dispose();
        GameEngine.getInstance().loadGame(setupMenu.getSelectedFilePath());
      } else {
        Integer numPlayers = (Integer) setupMenu.getPlayerCount()
            .getSelectedItem(); // Use the wrapper class Integer directly
        dispose();
        GameEngine.getInstance().initialiseGame(numPlayers);
      }
    };
    setupMenu = new SetupMenu(startGameListener);
    switchPanel(setupMenu.getMainPanel());
  }

  /**
   * Switches the screen from the setup menu to the game board.
   *
   * @param chitCards    The list of chit cards to display on the game board.
   * @param volcanoCards The list of volcano cards to display on the game board.
   * @param strategy     The strategy to set up the board.
   */
  public void switchScreen(List<ChitCard> chitCards, List<VolcanoCard> volcanoCards,
      SetBoardStrategy strategy) {
    updateSize(volcanoCards);
    gameBoard = new GameBoard(chitCards, volcanoCards, strategy, e -> this.saveGame());
    switchPanel(gameBoard.getPanel());
    setupGlassPane();
  }

  /**
   * Updates the size based on the number of squares on each side of the board
   *
   * @param volcanoCards The list of volcano cards to display on the game board.
   */
  private void updateSize(List<VolcanoCard> volcanoCards) {
    int totalCards = volcanoCards.size();
    int cardsPerSide = totalCards / 4;
    int remainingCards = totalCards % 4;
    int index = 0;
    int maxHorizontalSquares = 0;
    int maxVerticalSquares = 0;
    int curHorizontalSquares = 0;
    int curVerticalSquares = 0;
    // finding max horizontal and vertical squares
    for (int i = 0; i < cardsPerSide + (remainingCards > 0 ? 1 : 0); i++) {
      maxHorizontalSquares += volcanoCards.get(index++).getNumSquares();
    }
    for (int i = 0; i < cardsPerSide + (remainingCards > 1 ? 1 : 0); i++) {
      maxVerticalSquares += volcanoCards.get(index++).getNumSquares();
    }
    for (int i = 0; i < cardsPerSide + (remainingCards > 2 ? 1 : 0); i++) {
      curHorizontalSquares += volcanoCards.get(index++).getNumSquares();
    }
    maxHorizontalSquares = Math.max(curHorizontalSquares, maxHorizontalSquares);
    for (int i = 0; i < cardsPerSide; i++) {
      curVerticalSquares += volcanoCards.get(index++).getNumSquares();
    }
    maxVerticalSquares = Math.max(maxVerticalSquares, curVerticalSquares);
    setSize(
        Math.min(GAMEFRAME_SIZE + Math.max(0, (maxHorizontalSquares - 6) * SIZE_PER_SQUARE), 1300),
        Math.min(GAMEFRAME_SIZE + Math.max(0, (maxVerticalSquares - 6) * SIZE_PER_SQUARE), 850));
  }

  /**
   * Helper method to switch the content panel.
   *
   * @param panel The new panel to display.
   */
  private void switchPanel(JPanel panel) {
    getContentPane().removeAll();
    getContentPane().add(panel, BorderLayout.CENTER);
    validate();
    repaint();
    setVisible(true);
  }

  /**
   * Sets up the glass pane for intercepting mouse events.
   */
  private void setupGlassPane() {
    glassPane = new JPanel();
    glassPane.setOpaque(false);
    glassPane.addMouseListener(new MouseAdapter() {
    });
    setGlassPane(glassPane);
  }

  /**
   * Pauses interactions with the frame.
   */
  public void pauseFrame() {
    glassPane.setVisible(true);
    Timer timer = new Timer(PAUSE_DURATION, e -> glassPane.setVisible(false));
    timer.setRepeats(false);
    timer.start();
  }

  /**
   * Starts a turn for the player with the specified color.
   *
   * @param colour The color of the player whose turn is starting.
   */
  public void startTurn(Color colour) {
    validate();
    repaint();
    gameBoard.promptPlayer(colour);
    startTimer();
  }

  /**
   * Starts the turn timer.
   */
  private void startTimer() {
    timeRemaining = TURN_TIME_LIMIT;
    gameBoard.updateTimer(timeRemaining);

    if (turnTimer != null) {
      turnTimer.stop();
    }

    turnTimer = new Timer(1000, e -> {
      timeRemaining--;
      gameBoard.updateTimer(timeRemaining);
      if (timeRemaining <= 0) {
        turnTimer.stop();
        GameEngine.getInstance().playGame();
      }
    });

    turnTimer.start();
  }

  /**
   * Displays a win screen for the player with the specified color.
   *
   * @param colour The color of the winning player.
   */
  public void winScreen(String colour) {
    if (turnTimer != null) {
      turnTimer.stop();
    }
    gameBoard.winPopup(colour);
  }

  /**
   * Hides the GameFrame by setting its visibility to false.
   */
  public void reset() {
    setVisible(false);
  }

  /**
   * Saves the current game state and exits.
   */
  private void saveGame() {
    SavedState.saveState(this.gameBoard);
    System.out.println("Game saved!");
    this.dispose();
    System.exit(0);
  }
}
