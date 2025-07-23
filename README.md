# Fiery Dragons Board Game

Welcome to the repository for our Java-based game. Below you will find an outline of how the project
is organised and where specific files are located.

## Project Structure

The project is structured to ensure clarity and ease of navigation for developers and users
interested in exploring the source code and resources.

### `Docs` Directory

contains all documentation

### `src/` Directory

This directory contains all the files organised into subdirectories reflecting different components
of the game:

#### `game/`: Contains all Java classes organised further into packages:

- `entities/`: Includes Java classes defining different game entities like Dragon, Player, etc.

- `engine/`: Contains classes related to the game mechanics and logic, such as GameEngine.

- `tiles/`: Houses classes that represent different types of tiles in the game, such as VolcanoCard.

- `utils/`: Includes classes for general utility functions for the game

- `view/`: Includes classes for the UI and handling of the game board and setup menu.

- `chitcards/`: Contains classes for the different types of cards used within the game.

#### `resources/`: This directory includes all non-Java files needed by the game, organised by type:

- `images/`: Contains all image files used in the game, such as icons for dragons, background /images
for game boards, etc.

- `configFiles`: Contains all config json files for the game setup.

## Running the Game

To run the game, navigate to the src directory and compile the Java files using your
preferred development environment

Ensure that you have the necessary Java JDK installed on your system, it may have to be JDK 22.0.1.

The Jar file was tested on an M1 mac

## License

This project is licensed under the MIT License.

## Credit

Made by: Behnam Mozafari, Shivansh Chaddha, Alvis Tong

all images were made by Alvis Tong with resources from https://boardgamegeek.com/boardgame/23658/fiery-dragons 
