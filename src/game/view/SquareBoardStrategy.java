package game.view;

import game.chitcards.ChitCard;
import game.tiles.VolcanoCard;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The SquareBoardStrategy class implements the SetBoardStrategy interface and provides the setup
 * for displaying chit cards and volcano cards on the game board.
 */
public class SquareBoardStrategy implements SetBoardStrategy {

  private static final Dimension CHIT_CARD_SIZE = new Dimension(55, 55);
  private static final Dimension VOLCANO_CARD_SIZE = new Dimension(140, 140);
  private static final Dimension EMPTY_SPACE_SIZE = new Dimension(50, 50);

  /**
   * Sets up the game board by configuring the chit card area and the volcano card border.
   *
   * @param board The GameBoard object to set up.
   */
  @Override
  public void setUpBoard(GameBoard board) {
    setUpChitCardPanel(board);
    setUpVolcanoCardBorder(board);
  }

  /**
   * Sets up the area to display chit cards. This method shuffles the chit cards and adds them to
   * the panel.
   *
   * @param board The GameBoard object containing the chit cards to be displayed.
   */
  private void setUpChitCardPanel(GameBoard board) {
    JPanel chitCardPanel = createChitCardPanel();
    List<ChitCard> chitCards = board.getChitCards();
    Collections.shuffle(chitCards);

    JPanel cardHolder = createCardHolderPanel(chitCards);

    chitCardPanel.add(cardHolder);
    chitCardPanel.add(board.getMessagePanel(),
        BorderLayout.CENTER); // Add the message panel above the chit cards
    board.getPanel().add(chitCardPanel, BorderLayout.CENTER);
  }

  /**
   * Creates the panel to hold chit cards.
   *
   * @return The created JPanel.
   */
  private JPanel createChitCardPanel() {
    JPanel chitCardPanel = new JPanel();
    chitCardPanel.setLayout(new BoxLayout(chitCardPanel, BoxLayout.Y_AXIS));
    chitCardPanel.setBorder(BorderFactory.createTitledBorder("Chit Cards"));
    return chitCardPanel;
  }

  /**
   * Creates the panel to hold the chit cards.
   *
   * @param chitCards The list of chit cards to add to the panel.
   * @return The created JPanel.
   */
  private JPanel createCardHolderPanel(List<ChitCard> chitCards) {
    JPanel cardHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
    for (ChitCard card : chitCards) {
      JButton cardButton = card.getButton();
      cardButton.setPreferredSize(CHIT_CARD_SIZE);
      cardHolder.add(cardButton);
    }
    return cardHolder;
  }

  /**
   * Sets up the border area to display volcano cards. This method divides the border into north,
   * south, east, and west panels, and adds volcano cards to them.
   *
   * @param board The GameBoard object containing the volcano cards to be displayed.
   */
  private void setUpVolcanoCardBorder(GameBoard board) {
    List<VolcanoCard> volcanoCards = board.getVolcanoCards();
    int totalCards = volcanoCards.size();
    int cardsPerSide = totalCards / 4;
    int remainingCards = totalCards % 4;

    JPanel northPanel = createBorderPanel(1, cardsPerSide + (remainingCards > 0 ? 1 : 0));
    JPanel southPanel = createBorderPanel(1, cardsPerSide + (remainingCards > 1 ? 1 : 0));
    JPanel eastPanel = createBorderPanel(cardsPerSide + (remainingCards > 2 ? 1 : 0), 1);
    JPanel westPanel = createBorderPanel(cardsPerSide, 1);

    JPanel empty1 = new JPanel();
    empty1.add(board.getSaveGameButton(), BorderLayout.WEST);

    // Create an inner top panel for layout adjustments
    JPanel innerTopPanel = new JPanel(new BorderLayout());
    innerTopPanel.add(empty1, BorderLayout.NORTH);
    JPanel empty2 = new JPanel();
    JPanel empty3 = new JPanel();
    JPanel empty4 = new JPanel();
    empty1.setPreferredSize(EMPTY_SPACE_SIZE);
    empty2.setPreferredSize(EMPTY_SPACE_SIZE);
    empty3.setPreferredSize(EMPTY_SPACE_SIZE);
    empty4.setPreferredSize(EMPTY_SPACE_SIZE);
    northPanel.add(empty1);
    southPanel.add(empty3);

    distributeVolcanoCards(volcanoCards, northPanel, southPanel, eastPanel, westPanel);
    northPanel.add(empty2);
    southPanel.add(empty4);

    board.getPanel().add(northPanel, BorderLayout.NORTH);
    board.getPanel().add(southPanel, BorderLayout.SOUTH);
    board.getPanel().add(westPanel, BorderLayout.WEST);
    board.getPanel().add(eastPanel, BorderLayout.EAST);
  }

  /**
   * Creates a panel for the border area.
   *
   * @param rows The number of rows for the panel grid.
   * @param cols The number of columns for the panel grid.
   * @return The created JPanel.
   */
  private JPanel createBorderPanel(int rows, int cols) {
    return new JPanel(new GridLayout(rows, cols));
  }

  private void distributeVolcanoCards(List<VolcanoCard> volcanoCards, JPanel northPanel,
      JPanel southPanel, JPanel eastPanel, JPanel westPanel) {
    int totalCards = volcanoCards.size();
    int cardsPerSide = totalCards / 4;
    int remainingCards = totalCards % 4;

    int index = 0;

    // Distribute cards to north panel
    for (int i = 0; i < cardsPerSide + (remainingCards > 0 ? 1 : 0); i++) {
      volcanoCards.get(index).addSquaresNorth();
      northPanel.add(setSize(volcanoCards.get(index++), VOLCANO_CARD_SIZE));
    }

    // Distribute cards to east panel
    for (int i = 0; i < cardsPerSide + (remainingCards > 1 ? 1 : 0); i++) {
      volcanoCards.get(index).addSquaresEast();
      eastPanel.add(setSize(volcanoCards.get(index++), VOLCANO_CARD_SIZE));
    }
    int tempInd = index + cardsPerSide + (remainingCards > 2 ? 1 : 0) - 1;
    // Distribute cards to south panel
    for (int i = 0; i < cardsPerSide + (remainingCards > 2 ? 1 : 0); i++) {
      volcanoCards.get(tempInd).addSquaresSouth();
      southPanel.add(setSize(volcanoCards.get(tempInd--), VOLCANO_CARD_SIZE));
    }
    tempInd = index + cardsPerSide + (remainingCards > 2 ? 1 : 0) + cardsPerSide - 1;
    // Distribute cards to west panel
    for (int i = 0; i < cardsPerSide; i++) {
      volcanoCards.get(tempInd).addSquaresWest();
      westPanel.add(setSize(volcanoCards.get(tempInd--), VOLCANO_CARD_SIZE));
    }
  }

  /**
   * Sets the preferred size of a volcano card panel to the specified size.
   *
   * @param volcanoCard The VolcanoCard object to set the size for.
   * @param size        The preferred size to set.
   * @return The JPanel component with the preferred size set.
   */
  private Component setSize(VolcanoCard volcanoCard, Dimension size) {
    JPanel panel = volcanoCard.getPanel();
    panel.setPreferredSize(size);
    return panel;
  }
}
