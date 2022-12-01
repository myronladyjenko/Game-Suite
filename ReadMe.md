# Game Suite Application
_____________________________________________________________________________________________________________________________________________

The Game Suite Application is an application that consists of two games: TicTacToe and Number Scramble (Nmerical TicTacToe). Both of the games are played with two players and on the 3 by 3 grid.

1. TicTacToe. This a standart TicTacToe game. This can can be run through the console as well as the GUI. Players decide for themselves whether they play for 'X' or 'O'. The players are allowed to play in any spot on the 3 by 3 grid, which is not occupied. The game is played until 3 'X' or 'O' are found on the board (horizontally, vertically, or diagonally) in which case either 'X' or 'O' wins. If there are no possible moves left on the board the game is a tie. This game is presented in both console and GUI versions.

2. Numerical TicTacToe. This a TicTacToe game that is played with number. In this game, the players pick who plays with only odd numbers and who uses only even ones. The player with odd numbers always goes first. The game is played until a player makes a line that adds up to 15 (note that all the fields have to filled with no repeated numbers). If all of the positions are filled with no lines summing up to 15, the game is a tie. This game is only presented in the GUI version.

## Description

The Game Suite application is an application that consists of tho games: TicTacToe and Numerical TicTacToe. Both of these games are a two-player games. One game is played with 'X' or 'O' and the other one is with numbers between 0 and 9. Note that TicTacToe game can be played both from the GUI and command-line. Both of the games are played until users exit the game, someone wins or there are no possible moves. 

&nbsp;

1. TicTacToe game command-line version:
    When the game starts, welcome message in printed on the console and the user is prompted to enter one of the menu options:
    * Start a new game
    * Load a game from a file
    * Exit

    Also, players are asked whether they want to turn on autosave (this way the file name is prompted once). Once the user starts the new game or loads the game, the proper player turn is determined and the players can start playing the TicTacToe game. The users are allowed to exit the game and save at any point in time.

2. TicTacToe game GUI version:
    When the user clikcs on the button: "Start TicTacToe Game", the TicTacToe view with the grid opens up. In that view the user is able to save and load the TicTacToe boards. The two players play until someone wins, the users decide to exit the game or the game is a tie. The user is able to save/load as well as exit/switch the game at any point during the game.

3. Numerical TicTacToe GUI version:
    When the user clikcs on the button: "Start Numerical TicTacToe Game", the Numerical TicTacToe view with the grid opens up. In that view the user is able to save and load the Numerical TicTacToe boards. The two players play until someone wins, the users decide to exit the game or the game is a tie. The user is able to save/load as well as exit/switch the game at any point during the game. 

&nbsp;

* The Player profile:
    The player profile is of the following format:

        PlayerName   W   L   T   TG
        PlayerOne    0   0   0   0
        PlayerTwo    0   0   0   0

        ... where W - Number of Wins, L - Losses, T - Ties, TG - Total Games.

    The option to load/save players profile is provided to the user everytime the user enter/quits from a game (TicTacToe or Numerical TicTacToe). Note that the profile is not displayed in the GUI (you can view the file that you saved the profile to). For the TicTacToe game, player X - is playerOne and O - playerTwo. For the Numerical TicTacToeGame, player O (player with odd numbers) - playerOne and player E - playerTwo. Each time the game is switched, the player profile resets to 0 unless the file is loaded with previously saved data.

_____________________________________________________________________________________________________________________________________________

* Main GUI window:
    The main GUI window contains the buttons to start TicTacToe game and Numerical TictacToe. It also contains the Welcome Message as well as the buttons to save and load the user profiles. The user profile contains information about the player's wins, loses, ties as well as total games. Note that at any point in the program, the user is allowed to switch any game.

* Overview of the Game Suite: the Game Suite application consists of 5 packages: boardgame, ui, game, tictactoe, and numericaltictactoe. In total, the Game Suite application contains 20 classes, 4 of which are user-defined exception classes. The boardgame package contains general classes about any boardgame (interface, abstract class). The game package contains classes that are used throughout the whole program. This package has classes that control and set up the games. Packages numericaltictactoe and tictactoe contains games specific implementations of the methods specified in the classes of the boardgame package. The Game Suite is structured in a way that utilizes inhertance and polymorphism through abstract classes and interfaces.

## Getting Started

### Dependencies

1. In order to compile and run the program through gradle, JDK 11 version or above should be installed. 
To run the application, the local instalation of gradle should be used (version 7.5.1). Also, the application can be run through the gradle extension.
2. In order to compile and run the application without gradle, JDK version 17 or above should be installed. The application then can be run by using 'javaC' command with all the classes with properly specified paths and combining the output to a jar. After, you can run the application by using 'java' command and the compiled .jar file.

### Executing program

To compile and run the application through gradle, we first need to get to the right folder, which in our case is A3. After, to run the program you could follow the steps below:

* To build the program the following command should be run:
```
gradle clean build
```
* OUTPUT:\
Starting a Gradle Daemon (subsequent builds will be faster)\
BUILD SUCCESSFUL in 6s\
4 actionable tasks: 4 executed

* The below command provides instructions on how to run the program
```
gradle run 
```
* OUTPUT:\
Task :run\
To run the GUI version of the game suite:\
java -jar build/libs/A3.jar\

    To run the TextUI version of the TicTacToe game:\
    java -cp build/classes/java/main game.TextUI\

    BUILD SUCCESSFUL in 432ms\
    1 actionable task: 1 executed

* To run the program with the console version, the following command should be executed:
```
java -cp build/classes/java/main game.TextUI
```

* To run the program with GUI version, the following command should be executed:
```
java -jar build/libs/A3.jar
```

## Limitations

For the TicTacToe game console version, the user is only allowed to save to the assets folder.
For the player profile, the file must have the player names as PlayerOne and PlayerTwo.

## Author Information

Full Name: Myron Ladyjenko\
Email: mladyjen@uoguelph.ca\
Phone: +1 (343) 264-3588

## Development History

Major development steps:
* 1.7
    * This commit includes the addition of the example of player profile file and the changes to ReadMe (added profile section).
    * See [7b3eff41c1bc6c35f4a09568e4e9628dca093a7e](Added example of playerProfile file)
* 1.6
    * This commit includes changes for the saving and loading of the player profile as well as fixed issues with removing actionListeners
    * See [92be81cadd3ffd1355ccdfb9b4f3fd9a1fc8e34e](Added saving and loading player profile + fixed the issues with switching with games)
* 1.5
    * Created structure of the player profile and added proper parsing for saving and loading.
    * See [18c8a2e698cc1adcd6c27e42ddf77a3196e2f4e9](Added parsing for the player loading + keeping track)
* 1.4
    * Added javadoc comments to all files in the codebase.
    * See [42b031f2a436e79788cc00c4cf8fc5c81a9efad5](Added Javadoc comments)
* 1.3
    * Created a class for saving and loading for the GUI version of the games that use FileHandling class to reuse code
    * See [7d3bd73c458c3f4b60df965c6b2ff67b81131581](Created a class for lading/saving for GUI)
* 1.2
    * Includes changes to the prompting for save when the user tries the exit the game and proper re-prompting.
    * See [4e090c9b2658bf1dbff17bf1886a162c27c47f3b](Added re-promting for saving/loading invalid file formats)
* 1.1
    * Created GUI class for Numerical TicTacToe game and fixed logic for the loading of the game.
    * See [830d07f3166682a2ede29c1ca50221404fae42a6](Created GUI for Numerical TicTacToe + fixed all the bugs in the backend for loading)
* 1.0
    * Made GUI more visually pleasable + this commit also includes changes from previous commit where modification for saving/loading were made in the ()...)View class for the games.
    * See [90c58f39441dade4e47222b501095bbaeec16140](Made GUI for TicTacToe bigger + resizable)
* 0.9
    * Created logic for the Numerical TicTacToe game.
    * See [904b394e37b04457c543b93946bd7145861005cb](Created backend for Numerical TicTacToe)
* 0.8
    * Apart from updating saving for TicTacToe board, this commit also includes polishing TextUI, and changes where the build.gradle and GameUI class has been slightly altered.
    * See [e90c4eb24522376ec00ef9cc5bcd2b45e44240a5](Fixed saving for the board for TicTacToe)
* 0.7
    * Added commit. This commit also contains some minor changes that resulted from moving classes between packages for proper program structure.
    * See [775af08a0b9236562c4eadd52e5d26aa301f4c0b](Created ReadMe)
* 0.6
    * Added initial version of the GUI + added javadoc comments for public and protected methods
    * See [4e4cc6edcecc169da1dc034ddc9928eea0f6d65a](Finised loading/saving from GUI + javadoc comments)
* 0.5
    * Created the GUI for the TicTacToe game and adjusted for proper input
    * See [26c90255bd0d0a8b3b52333192a135b77e12daab](Created GUI for TicTacToe. Added saving and proper inputs)
* 0.4
    * Implemented the TextUI class to run the TicTacToe game from the console
    * See [795ce7368ce0af9195e9a78f0fdd4f69fe2bf05f](Create full TextUI for TicTacToe started working on UI)
* 0.3
    * Created the backend logic for the TicTacToe game.
    * See [31a6bc5034568eca30515b8e042c802a61a0b4bd](Added basic functionality for TicTacToe game)
* 0.2
    * Understood the structure of the program (use of the interface, abstract class and abstract methods). Created classes for the proper packages for the two required games.
    * See [49e12deeb9eaa2dfb8f7cd3fe60ad947e06dd9dc](Created initial classes)
* 0.1
    * Initial Release. See [31673c77e3d4e5b727b574b889da4d93cb61b4d7]

## Acknowledgments

Inspiration, code snippets, etc.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [simple-readme] (https://gist.githubusercontent.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc/raw/d59043abbb123089ad6602aba571121b71d91d7f/README-Template.md)
