package smitedreal.smiteddata.examples;

import smitedreal.smiteddata.SDAT;

public class writingLines {
    private static SDAT file;
    private static int number;
    public static void main(String[] args) {
        try {
            if (args.length == 2) {
                file = new SDAT(args[0]);
                number = Integer.parseInt(args[1]);
            } else {
                System.out.println(file.getName() + ": Usage: writingLines <file.extension> <lineCount>");
            }
        } catch (NumberFormatException e) {
            System.out.println(file.getName() + ": Usage: writingLines <file.extension> <lineCount>");
        }
        file.clear();
        for (int i = 0; i < number; i++) {
            file.writeLine("Line: " + (i + 1));
        }
        System.out.println(file.getName() + ": Written " + number + " Lines");
    }
}
