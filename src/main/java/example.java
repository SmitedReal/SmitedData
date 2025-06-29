import java.io.IOException;

public class example {
    public static void main(String[] args) {
        // Create sample data
        SDAT data = SDAT.object()
                .put("name", "John Doe")
                .put("secretData", "This is confidential!")
                .put("numbers", SDAT.array().add(1).add(2).add(3));

        try {
            // Save to binary file
            data.saveToFile("data.sdat");
            System.out.println("Saved data to data.sdat");

            // Load from binary file
            SDAT loaded = SDAT.loadFromFile("data.sdat");
            System.out.println("Loaded data:");
            System.out.println(loaded.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}