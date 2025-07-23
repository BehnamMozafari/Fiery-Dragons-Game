package game.chitcards;

import static game.utils.GameUtils.CHITCARD_SIZE;

import game.engine.GameEngine;
import game.engine.Savable;
import game.entities.Dragon;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class ChitCard implements Savable {

  private static boolean allowFlipping = true;
  private final JButton button;  // Composition: use a JButton internally
  private final Integer numMoves;
  protected ImageIcon frontIcon;
  protected ImageIcon backIcon;
  private boolean flipped = false;  // variable to track flip status

  /**
   * Constructs a new ChitCard with the specified number of moves and front icon path.
   *
   * @param numMoves              The number of moves the card will allow when played.
   * @param originalFrontIconPath The path to the image file used as the front icon.
   */
  public ChitCard(int numMoves, String originalFrontIconPath) {
    this.numMoves = numMoves;
    this.button = new JButton();  // Initialize the internal JButton

    Dimension smallButtonSize = CHITCARD_SIZE;  // Define the size for icons

    // Load and scale the front icon
    ImageIcon originalFrontIcon = new ImageIcon(getClass().getResource(originalFrontIconPath));
    this.frontIcon = new ImageIcon(originalFrontIcon.getImage()
        .getScaledInstance(smallButtonSize.width, smallButtonSize.height, Image.SCALE_SMOOTH));

    // Load and scale the back icon
    ImageIcon originalBackIcon = new ImageIcon(getClass().getResource("/images/CardCover.png"));
    this.backIcon = new ImageIcon(originalBackIcon.getImage()
        .getScaledInstance(smallButtonSize.width, smallButtonSize.height, Image.SCALE_SMOOTH));

    button.setIcon(backIcon);  // Start with the back icon
    button.addActionListener(e -> performAction());
  }

  /**
   * Disables the ability to flip any chit cards.
   */
  public static void toggleAllowFlippingFalse() {
    ChitCard.allowFlipping = false;
  }

  /**
   * Enables the ability to flip chit cards.
   */
  public static void toggleAllowFlippingTrue() {
    ChitCard.allowFlipping = true;
  }

  /**
   * Processes the action when the card is clicked on. This method must be implemented by concrete
   * card classes to define specific actions.
   *
   * @param dragon The dragon playing the card.
   */
  public abstract void accept(Dragon dragon);

  /**
   * Returns the number of moves associated with this card.
   *
   * @return the number of moves.
   */
  public Integer getNumMoves() {
    return numMoves;
  }

  private void performAction() {
    if (!this.flipped && ChitCard.allowFlipping) {
      ChitCard.allowFlipping = false;
      this.flip();
      Dragon curDragon = GameEngine.getInstance().getCurrentDragon();
      GameEngine.getInstance().addChitCard(this);
      this.accept(curDragon);
      ChitCard.allowFlipping = true;
    }
  }

  /**
   * Returns the JButton associated with this chit card.
   *
   * @return the button representing this chit card.
   */
  public JButton getButton() {
    return button;
  }

  /**
   * Returns the front icon of the card.
   *
   * @return the front icon ImageIcon object.
   */
  public ImageIcon getFrontIcon() {
    return frontIcon;
  }

  /**
   * Returns the back icon of the card.
   *
   * @return the back icon ImageIcon object.
   */
  public ImageIcon getBackIcon() {
    return backIcon;
  }

  /**
   * Flips the card to show the other icon depending on the current state.
   */
  public void flip() {
    if (this.flipped) {
      button.setIcon(backIcon);
      this.flipped = false;
    } else {
      button.setIcon(frontIcon);
      this.flipped = true;
    }
  }

}