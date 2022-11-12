package game;

/**
 * This class defines a user-defined exception 'ThrowExceptionNoSuchFileExists'. 
 * This exception occures when the file inputed by the user doesn't exist or in wrong format. 
 * The passed message gets to the superclass where gets set and is accessible afterwards.
 * 
 * @author Myron Ladyjenko
 */
public class ThrowExceptionFileActionHasFailed extends Exception {
    /**
     * This constructor calls constructor of the exception class with the message passed as a parameter
     * whenever this type of exception thrown from any of the classes
     * 
     * @param exceptionMessage the message to be displayed to the user
     */
    public ThrowExceptionFileActionHasFailed(String exceptionMessage) {
            super(exceptionMessage);
    }
}
