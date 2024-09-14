// Number Game 

import java.util.Scanner;
import java.util.Random;

public class Number_Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        
        while (playAgain) {
            int numberToGuess = random.nextInt(100) + 1;  // Generate random number between 1 and 100
            int numberOfAttempts = 0;
            boolean hasGuessedCorrectly = false;

            System.out.println("I have selected a number between 1 and 99. Try to guess it!");
            
            while (!hasGuessedCorrectly && numberOfAttempts < 10) {  // Limiting attempts to 10
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                numberOfAttempts++;

                if (userGuess < numberToGuess) {
                    System.out.println("You Guess too low!");
                } else if (userGuess > numberToGuess) {
                    System.out.println("You Guess Too high!");
                } else {
                    System.out.println("Congratulations! You've guessed the correct number.");
                    hasGuessedCorrectly = true;
                }
            }

            if (!hasGuessedCorrectly) {
                System.out.println("Sorry, you've used all attempts. The number was: " + numberToGuess);
            }
            
            System.out.println("You took " + numberOfAttempts + " attempts.");
            System.out.print("Do you want to play again? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");
        }

        System.out.println("Thank you for playing!");
        scanner.close();
    }
}


