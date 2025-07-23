package game.tiles;

import java.awt.Color;

/**
 * Represents an iterator for traversing squares on volcano cards. It iterates over the squares of a
 * volcano card and can move to the next or previous square.
 */
public class VolcanoCardIterator {

  private VolcanoCard currentCard;
  private int currentIndex;

  /**
   * Constructs a VolcanoCardIterator with the specified current card and index.
   *
   * @param currentCard  The current volcano card.
   * @param currentIndex The current index within the card.
   */
  public VolcanoCardIterator(VolcanoCard currentCard, int currentIndex) {
    this.currentCard = currentCard;
    this.currentIndex = currentIndex;
  }

  /**
   * Returns the current VolcanoCard.
   *
   * @return current VolcanoCard.
   */
  public VolcanoCard getCurrentCard() {
    return currentCard;
  }

  /**
   * Returns the current Square index.
   *
   * @return current index.
   */
  public int getCurrentIndex() {
    return currentIndex;
  }

  /**
   * Sets the current index.
   *
   * @param tmpSquareIndex new index.
   */
  public void setCurrentIndex(int tmpSquareIndex) {
    this.currentIndex = tmpSquareIndex;
  }

  /**
   * Returns the current Square.
   *
   * @return current Square.
   */
  public Square getSquare() {
    return this.currentCard.getSquare(currentIndex);
  }

  /**
   * Moves to the next square in the iterator sequence. If the current index exceeds the number of
   * squares on the card, it moves to the next card.
   *
   * @return The next square in the iterator sequence.
   */
  private Square next() {
    if (this.currentIndex == -1) {
      this.currentIndex = this.currentCard.getCaveIndex();
    }
    this.currentIndex++;
    if (this.currentIndex > this.currentCard.getNumSquares() - 1) {
      this.currentCard = this.currentCard.getRightNeighbour();
      this.currentIndex = 0;
    }
    return this.currentCard.getSquare(this.currentIndex);
  }

  /**
   * Moves to the previous square in the iterator sequence. If the current index becomes negative,
   * it moves to the previous card.
   *
   * @return The previous square in the iterator sequence.
   */
  private Square prev() {
    this.currentIndex--;
    if (this.currentIndex < 0) {
      this.currentCard = this.currentCard.getLeftNeighbour();
      this.currentIndex = this.currentCard.getNumSquares() - 1;
    }
    return this.currentCard.getSquare(this.currentIndex);
  }

  /**
   * Iterates over a number of spaces and returns the resulting square.
   *
   * @param spaces The number of spaces to move.
   * @return The resulting square.
   */
  public Square iterate(int spaces) {
    Square curSquare = this.getSquare();
    int i = 0;
    while (i > spaces) {
      curSquare = this.prev();
      i--;
    }
    while (i < spaces) {
      curSquare = this.next();
      i++;
    }
    return curSquare;
  }

  /**
   * Peeks at a number of spaces ahead and returns the resulting square without changing the current
   * position.
   *
   * @param spaces The number of spaces to move.
   * @return The resulting square.
   */
  public Square peek(int spaces) {
    VolcanoCard tempCard = this.currentCard;
    int tempInd = this.currentIndex;
    Square curSquare = this.getSquare();
    int i = 0;
    while (i > spaces) {
      curSquare = this.prev();
      i--;
    }
    while (i < spaces) {
      curSquare = this.next();
      i++;
    }
    this.currentIndex = tempInd;
    this.currentCard = tempCard;
    return curSquare;
  }

  /**
   * Checks if the dragon with colour passes its cave given the number of spaces moved
   *
   * @param colour colour of dragon
   * @param spaces number of spaces moved, either +ve or -ve
   * @return boolean value if the cave has been passed or not
   */
  public boolean passesCave(Color colour, int spaces) {
    if (this.currentIndex == -1) {
      return false;
    }
    VolcanoCard tempCard = this.currentCard;
    int tempInd = this.currentIndex;
    int i = 0;
    boolean passes = false;
    while (i > spaces) {
      this.prev();
      if (this.currentCard.getCaveColour() == colour
          && this.currentIndex == this.currentCard.getCaveIndex()) {
        passes = true;
      }
      i--;
    }
    while (i < spaces) {
      this.next();
      if (this.currentCard.getCaveColour() == colour
          && this.currentIndex > this.currentCard.getCaveIndex() || (
          this.currentCard.getCaveColour() == colour
              && this.currentIndex == this.currentCard.getCaveIndex() && i < (spaces - 1))) {
        passes = true;
      }
      i++;
    }
    this.currentIndex = tempInd;
    this.currentCard = tempCard;
    return passes;
  }

  /**
   * Checks if the dragon with colour is on a cave given the number of spaces moved.
   *
   * @param colour colour of dragon
   * @param spaces number of spaces moved, either +ve or -ve
   * @return boolean denoting if the dragon is on its cave or not
   */
  public boolean onCave(Color colour, int spaces) {
    VolcanoCard tempCard = this.currentCard;
    int tempInd = this.currentIndex;
    int i = 0;
    while (i > spaces) {
      this.prev();
      i--;
    }
    while (i < spaces) {
      this.next();
      i++;
    }
    boolean isOnCave = this.currentCard.getCaveColour() == colour
        && this.currentIndex == this.currentCard.getCaveIndex();
    this.currentIndex = tempInd;
    this.currentCard = tempCard;
    return isOnCave;
  }
}
