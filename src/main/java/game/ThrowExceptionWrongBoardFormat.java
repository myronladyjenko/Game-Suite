package game;

/**
 * This class defines a user-defined exception 'ThrowExceptionWrongBoardFormat'. 
 * This exception occures when the board read from the file is corrupted (in in-proper format). 
 * The passed message gets to the superclass where gets set and is accessible afterwards.
 * 
 * @author Myron Ladyjenko
 */
public class ThrowExceptionWrongBoardFormat extends Exception {
    /**
     * This constructor calls constructor of the exception class with the message passed as a parameter
     * whenever this type of exception thrown from any of the classes
     * 
     * @param exceptionMessage the message to be displayed to the user
     */
    public ThrowExceptionWrongBoardFormat(String exceptionMessage) {
            super(exceptionMessage);
    }
}
