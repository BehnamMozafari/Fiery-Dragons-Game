package game.view;

import com.google.gson.Gson;
import game.engine.SavedState;
import game.utils.GameUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Represents the setup menu panel for configuring the game settings. It allows players to select
 * the number of players and start the game or load a saved game.
 */
public class SetupMenu {

  private static final String BACKGROUND_IMAGE_PATH = "/images/firey_dragons_background.png";
  private static final String TITLE_TEXT = "Firey Dragons";
  private static final String PLAYER_LABEL_TEXT = "Players:";
  private static final String START_BUTTON_TEXT = "Start Game";

  private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 50);
  private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 24);
  private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);

  private static final Dimension PLAYER_COUNT_SIZE = new Dimension(60, 30);
  private static final Dimension BUTTON_SIZE = new Dimension(640, 50);
  private static final int VERTICAL_STRUT_SIZE = 20;
  private static final Dimension SPACING_SIZE = new Dimension(10, 0);

  private final JPanel mainPanel;
  private final ImageIcon backgroundImage;
  private JButton startGameButton;
  private JButton loadGameButton;
  private JButton clearSelectionButton;
  private JComboBox<Integer> playerCount;
  private String selectedFilePath = null;
  private JLabel selectedFileLabel;

  /**
   * Constructs a SetupMenu with the specified ActionListener for the start game button.
   *
   * @param startGameListener The ActionListener for the start game button.
   */
  public SetupMenu(ActionListener startGameListener) {
    backgroundImage = loadImage(BACKGROUND_IMAGE_PATH);
    mainPanel = createMainPanel();
    initialiseMenu(startGameListener);
  }

  /**
   * Loads an image from the specified path.
   *
   * @param path The path to the image.
   * @return The loaded ImageIcon.
   */
  private ImageIcon loadImage(String path) {
    return new ImageIcon(
        Objects.requireNonNull(getClass().getResource(path), "Background image not found"));
  }

  /**
   * Creates the main panel with custom painting.
   *
   * @return The created JPanel.
   */
  private JPanel createMainPanel() {
    return new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
          g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
      }
    };
  }

  /**
   * Initializes the menu components.
   *
   * @param startGameListener The ActionListener for the start game button.
   */
  private void initialiseMenu(ActionListener startGameListener) {
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel titleLabel = createTitleLabel();
    JPanel playerSelectionPanel = createPlayerSelectionPanel();
    startGameButton = createStartButton(startGameListener);

    // Load Game Button
    loadGameButton = new JButton("Load Game");
    loadGameButton.setFont(new Font("Arial", Font.BOLD, 24));
    loadGameButton.setForeground(Color.BLACK);
    loadGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    loadGameButton.setMaximumSize(new Dimension(640, 50));
    loadGameButton.addActionListener(e -> showFileChooser());

    // Label to display the selected file
    selectedFileLabel = new JLabel("No save file selected");
    selectedFileLabel.setFont(new Font("Arial", Font.ITALIC, 20));
    selectedFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Clear Selection Button
    clearSelectionButton = new JButton("Clear Selection");
    clearSelectionButton.setFont(new Font("Arial", Font.BOLD, 24));
    clearSelectionButton.setForeground(Color.BLACK);
    clearSelectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    clearSelectionButton.setMaximumSize(new Dimension(640, 50));
    clearSelectionButton.addActionListener(e -> clearFileSelection());
    clearSelectionButton.setVisible(false);

    mainPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SIZE)); // Add space at the top
    mainPanel.add(titleLabel);
    mainPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SIZE)); // Space before player selection
    mainPanel.add(playerSelectionPanel); // Add the player selection panel
    mainPanel.add(Box.createVerticalStrut(VERTICAL_STRUT_SIZE)); // Space before button
    mainPanel.add(Box.createVerticalStrut(20)); // Space before load game button
    mainPanel.add(loadGameButton);
    mainPanel.add(Box.createVerticalStrut(10)); // Space before selected file label
    mainPanel.add(selectedFileLabel);
    mainPanel.add(Box.createVerticalStrut(10)); // Space before clear selection button
    mainPanel.add(clearSelectionButton);
    mainPanel.add(Box.createVerticalStrut(20)); // Space before start game button
    mainPanel.add(startGameButton);

    // add resources test json to system directory
    loadSavedFiles();
  }

  /**
   * Creates the title label.
   *
   * @return The created JLabel.
   */
  private JLabel createTitleLabel() {
    JLabel titleLabel = new JLabel(TITLE_TEXT);
    titleLabel.setFont(TITLE_FONT);
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    return titleLabel;
  }

  /**
   * Creates the player selection panel.
   *
   * @return The created JPanel.
   */
  private JPanel createPlayerSelectionPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    panel.setOpaque(false);
    panel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel playerLabel = new JLabel(PLAYER_LABEL_TEXT);
    playerLabel.setFont(LABEL_FONT);

    playerCount = new JComboBox<>(new Integer[]{2, 3, 4});
    playerCount.setPreferredSize(PLAYER_COUNT_SIZE);
    playerCount.setMaximumSize(playerCount.getPreferredSize()); // Ensure the combo box doesn't grow

    panel.add(playerLabel);
    panel.add(Box.createRigidArea(SPACING_SIZE)); // Add some spacing
    panel.add(playerCount);

    return panel;
  }

  /**
   * Creates the start button.
   *
   * @param startGameListener The ActionListener for the start game button.
   * @return The created JButton.
   */
  private JButton createStartButton(ActionListener startGameListener) {
    JButton button = new JButton(START_BUTTON_TEXT);
    button.setFont(BUTTON_FONT);
    button.setForeground(Color.BLACK);
    button.setAlignmentX(Component.CENTER_ALIGNMENT);
    button.setMaximumSize(BUTTON_SIZE);
    button.addActionListener(startGameListener);
    return button;
  }

  /**
   * Shows a file chooser dialog to select a save file from the 'resources' folder.
   */
  private void showFileChooser() {
    // Specify the path to the save files folder in the user's home directory
    String userHome = System.getProperty("user.home");
    String saveFilesPath = userHome + File.separator + "SaveFilesFieryDragons";
    File saveFilesDirectory = new File(saveFilesPath);

    // Create the SaveFiles directory if it does not exist
    if (!saveFilesDirectory.exists()) {
      saveFilesDirectory.mkdirs();
    }

    JFileChooser fileChooser = new JFileChooser(saveFilesDirectory);
    fileChooser.setFileFilter(new FileNameExtensionFilter("Save Files", "json"));
    fileChooser.setAcceptAllFileFilterUsed(false);
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

    int returnValue = fileChooser.showOpenDialog(null);
    if (returnValue == JFileChooser.APPROVE_OPTION) {
      selectedFilePath = fileChooser.getSelectedFile().getPath();
      selectedFileLabel.setText("Selected save file: " + fileChooser.getSelectedFile().getName());
      clearSelectionButton.setVisible(true);
      System.out.println("Selected save file: " + selectedFilePath);
    }
  }

  /**
   * Clears the selected file.
   */
  private void clearFileSelection() {
    selectedFilePath = null;
    selectedFileLabel.setText("No save file selected");
    clearSelectionButton.setVisible(false);
  }

  /**
   * Returns the selected file path.
   *
   * @return The selected file path.
   */
  public String getSelectedFilePath() {
    return selectedFilePath;
  }

  /**
   * Returns the JComboBox used for selecting the number of players.
   *
   * @return The JComboBox for selecting the number of players.
   */
  public JComboBox<Integer> getPlayerCount() {
    return playerCount;
  }

  /**
   * Returns the main panel of the setup menu.
   *
   * @return The main panel.
   */
  public JPanel getMainPanel() {
    return mainPanel;
  }

  /**
   * add resources the test json to system directory
   */
  private void loadSavedFiles() {
    Gson gson = new Gson();
    SavedState savedState = null;
    // Load JSON file from resources
    try (InputStream inputStream = getClass().getResourceAsStream(GameUtils.DIFF_CONFIG_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      // Parse JSON using Gson
      savedState = gson.fromJson(reader, SavedState.class);
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (IOException e) {
      System.out.println("Error reading JSON file: " + e.getMessage());
    }

    GameUtils.writeJsonFile(savedState, GameUtils.SYSTEM_SAVE_PATH);
  }
}