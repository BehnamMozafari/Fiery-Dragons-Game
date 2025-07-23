package game.engine;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Configuration class representing the structure of the configuration JSON file. This class is used
 * for deserialisation with Gson.
 */
public class Config {

  private List<String> chitCards;
  private List<String> volcanoCards;
  private List<String> caves;
  private Map<String, List<Integer>> chitCardMoves;

  /**
   * Gets the list of chit card names.
   *
   * @return a list of chit card names
   */
  public List<String> getChitCards() {
    return chitCards;
  }

  /**
   * Sets the list of chit card names.
   *
   * @param chitCards a list of chit card names
   */
  public void setChitCards(List<String> chitCards) {
    this.chitCards = validateList(chitCards, "chitCards");
  }

  /**
   * Gets the list of volcano card descriptions.
   *
   * @return a list of volcano card descriptions
   */
  public List<String> getVolcanoCards() {
    return volcanoCards;
  }

  /**
   * Sets the list of volcano card descriptions.
   *
   * @param volcanoCards a list of volcano card descriptions
   */
  public void setVolcanoCards(List<String> volcanoCards) {
    this.volcanoCards = validateList(volcanoCards, "volcanoCards");
  }

  /**
   * Gets the list of cave descriptions.
   *
   * @return a list of cave descriptions
   */
  public List<String> getCaves() {
    return caves;
  }

  /**
   * Sets the list of cave descriptions.
   *
   * @param caves a list of cave descriptions
   */
  public void setCaves(List<String> caves) {
    this.caves = validateList(caves, "caves");
  }

  /**
   * Gets the map of chit card moves.
   *
   * @return a map where the key is the chit card name and the value is a list of possible moves
   */
  public Map<String, List<Integer>> getChitCardMoves() {
    return chitCardMoves;
  }

  /**
   * Sets the map of chit card moves.
   *
   * @param chitCardMoves a map where the key is the chit card name and the value is a list of
   *                      possible moves
   */
  public void setChitCardMoves(Map<String, List<Integer>> chitCardMoves) {
    this.chitCardMoves = Objects.requireNonNull(chitCardMoves, "chitCardMoves cannot be null");
  }

  /**
   * Validates a list and ensures it is not null.
   *
   * @param list      The list to validate.
   * @param fieldName The name of the field being validated.
   * @return The validated list.
   */
  private List<String> validateList(List<String> list, String fieldName) {
    return Objects.requireNonNull(list, fieldName + " cannot be null");
  }
}
