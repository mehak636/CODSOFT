import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        int totalScore = 0;

        System.out.println("Welcome to the Number Guessing Game!");

        while (playAgain) {
            int generatedNumber = random.nextInt(100) + 1; // Random number between 1 and 100
            int attempts = 0;
            int maxAttempts = 10;
            boolean guessedCorrectly = false;

            System.out.println("\nA number has been generated between 1 and 100. You have " + maxAttempts + " attempts to guess it!");

            while (attempts < maxAttempts && !guessedCorrectly) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == generatedNumber) {
                    guessedCorrectly = true;
                    int score = maxAttempts - attempts + 1; // Higher score for fewer attempts
                    totalScore += score;
                    System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                    System.out.println("You earned " + score + " points. Total score: " + totalScore);
                } else if (userGuess < generatedNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("\nYou've used all your attempts. The correct number was " + generatedNumber + ".");
            }

            System.out.print("\nDo you want to play another round? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");
        }

        System.out.println("\nGame over! Your final score is: " + totalScore);
        System.out.println("Thank you for playing!");
        scanner.close();
    }
}