package smitedreal.smiteddata;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class SDAT {

    /*
    THIS LIBARY IS STRAIGHT UP DOGSHIT, SO PLEASE DONT USE IT
    */
    private File file;

    public SDAT(String path) {
        try {
            this.file = new File(path);
            if (file.createNewFile()) {
                System.out.println("Created new sdat.SDAT file at " + path);
            } else {
                System.out.println("sdat.SDAT file already exists at " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLines() {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            return null;
        }
    }

    public String readLine(int index) {
        try {
            if (Files.readAllLines(file.toPath()).get(index) == "") {
                return null;
            } else {
                return Files.readAllLines(file.toPath()).get(index);
            }
        } catch (IOException e) {
            return null;
        }
    }
    public int lineCount() {
        int count = 0;
        try {
            for (String line : Files.readAllLines(file.toPath())) {
                count++;
            }
            return count;
        } catch (IOException e) {
            return 0;
        }
    }
    public void writeLine(String line) {
        try {
            Files.write(file.toPath(), (line + System.lineSeparator()).getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String line) {
        try {
            Files.write(file.toPath(), line.getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newLine() {
        try {
            Files.write(file.toPath(), System.lineSeparator().getBytes(), java.nio.file.StandardOpenOption.APPEND);
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeLines(List<String> lines) {
        try {
            Files.write(file.toPath(), lines, java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deleteFile() {
        if (file.delete()) {
            System.out.println("Deleted sdat.SDAT file at " + file.getPath());
        } else {
            System.out.println("Failed to delete sdat.SDAT file at " + file.getPath());
        }
    }
    public String getPath() {
        return file.getPath();
    }
    public String getName() {
        return file.getName();
    }
    public boolean exists() {
        return file.exists();
    }
    public long getSize() {
        return file.length();
    }
    public void renameFile(String newName) {
        File newFile = new File(file.getParent(), newName);
        if (file.renameTo(newFile)) {
            file = newFile;
            System.out.println("Renamed sdat.SDAT file to " + newName);
        } else {
            System.out.println("Failed to rename sdat.SDAT file to " + newName);
        }
    }

    public Double getDouble(int index) {
        try {
            String line = Files.readAllLines(file.toPath()).get(index).trim();
            String[] parts = line.split(":");
            return Double.parseDouble(parts[1].trim());
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Float getFloat(int index) {
        try {
            String line = Files.readAllLines(file.toPath()).get(index).trim();
            String[] parts = line.split(":");
            return Float.parseFloat(parts[1].trim());
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public Integer getInteger(int index) {
        try {
            String line = Files.readAllLines(file.toPath()).get(index).trim();
            String[] parts = line.split(":");
            return Integer.parseInt(parts[1].trim());
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }


    public void clear() {
        try {
            Files.write(file.toPath(), new byte[0], java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Cleared all content from the sdat.SDAT file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteLine(int index) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            if (index >= 0 && index < lines.size()) {
                lines.remove(index);
                Files.write(file.toPath(), lines, java.nio.charset.StandardCharsets.UTF_8);
                System.out.println("Deleted line at index " + index + " from the sdat.SDAT file.");
            } else {
                System.out.println("Index out of bounds. No line deleted.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}