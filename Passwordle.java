import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class Passwordle {
   public static void main(String[] args) throws IOException {

      String wotd = getWordOfTheDay();
      //System.out.println(wotd);

      Scanner user_scanner = new Scanner(System.in);
   
      System.out.println("\nWelcome to Passwordle :) \nThe bad password of the day is " + wotd.length() + " characters long. \nGood luck!\n");
      
      int num_lives = wotd.length();

      while (num_lives > 0) {
         boolean correctLength = false;

         System.out.println("Enter your guess (guesses remaining: " + num_lives + ")");
         
         String guess = "";
         while (!correctLength) {
            guess = user_scanner.nextLine();
            if (guess.length() != wotd.length()) { 
               System.out.println("Incorrect word length you silly goose! Try again...");
            } else {
               correctLength = true;
            }
         }

         System.out.println("\n" + formatGuess(guess));
         String feedback = printFeedback(wotd, guess);
         System.out.println(feedback + "\n");

         if (checkIfWon(wotd.length(), feedback)) {
            System.out.println("Completed <3");
            System.exit(0);
         }

         num_lives --;
      };

      System.out.println("Uh oh... Game over (you should've guessed '" + wotd + "')");
      user_scanner.close();
   }

   private static boolean checkIfWon(int word_size, String guess) {
      String correct = ""; 
      for (int i=0; i<word_size; i++) {
         correct += "🟩 ";
      }
      if (guess.equals(correct)) return true;
      return false;
   }

   private static String printFeedback(String wotd, String guess) {

      String response = "";
      for (int i=0; i<wotd.length(); i++) {
         if (wotd.contains(String.valueOf(guess.charAt(i))) && wotd.charAt(i) != guess.charAt(i)) {
            response = response + "🟨 ";
         } else if (wotd.charAt(i) == guess.charAt(i)) {
            response = response + "🟩 ";
         } else {
            response = response + "⬜️ ";
         }
      }
      return response.toString();
   }

   private static String getWordOfTheDay() throws FileNotFoundException {
      File password_file = new File("password_list.txt");
      Scanner file_scanner = new Scanner(password_file);

      int num_lines = 0;
      while(file_scanner.hasNextLine()) {
         num_lines++;
         file_scanner.nextLine();
      }

      Random random = new Random();
      int line_number = random.nextInt(num_lines);

      file_scanner.close();
      Scanner file_scanner1 = new Scanner(password_file);
      for (int i=0; i<line_number; i++) {
          file_scanner1.nextLine();
      }
      
      String wotd = file_scanner1.nextLine();
      file_scanner1.close();
      return wotd;
   }

   private static String formatGuess(String guess) {
      String formattedGuess = "";
      for (int i=0; i<guess.length(); i++) {
         formattedGuess += guess.charAt(i) + "  ";
      }
      return formattedGuess;
   }

   private static boolean checkWord(String guess) throws FileNotFoundException {
      File password_file = new File("password_list.txt");
      Scanner fileScanner = new Scanner(password_file);

      int num_lines = 0;
      while(fileScanner.hasNextLine()) {
         num_lines++;
         fileScanner.nextLine();
      }

      for (int i=0; i<num_lines; i++) {
         if (fileScanner.nextLine().equals(guess)) {
            fileScanner.close();
            return true;
         }
      }
      fileScanner.close();
      return false;
   }

}