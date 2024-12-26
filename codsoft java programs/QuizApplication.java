import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApplication {

    private static int score = 0;
    private static int timeLimit = 10; // Time limit for each question in seconds
    private static boolean answered = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Quiz Questions and Answers
        String[] questions = {
            "What is the capital of France?",
            "Who developed the theory of relativity?",
            "What is the largest planet in our Solar System?"
        };

        String[][] options = {
            {"1. Paris", "2. London", "3. Berlin", "4. Madrid"},
            {"1. Isaac Newton", "2. Albert Einstein", "3. Galileo Galilei", "4. Marie Curie"},
            {"1. Earth", "2. Jupiter", "3. Saturn", "4. Neptune"}
        };

        int[] correctAnswers = {1, 2, 2}; // Correct option indices

        // Loop through each question
        for (int i = 0; i < questions.length; i++) {
            System.out.println("\nQuestion " + (i + 1) + ": " + questions[i]);

            // Display options
            for (String option : options[i]) {
                System.out.println(option);
            }

            // Timer logic
            answered = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!answered) {
                        System.out.println("\nTime's up! Moving to the next question...");
                        answered = true;
                        synchronized (scanner) {
                            scanner.notify();
                        }
                    }
                }
            }, timeLimit * 1000);

            // User input
            synchronized (scanner) {
                int userAnswer = -1;
                while (!answered) {
                    System.out.print("Your answer (1-4): ");
                    try {
                        userAnswer = scanner.nextInt();
                        answered = true;
                        if (userAnswer == correctAnswers[i]) {
                            System.out.println("Correct!");
                            score++;
                        } else {
                            System.out.println("Wrong! The correct answer was: " + correctAnswers[i]);
                        }
                        timer.cancel();
                        scanner.notify();
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a number between 1 and 4.");
                        scanner.nextLine(); // Clear the invalid input
                    }
                }
            }
        }

        // Display results
        System.out.println("\nQuiz Over!");
        System.out.println("Your final score: " + score + "/" + questions.length);
        scanner.close();
    }
}