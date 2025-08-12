package smitedreal.smiteddata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * The main file for the SDAT Libary
 */
public class SDAT {
    private File file;

    /**
     * this is the SDAT thing I don't know how its called
     * @param path the path in which to make the file
     */
    public SDAT(String path) {
        try {
            this.file = new File(path);
            if (file.createNewFile()) {
            // Create file
            System.out.println(file.getName() + ": File created.");
            } else {
            // file already exists, do nothing
            System.out.println(file.getName() + ": File already exists.");
            }
        } catch (IOException e) {
            // Handle errors
            System.out.println(file.getName() + ": Error Occurred: " + e);
        }
    }

    /**
     * This function gets the lines of the file
     * @return Returns all the lines in the file
     */

    public List<String> getLines() {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
            return null;
        }
    }

    /**
     * this function reads the specified line
     * @param index The index of the line you are trying to read
     * @return returns the line
     */

    public String readLine(int index) {
        try {
            if (Files.readAllLines(file.toPath()).get(index).equalsIgnoreCase("")) {
                return null;
            } else {
                return Files.readAllLines(file.toPath()).get(index);
            }
        } catch (IOException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
            return null;
        }
    }

    /**
     * this function tells you the number of lines in the file
     * @return returns the number of lines in the file
     */
    public int lineCount() {
        int count = 0;
        try {
            for (int i = 0; i < Files.readAllLines(file.toPath()).size(); i++) count++;
            return count;
        } catch (IOException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
            return 0;
        }
    }

    /**
     * this function writes a line to the file
     * @param line the line to write to the file
     */
    public void writeLine(String line) {
        try {
            Files.write(file.toPath(), (line + System.lineSeparator()).getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
        }
    }

    /**
     * this function does the same thing as writeLine, but just doesn't add a new line
     * @param line the line to write to the file
     */
    public void write(String line) {
        try {
            Files.write(file.toPath(), line.getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
        }
    }

    /**
     * this function writes a new file
     */
    public void newLine() {
        try {
            Files.write(file.toPath(), System.lineSeparator().getBytes(), java.nio.file.StandardOpenOption.APPEND);
        }  catch (IOException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
        }
    }

    /**
     * this function is the same as writeLine, but writes multiple files
     * @param lines the list of lines to write
     */
    public void writeLines(List<String> lines) {
        try {
            Files.write(file.toPath(), lines, java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
        }
    }

    /**
     * this function removes the whole files
     */
    public void deleteFile() {
        if (file.delete()) {
            System.out.println(file.getName() + ": File deleted");
        } else {
            System.out.println(file.getName() + ": File deleted");
        }
    }

    /**
     * this function tells you the path for the file
     * @return returns the path of the file
     */
    public String getPath() {
        return file.getPath();
    }

    /**
     * this function tells you the name of the file
     * @return returns the file name
     */
    public String getName() {
        return file.getName();
    }

    /**
     * this function tells you if the file exists
     * @return returns if the file exists
     */
    public boolean exists() {
        return file.exists();
    }

    /**
     * this function gives you the size of the file
     * @return returns the size of the file
     */
    public long getSize() {
        return file.length();
    }

    /**
     * this function gives you the file
     * @return returns the file
     */
    public File getFile() {
        return file;
    }

    /**
     * this function renames the whole file
     * @param newName the new name for the file
     */
    public void renameFile(String newName) {
        File newFile = new File(file.getParent(), newName);
        if (file.renameTo(newFile)) {
            file = newFile;
            System.out.println("Renamed sdat.SDAT file to " + newName);
        } else {
            System.out.println("Failed to rename sdat.SDAT file to " + newName);
        }
    }
    /**
     * this function finds a double in the specified line
     * @param index the index of the line where you get the double
     * @return returns the found double
     */

    public Double getDouble(int index) {
        try {
            String line = Files.readAllLines(file.toPath()).get(index).trim();
            String[] parts = line.split(":");
            return Double.parseDouble(parts[1].trim());
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
            return null;
        }
    }

    /**
     * this function finds a float in the specified line
     * @param index the index of the line where you get the float
     * @return returns the found float
     */

    public Float getFloat(int index) {
        try {
            String line = Files.readAllLines(file.toPath()).get(index).trim();
            String[] parts = line.split(":");
            return Float.parseFloat(parts[1].trim());
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
            return null;
        }
    }

    /**
     * this function finds an integer in the specified line
     * @param index the index of the line where you get the integer
     * @return returns the found integer
     */

    public Integer getInteger(int index) {
        try {
            String line = Files.readAllLines(file.toPath()).get(index).trim();
            String[] parts = line.split(":");
            return Integer.parseInt(parts[1].trim());
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
            return null;
        }
    }

    /**
     * this function finds a long in the specified line
     * @param index the index of the line where you get the long
     * @return returns the found long
     */
    public Long getLong(int index) {
        try {
            String line = Files.readAllLines(file.toPath()).get(index).trim();
            String[] parts = line.split(":");
            return Long.parseLong(parts[1].trim());
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
            return null;
        }
    }

    /**
     * this function removes all the lines in the file
     */
    public void clear() {
        try {
            Files.write(file.toPath(), new byte[0], java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
        }
    }

    /**
     * this function deletes the line specified
     * @param index the index of the line to delete
     */
    public void deleteLine(int index) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (index >= 0 && index < lines.size()) {
                lines.remove(index);
                Files.write(file.toPath(), lines, java.nio.charset.StandardCharsets.UTF_8);
            } else {
                System.out.println(file.getName() + ": Line at index " + index + " doesnt exist.");
            }
        } catch (IOException e) {
            System.out.println(file.getName() + ": Error Occurred: " + e);
        }
    }
}