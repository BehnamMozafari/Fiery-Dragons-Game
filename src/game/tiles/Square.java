package game.tiles;

import static game.utils.GameUtils.SQUARE_SIZE;

import game.chitcards.BabyDragonCard;
import game.chitcards.BatCard;
import game.chitcards.SalamanderCard;
import game.chitcards.SpiderCard;
import game.engine.Savable;
import game.entities.Dragon;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Square class represents a square on the game board. Each square can be occupied by a dragon and
 * has its own icon.
 */
public abstract class Square implements Savable {

  private final JPanel panel;
  protected Dragon occupied;
  protected ImageIcon icon; // Icon for the square itself

  /**
   * Constructs a Square with the specified icon.
   *
   * @param iconPath The path to the icon file.
   */
  public Square(String iconPath) {
    panel = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (icon != null) {
          g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        if (occupied != null) {
          ((Graphics2D) g).setStroke(new BasicStroke(2));
          g.setColor(Color.RED);
          g.drawRect(5, 5, 25, 25);
          g.drawImage(occupied.getIcon().getImage(), 5, 5, 25, 25, this);
        }
      }
    };
    ImageIcon tempIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
    icon = new ImageIcon(
        tempIcon.getImage()
            .getScaledInstance(SQUARE_SIZE.width, SQUARE_SIZE.height, Image.SCALE_SMOOTH));
    panel.setLayout(new BorderLayout());
    panel.setPreferredSize(SQUARE_SIZE);
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    panel.setOpaque(false);
  }

  /**
   * Gets the dragon occupying the square.
   *
   * @return The dragon occupying the square.
   */
  public Dragon getOccupied() {
    return occupied;
  }

  public JPanel getPanel() {
    return panel;
  }

  /**
   * Interacts with a dragon using a BatCard.
   *
   * @param dragon The dragon to interact with.
   * @param card   The BatCard used for interaction.
   */
  public abstract void interact(Dragon dragon, BatCard card);

  /**
   * Interacts with a dragon using a SpiderCard.
   *
   * @param dragon The dragon to interact with.
   * @param card   The SpiderCard used for interaction.
   */
  public abstract void interact(Dragon dragon, SpiderCard card);

  /**
   * Interacts with a dragon using a SalamanderCard.
   *
   * @param dragon The dragon to interact with.
   * @param card   The SalamanderCard used for interaction.
   */
  public abstract void interact(Dragon dragon, SalamanderCard card);

  /**
   * Interacts with a dragon using a BabyDragonCard.
   *
   * @param dragon The dragon to interact with.
   * @param card   The BabyDragonCard used for interaction.
   */
  public abstract void interact(Dragon dragon, BabyDragonCard card);

  /**
   * Checks if the square is occupied by a dragon.
   *
   * @return True if the square is occupied, false otherwise.
   */
  public boolean isOccupied() {
    return occupied != null;
  }

  /**
   * Sets the dragon occupying the square.
   *
   * @param dragon The dragon to occupy the square.
   */
  public void setOccupied(Dragon dragon) {
    this.occupied = dragon;
    panel.repaint();
  }

  /**
   * Clears the occupation of the square.
   */
  public void clearOccupied() {
    setOccupied(null);
  }
}
