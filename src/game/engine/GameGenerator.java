package game.engine;

import com.google.gson.Gson;
import game.chitcards.ChitCard;
import game.entities.Dragon;
import game.tiles.Cave;
import game.tiles.VolcanoCard;
import game.utils.GameUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * Generates and initializes all game components including dragons, caves, chit cards, and volcano
 * cards.
 */
public class GameGenerator extends AbstractGameGenerator {

  private final Config config;


  /**
   * Constructs a GameGenerator and initializes game components based on the specified number of
   * players.
   *
   * @param count The number of players for which to generate game components.
   */
  public GameGenerator(int count) {
    this.config = getConfig(GameUtils.CONFIG_PATH);
    this.cardFactory = new DefaultCardFactory();
    createDragons(count);
    createChitCards();
  }

  /**
   * Constructs a GameGenerator and initializes game components based on the specified number of
   * players and card factory.
   *
   * @param count       The number of players for which to generate game components.
   * @param cardFactory The factory used to create card pools
   * @param config      The configuration class
   */
  public GameGenerator(int count, AbstractCardFactory cardFactory, Config config) {
    this.cardFactory = cardFactory;
    this.config = config;
    createDragons(count);
    createChitCards();

  }

  /**
   * Retrieves the list of dragons generated for the game.
   *
   * @return List of dragons.
   */
  public List<Dragon> getDragons() {
    return dragons;
  }

  /**
   * Retrieves the list of volcano cards generated for the game.
   *
   * @return List of volcano cards.
   */
  public List<VolcanoCard> getVolcanoCards() {
    return volcanoCards;
  }

  /**
   * Retrieves the list of chit cards generated for the game.
   *
   * @return List of chit cards.
   */
  public List<ChitCard> getChitCards() {
    return chitCards;
  }

  /**
   * Creates and initializes dragons for the game based on the number of players. Assigns each
   * dragon a cave and a starting position on a volcano card.
   *
   * @param count The number of players, which determines the number of dragons to create.
   */
  protected void createDragons(int count) {
    // get strings of caves from config file
    List<String> caveStrings = this.config.getCaves();

    // create caves
    for (int i = 0; i < count; i++) {
      availableCaves.add(cardFactory.createCave(caveStrings.get(i)));
    }

    createVolcanoCards();

    // assign dragons to volcano card
    int i = 1;
    dragons.add(new Dragon(volcanoCards.get(0), availableCaves.get(0)));
    availableCaves.get(0).setOccupied(dragons.get(0));
    if (count >= 3) {
      dragons.add(new Dragon(volcanoCards.get(2), availableCaves.get(2)));
      availableCaves.get(2).setOccupied(dragons.get(i));
      i++;
    }
    dragons.add(new Dragon(volcanoCards.get(4), availableCaves.get(1)));
    availableCaves.get(1).setOccupied(dragons.get(i));
    i++;
    if (count == 4) {
      dragons.add(new Dragon(volcanoCards.get(6), availableCaves.get(3)));
      availableCaves.get(3).setOccupied(dragons.get(i));
    }
  }

  /**
   * Initializes volcano cards with their respective squares and optionally a cave. Sets left and
   * right neighbors for each card to form a circular linked list, facilitating game logic that
   * requires knowing adjacent cards.
   */
  protected void createVolcanoCards() {
    // get strings of volcanoes from config file
    List<String> volcanoStrings = this.config.getVolcanoCards();

    // initialisation local variable to control creation of volcano card
    int j = 1;
    int rows = 2;
    int cols = 3;
    boolean reverse;
    Cave cave = null;
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

      // set cave accordingly
      if (i % 2 == 0) {
        switch (i) {
          case 0 -> cave = availableCaves.get(0);
          case 2 -> cave = availableCaves.size() > 2 ? availableCaves.get(2) : null;
          case 4 -> cave = availableCaves.get(1);
          case 6 -> cave = availableCaves.size() > 3 ? availableCaves.get(3) : null;
        }
      } else {
        cave = null;
      }

      // set card
      tempCard.initialiseVolcano(cave, reverse, rows, cols);
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
   * Creates a set of chit cards used in the game, including different types with varying move
   * capabilities. This includes cards for bats, spiders, salamanders, baby dragons, and pirate
   * dragons, each affecting gameplay in specific ways.
   */
  protected void createChitCards() {
    // the list of all the chit cards
    List<String> chitStrings = this.config.getChitCards();

    // the Hashmap of chit cards and their move count
    Map<String, List<Integer>> chitCardMoves = this.config.getChitCardMoves();

    // the list of move count of each chit card
    List<Integer> eachChitCardMoves;
    for (String chitStr : chitStrings) {
      eachChitCardMoves = chitCardMoves.get(chitStr);

      for (Integer numMoves : eachChitCardMoves) {
        chitCards.add(cardFactory.createChitCard(chitStr, numMoves));
      }
    }
  }

  /**
   * Loads and returns a configuration object from the specified path. This method uses Gson to
   * parse the JSON configuration file.
   *
   * @param path the path to the configuration file
   * @return the loaded Config object, or null if there was an error reading the file
   */
  private Config getConfig(String path) {
    Gson gson = new Gson();

    // Load JSON file from resources
    InputStream inputStream = GameGenerator.class.getResourceAsStream(path);

    if (inputStream == null) {
      System.out.println("File not found!");
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      // Parse JSON using Gson
      return gson.fromJson(reader, Config.class);
    } catch (Exception e) {
      System.out.println("Error reading JSON file: " + e.getMessage());
    }
    return null;
  }
}
