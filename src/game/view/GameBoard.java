package game.view;

import game.chitcards.ChitCard;
import game.tiles.VolcanoCard;
import game.utils.GameUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Represents the game board view, containing chit cards and volcano cards. It displays chit cards
 * in the center and volcano cards around the perimeter.
 */
public class GameBoard {

  private static final String TIMER_TEXT = "Time: ";
  private static final String PROMPT_TEXT = "It's the turn of the player with color: ";
  private static final String WINNER_TEXT = "The winner is the player with color: ";
  private static final Font TIMER_FONT = new Font("Times New Roman", Font.BOLD, 25);
  private static final Font MESSAGE_FONT = new Font("Times New Roman", Font.BOLD, 25);
  private static final Font WIN_MESSAGE_FONT = new Font("Times New Roman", Font.BOLD, 18);
  private static final Color TIMER_COLOR = Color.RED;
  private static final Color WIN_MESSAGE_COLOR = Color.BLACK;

  private final List<ChitCard> chitCards;
  private final List<VolcanoCard> volcanoCards;
  private final JPanel panel; // Main container using composition
  private final JPanel messagePanel; // Panel to display the prompt message and timer
  private final JLabel messageLabel; // Label to display the text in the message panel
  private final JLabel timerLabel; // Label to display the timer
  private final JButton saveGameButton; // Save game button

  /**
   * Constructs a GameBoard with the specified chit cards and volcano cards.
   *
   * @param chitCards        The list of chit cards to display on the board.
   * @param volcanoCards     The list of volcano cards to display on the board.
   * @param strategy         The strategy to set up the board.
   * @param saveGameListener The listener for the save game button.
   */
  public GameBoard(List<ChitCard> chitCards, List<VolcanoCard> volcanoCards,
      SetBoardStrategy strategy, ActionListener saveGameListener) {
    this.chitCards = chitCards;
    this.volcanoCards = volcanoCards;
    this.panel = new JPanel(new BorderLayout());
    this.messagePanel = new JPanel();
    this.messageLabel = new JLabel("");
    this.timerLabel = new JLabel("Time: 30"); // Initialize with default time
    setupTimerLabel();
    setupMessagePanel();
    this.saveGameButton = new JButton("Save & Exit");
    saveGameButton.setFont(new Font("Arial", Font.BOLD, 20));
    saveGameButton.setPreferredSize(new Dimension(140, 40));
    saveGameButton.addActionListener(saveGameListener);
    initialiseBoard(strategy);
  }

  /**
   * Initializes the game board by setting up chit card area and volcano card border.
   *
   * @param strategy The strategy to set up the board.
   */
  public void initialiseBoard(SetBoardStrategy strategy) {
    strategy.setUpBoard(this);
    this.panel.revalidate();
    this.panel.repaint();
  }

  /**
   * Returns the save game button.
   *
   * @return The save game button.
   */
  public JButton getSaveGameButton() {
    return saveGameButton;
  }

  /**
   * Sets up the timer label with the specified font and color.
   */
  private void setupTimerLabel() {
    timerLabel.setFont(TIMER_FONT);
    timerLabel.setForeground(TIMER_COLOR);
  }

  /**
   * Sets up the message panel by adding the message label to it.
   */
  private void setupMessagePanel() {
    messagePanel.add(messageLabel);
    messagePanel.add(timerLabel);
  }

  /**
   * Returns the main panel of the game board.
   *
   * @return The main panel.
   */
  public JPanel getPanel() {
    return panel;
  }

  /**
   * Returns the message panel of the game board.
   *
   * @return The message panel.
   */
  public JPanel getMessagePanel() {
    return messagePanel;
  }


  /**
   * Prompts the player with the specified color for their turn.
   *
   * @param colour The color of the player whose turn it is to be prompted.
   */
  public void promptPlayer(Color colour) {
    String colorName = GameUtils.colorToString(colour);
    setMessageLabel(PROMPT_TEXT + colorName, colour, MESSAGE_FONT);
    panel.revalidate();
    panel.repaint();
  }

  /**
   * Updates the timer display.
   *
   * @param time The remaining time to display.
   */
  public void updateTimer(int time) {
    timerLabel.setText(TIMER_TEXT + time);
    panel.revalidate();
    panel.repaint();
  }

  /**
   * Displays a popup message indicating the winner with the specified color.
   *
   * @param colorName The color of the winning player.
   */
  public void winPopup(String colorName) {
    ImageIcon icon = createDragonIcon(colorName);
    JPanel messagePanel = createWinMessagePanel(colorName, icon);

    JOptionPane.showMessageDialog(panel, messagePanel, "Game Over", JOptionPane.INFORMATION_MESSAGE,
        null);
  }

  /**
   * Creates a message panel for the win popup with the specified color and icon.
   *
   * @param colorName The color name of the winning player.
   * @param icon      The icon to display in the win popup.
   * @return The created message panel.
   */
  private JPanel createWinMessagePanel(String colorName, ImageIcon icon) {
    JPanel messagePanel = new JPanel();
    messagePanel.setBackground(GameUtils.getColorFromName(colorName));
    messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

    JLabel messageLabel = new JLabel(WINNER_TEXT + colorName);
    messageLabel.setFont(WIN_MESSAGE_FONT);
    messageLabel.setForeground(WIN_MESSAGE_COLOR);
    messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel iconLabel = new JLabel(icon);
    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    messagePanel.add(Box.createVerticalStrut(10)); // Add some vertical space
    messagePanel.add(iconLabel); // Add the custom icon
    messagePanel.add(Box.createVerticalStrut(10)); // Add some vertical space
    messagePanel.add(messageLabel); // Add the message label

    return messagePanel;
  }

  /**
   * Creates an icon based on the color name.
   *
   * @param colorName The color name.
   * @return The ImageIcon for the corresponding dragon.
   */
  private ImageIcon createDragonIcon(String colorName) {
    String filePath = "/images/" + colorName.toUpperCase() + "Dragon.png";
    return new ImageIcon(getClass().getResource(filePath));
  }

  /**
   * Returns the list of chit cards.
   *
   * @return The list of chit cards.
   */
  public List<ChitCard> getChitCards() {
    return chitCards;
  }

  /**
   * Returns the list of volcano cards.
   *
   * @return The list of volcano cards.
   */
  public List<VolcanoCard> getVolcanoCards() {
    return volcanoCards;
  }

  /**
   * Sets the message label with specified text, color, and font.
   *
   * @param text  The text to display.
   * @param color The color of the text.
   * @param font  The font of the text.
   */
  private void setMessageLabel(String text, Color color, Font font) {
    messageLabel.setText(text);
    messageLabel.setFont(font);
    messageLabel.setForeground(color);
  }
}
