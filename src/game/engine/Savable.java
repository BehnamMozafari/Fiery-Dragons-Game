package game.engine;

/**
 * An interface to be implemented by classes that need to support saving their state. This interface
 * defines a method for converting an object's state to a JSON string representation.
 */
public interface Savable {

  /**
   * Converts the state of the implementing object to a JSON string representation.
   *
   * @return A JSON string that represents the current state of the object.
   */
  String saveState();
}