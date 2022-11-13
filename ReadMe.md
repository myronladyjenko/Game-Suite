# Connect Four Game

!!! IMPORTANT !!! 

As you can see in the repository for A3 and through commits, I have made changes the boardgame package classes. I only modified the spacing/comments. I have not modified any logic or method signatures. I realized to late, that we are not suppose to touch those classes at all. I apologize for the confusion.
_____________________________________________________________________________________________________________________________________________

The Game Suite Application is an application that consists of two games: TicTacToe and Number Scramble (Nmerical TicTacToe). Both of the games are played with two players and on the 3 by 3 grid.

1. TicTacToe. This a standart TicTacToe game. This can can be run through the console as well as the GUI. Players decide for themselves whether they play for 'X' or 'O'. The players are allowed to play in any spot on the 3 by 3 grid, which is not occupied. The game is played until 3 'X' or 'O' are found on the board (horizontally, vertically, or diagonally) in which case either 'X' or 'O' wins. If there are no possible moves left on the board the game is a tie.

2. Numerical TicTacToe. This a TicTacToe game that is played with number. In this game, the players pick who plays with only odd numbers and who uses only even ones. The player with odd numbers always goes first. The game is played until a player makes a line that adds up to 15 (note that all the fields have to filled with no repeated numbers). If all of the positions are filled with no lines summing up to 15, the game is a tie.

## Description

The Game Suite Application application is an application that consists of tho games: TicTacToe and Numerical TicTacToe. Both of these games are a two-player games. One game is played with 'X' or 'O' and the other one is with number. Note that TicTacToe game can be played both from the GUI and command-line. Both of the games are played until users exit the game, someone wins or there e are no possible moves.

1. TicTacToe game command-line version:
    When the game starts, welcome message in printed on the console and the user is prompted to enter one of the menu options:
    * 1) Start a new game
    * 2) Load a game from a file
    * 3) Exit

Also, players are asked whether they want to turn on autosave (the file name is prompted once).

Once the user select the option, the program will print the current state of the board on the console and prompt the user to input a column to put his token in. After the user makes a move, the current state of the board is printed and the user is prompted to save the current state of the board to the file (if autosave option is specified, the board gets automatically saved). This process repeats until someone win or the game is a tie. If the file is saved, the user gets an option to quit the game by returning to the main menu and exiting.

My Connect Four game consists of 12 classes, 5 of which are user-defined exception classes. The classes are structured in way that Board class controls the state of the board and the board itself is build on BoardCell pieces, which represent each cell. The ConnectFour class is used to control the flow of the game based on the information that other classes could provided. TextUI class is used for interactions with the user(printing and getting the input). FileHandling, Player and Runner are suplementary classes the help with the building the game itself. For further description of the classes refer to the comment above each class in the source code.

## Getting Started

### Dependencies

1. In order to compile and run the program through gradle, JDK 11 version or above should be installed. 
To run the application, the local instalation of gradle should be used (i. e. version 7.5.1). Also, the application can be run through the gradle extension.
2. In order to run the application without gradle, JDK version 17 or above should be installed. The application then can be run by using 'javaC' command with all the classes with properly specified paths and combining the output to a jar. After, you can run the application by using 'java' command and the compiled .jar file.

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
Task :run
To run the program:
java -jar build/libs/A3.jar
BUILD SUCCESSFUL in 517ms
1 actionable task: 1 executed

* To run the program the following command should be executed:
```
java -jar build/libs/A3.jar
```

## Limitations

## Author Information

Full Name: Myron Ladyjenko\
Email: mladyjen@uoguelph.ca\
Phone: +1 (343) 264-3588

## Development History

Major development steps:

* 0.8
    * Created an additional test, cleaned build.gradle file as well as fixed checkstyle errors in BoardTest class
    * See [b7c72fc0bbd59d7344f4c8234b14b8766cfe8927](Fixed last test + fixed checkstyle errors + removed 'test' task from build.gradle)
* 0.7
    * Created Junit tests from every method in Board class. Made adjustements to clean code in the classes
    * See [f36009f9dfd0888a71438ced6e7d539fdf2fc952](Minor adjustments to source code + created a Test Suite(JUnit Tests))
* 0.6
    * Added javadoc comments for public methods as well as comments explaining purpose of each class
    * See [1bd12c7f541d713dab6d35f3c77a647bb46461ef](Added all the Javadoc comments + created extra exception class)
* 0.5
    * Implemented file handling
    * See [9697b30aa7d39c39898cf80b07d60a4a76d4f9c7](Created FileHandling class)
* 0.4
    * Added exceptions + created fully functions user menu
    * See [795ce7368ce0af9195e9a78f0fdd4f69fe2bf05f](Deleted validateMove class, added exception for handling user input)
* 0.3
    * Resturctured creating of the board (only Cell class) + Added main functionaly to play the game + added all winConditions
    * See [240fadaf2908128271b14e9b6a12a46f5b1c4fe3](Created all win conditions + playGame functionality)
* 0.2
    * Created classes to create board + created Runner class
    * See [ef7002b1c32a7282e21550719700d45b413938f8](Created initial classes)
* 0.1
    * Initial Release

## Acknowledgments

Inspiration, code snippets, etc.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [simple-readme] (https://gist.githubusercontent.com/DomPizzie/7a5ff55ffa9081f2de27c315f5018afc/raw/d59043abbb123089ad6602aba571121b71d91d7f/README-Template.md)