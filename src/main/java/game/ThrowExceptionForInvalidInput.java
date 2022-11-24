package game;

/**
 * This class defines a user-defined exception 'ThrowExceptionForInvalidInput'. 
 * This exception occures when user input is in correct (non-correct characters). 
 * The passed message gets to the superclass where gets set and is accessible afterwards.
 * 
 * @author Myron Ladyjenko
 */
public class ThrowExceptionForInvalidInput extends Exception {
    /**
     * This constructor calls constructor of the exception class with the message passed as a parameter
     * whenever this type of exception thrown from any of the classes
     * 
     * @param exceptionMessage the message to be displayed to the user
     */
    public ThrowExceptionForInvalidInput(String exceptionMessage) {
            super(exceptionMessage);
    }
}
