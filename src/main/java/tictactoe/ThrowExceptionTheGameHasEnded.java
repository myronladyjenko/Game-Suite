package tictactoe;

/**
 * This class defines a user-defined exception 'ThrowExceptionWrongMoveOnBoard'. 
 * This exception gets thrown when the user tries to make an invalid move on the board. 
 * The passed message gets to the superclass where gets set and is accessible afterwards.
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
