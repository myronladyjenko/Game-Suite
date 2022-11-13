package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import boardgame.Saveable;

/**
 * This class is used as a helper class that is used for File Handling. The purpose of this class is to
 * load from file and save to file. In both cases we are dealing with the string representation of the board, 
 * however, the class doesn't know that the string in any way relates to the board.
 * 
 * @author Myron Ladyjenko
 */
public class FileHandling {
    private static boolean successfulLoadFromFile = false;
    private static StringBuilder strToStoreBoard = new StringBuilder("");;

    /**
     * The function takes a file name and a Saveable object as parameters. It reads the file line by
     * line and stores the lines in a StringBuilder object. It then calls the loadSavedString() method
     * of the Saveable object and passes the StringBuilder object as a parameter
     * 
     * @param fileName the name of the file to load from
     * @param toLoad the object that will be loaded from the file
     * @throws ThrowExceptionFileActionHasFailed throws this exception when file doesn't exists or fialed to open
     */
    public static void loadFile(String fileName, Saveable toLoad) throws ThrowExceptionFileActionHasFailed {
        String oneLine = "";
        BufferedReader myReader;
        Path path = FileSystems.getDefault().getPath(fileName);
        try {
            myReader = Files.newBufferedReader(path);
            while ((oneLine = myReader.readLine()) != null) {
                strToStoreBoard.append(oneLine + "\n");
            }
            myReader.close();
            successfulLoadFromFile = true;
            toLoad.loadSavedString(strToStoreBoard.toString());
        } catch (Exception e) {
            throw new ThrowExceptionFileActionHasFailed("Provided file name is incorrect or doesn't exist or"
                                                        + " failed to open: " + fileName);
        }
    }

    /**
     * Check if the file exists, if not create it, then write the string to the file.
     * 
     * @param fileName The name of the file to save to.
     * @param toSave The object that is to be saved to the file.
     * @throws ThrowExceptionFileActionHasFailed an exception that occurs when file couldn't open (or get created)
     */
    public static void saveToFile(String fileName, Saveable toSave) throws ThrowExceptionFileActionHasFailed {
        checkFileExistsOtherwiseCreate(fileName);

        Path path = FileSystems.getDefault().getPath(fileName);
        try {
            Files.writeString(path, toSave.getStringToSave());
            successfulLoadFromFile = true;
        } catch(IOException e) {
            throw new ThrowExceptionFileActionHasFailed("Unable to write to the file: " + fileName);
        }
    }

    private static void checkFileExistsOtherwiseCreate(String fileName) 
                                                throws ThrowExceptionFileActionHasFailed {
        File file = new File(fileName);

        if (!file.exists()) {
            try {
                file.createNewFile(); 
            } catch (Exception ex) {
                throw new ThrowExceptionFileActionHasFailed("Couldn't open the file " + fileName);
            }
        }
    }

    /**
     * This function returns the value of the boolean variable successfulLoadFromFile
     * 
     * @return The boolean value of successfulLoadFromFile.
     */
    public static boolean getLoadFromFileResult() {
        return successfulLoadFromFile;
    }

    /**
     * It returns the string representation of the board
     * 
     * @return The string representation of the board.
     */
    public static String getStringBoard() {
        return strToStoreBoard.toString();
    }

    /**
     * The function returns a string representation of the board
     * 
     * @return The string representation of the board.
     */
    public String toString() {
        return strToStoreBoard.toString();
    }
}

