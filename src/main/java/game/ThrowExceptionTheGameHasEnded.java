package game;

/**
 * This class defines a user-defined exception 'ThrowExceptionTheGameHasEnded'. 
 * This exception gets thrown when the loaded game is ended 
 * (there is winning combination on the board or the game is a tie)
 * 
 * @author Myron Ladyjenko
 */
public class ThrowExceptionTheGameHasEnded extends Exception {
    /**
     * This constructor calls constructor of the exception class with the message passed as a parameter
     * whenever this type of exception thrown from any of the classes
     * 
     * @param exceptionMessage the message to be displayed to the user
     */
    public ThrowExceptionTheGameHasEnded(String exceptionMessage) {
            super(exceptionMessage);
    }
}
