package game.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.engine.SavedState;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class GameUtils {

  public static final Dimension SQUARE_SIZE = new Dimension(120, 120);
  public static final Dimension CHITCARD_SIZE = new Dimension(70, 70);
  public static final Integer BOARD_SIZE = 24;
  public static final Integer GAMEFRAME_SIZE = 800;
  public static final String SYSTEM_SAVE_PATH = System.getProperty("user.home") + File.separator
      + "SaveFilesFieryDragons/TestDifferentBoardConfig.json";
  public static final String CONFIG_PATH = "/configFiles/defaultConfig.json";
  public static final String DIFF_CONFIG_PATH = "/configFiles/TestDifferentBoardConfig.json";
  private static final Map<Color, String> colorToStringMap = new HashMap<>();
  private static final Map<String, Color> stringToColorMap = new HashMap<>();
  private static final String BASE_SAVE_PATH =
      System.getProperty("user.home") + File.separator + "SaveFilesFieryDragons/game";
  private static final String FILE_EXTENSION = ".json";
  private static final String COLOR_WHITE = "White";
  private static final String COLOR_ORANGE = "Orange";
  private static final String COLOR_BLUE = "Blue";
  private static final String COLOR_GREEN = "Green";
  private static final String COLOR_UNKNOWN = "Unknown";

  static {
    colorToStringMap.put(Color.WHITE, COLOR_WHITE);
    colorToStringMap.put(Color.ORANGE, COLOR_ORANGE);
    colorToStringMap.put(Color.BLUE, COLOR_BLUE);
    colorToStringMap.put(Color.GREEN, COLOR_GREEN);

    stringToColorMap.put(COLOR_WHITE.toLowerCase(), Color.WHITE);
    stringToColorMap.put(COLOR_ORANGE.toLowerCase(), Color.ORANGE);
    stringToColorMap.put(COLOR_BLUE.toLowerCase(), Color.BLUE);
    stringToColorMap.put(COLOR_GREEN.toLowerCase(), Color.GREEN);
  }

  /**
   * Private constructor to prevent instantiation of the GameUtils class. Throws an
   * UnsupportedOperationException if instantiation is attempted.
   */
  private GameUtils() {
    // Private constructor to prevent instantiation
    throw new UnsupportedOperationException("Utility class should not be instantiated");
  }

  /**
   * Converts a Color to a String representation.
   *
   * @param color The color to convert.
   * @return The string representation of the color.
   */
  public static String colorToString(Color color) {
    return colorToStringMap.getOrDefault(color, COLOR_UNKNOWN);
  }

  /**
   * Converts a color name to a Color object.
   *
   * @param colorName The color name.
   * @return The Color object.
   */
  public static Color getColorFromName(String colorName) {
    return stringToColorMap.getOrDefault(colorName.toLowerCase(), Color.BLACK);
  }

  /**
   * Gets the save path for the current save file.
   *
   * @return save path string
   */
  public static String getSavePath() {
    // Get the current date and time
    LocalDateTime now = LocalDateTime.now();

    // Define a formatter for the date and time
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_yyyy-MM-dd_HH-mm-ss");

    // Format the date and time as a string
    String formattedDateTime = now.format(formatter);
    return BASE_SAVE_PATH + formattedDateTime + FILE_EXTENSION;
  }

  /**
   * Initialises the SavedState object by reading a JSON file from the specified path.
   *
   * @param path the path to the JSON file containing the saved state.
   * @return the SavedState object parsed from the JSON file, or null if an error occurs.
   */
  public static SavedState initialiseSavedState(String path) {
    Gson gson = new Gson();
    // Load JSON file from resources
    try (InputStream inputStream = new FileInputStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      // Parse JSON using Gson
      return gson.fromJson(reader, SavedState.class);
    } catch (FileNotFoundException e) {
      System.out.println("File not found at path: " + path);
    } catch (IOException e) {
      System.out.println("Error reading JSON file: " + e.getMessage());
    }
    return null;
  }

  /**
   * Writes the given SavedState object to a JSON file at the specified save path.
   *
   * @param savedState the SavedState object to be serialized and saved.
   * @param savePath   the path where the JSON file will be saved.
   */
  public static void writeJsonFile(SavedState savedState, String savePath) {
    if (savedState != null) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      // Create the directories if they do not exist
      File file = new File(savePath);
      file.getParentFile().mkdirs();

      // Serialize the Config object to JSON and save to the resource directory
      try (FileWriter writer = new FileWriter(file)) {
        gson.toJson(savedState, writer);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
