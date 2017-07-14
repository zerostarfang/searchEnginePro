package com.app.backend.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {

    ///Files
    private File file = null;
    ///File readers
    private FileReader fr = null;
    ///Buffered Reader
    private BufferedReader br = null;

    /**
     * Class constructor
     *
     * @param fileName Path to file
     */
    public ReadFile(String fileName) {

        try {
            this.file = new File(fileName);
            this.fr = new FileReader(file);
            this.br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            System.out.println("An error has ocurred during file opening. Error message: " + e.toString());
            this.closeFile();
        }

    }

    /**
     * Close file method
     */
    private void closeFile() {
        try {
            if (null != fr) {
                this.fr.close();
            }
        } catch (IOException e) {
            System.out.println("An error has occurred during the file closing. Error message: " + e.toString());
        }

    }

    /**
     * Method to read a new line
     *
     * @return New string with the next line
     */
    public String readLine() {

        String line = null;
        try {
            if ((line = this.br.readLine()) != null) {
                return line;
            }
        } catch (IOException e) {
            System.out.println("An error has occurred during the line reading. Error message: " + e.toString());
            this.closeFile();
        }
        return line;
    }
}
