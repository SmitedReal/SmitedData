public class example {
public static void main(String[] args) {
   // declaring a new SDAT object creates a file,
   // if it does not already exist.
   SDAT data = new SDAT("data.txt");
   // This thing prints out the lines in the file.
   for (int i = 0; i < data.lineCount(); i++) {
       System.out.println((i + 1) + ": " + data.readLine(i));
   }
}
}
