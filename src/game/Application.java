package game;

import game.engine.GameEngine;
import javax.swing.SwingUtilities;

public class
Application {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      GameEngine gameEngine = new GameEngine();
    });
  }
}
// able to see this right ???