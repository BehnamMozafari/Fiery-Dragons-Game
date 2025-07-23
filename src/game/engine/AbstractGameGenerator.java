package game.engine;

import game.chitcards.ChitCard;
import game.entities.Dragon;
import game.tiles.Cave;
import game.tiles.VolcanoCard;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates and initializes all game components including dragons, caves, chit cards, and volcano
 * cards.
 */
public abstract class AbstractGameGenerator {

    protected final List<Dragon> dragons = new ArrayList<>();
    protected final List<VolcanoCard> volcanoCards = new ArrayList<>();
    protected final List<Cave> availableCaves = new ArrayList<>();
    protected final List<ChitCard> chitCards = new ArrayList<>();
    protected AbstractCardFactory cardFactory;


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
    abstract void createDragons(int count);

    /**
     * Initializes volcano cards with their respective squares and optionally a cave. Sets left and
     * right neighbors for each card to form a circular linked list, facilitating game logic that
     * requires knowing adjacent cards.
     */
    abstract void createVolcanoCards();

    /**
     * Creates a set of chit cards used in the game, including different types with varying move
     * capabilities. This includes cards for bats, spiders, salamanders, baby dragons, and pirate
     * dragons, each affecting gameplay in specific ways.
     */
    abstract void createChitCards();
}
