package smitedreal.smiteddata.examples;

import smitedreal.smiteddata.SDAT;

public class readLines {
    public static void main(String[] args) {
        // declaring a new sdat.SDAT object creates a file,
        // if it does not already exist.
        SDAT data = new SDAT("readLines.sdat");
        // This thing prints out the lines in the file.
        for (int i = 0; i < data.lineCount(); i++) {
            System.out.println((i + 1) + ": " + data.readLine(i));
        }
    }
}
