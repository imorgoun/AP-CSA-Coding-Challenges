import java.util.*;

public class GuessingGame {
    public static int numberOfGames = 0; public static int totalGuesses = 0; public static int bestScore = 1000;
    public static void newGame() {
        Random random = new Random(); Scanner input = new Scanner(System.in);
        int currentGuess = 102; int chosenInt = random.nextInt(10)+1; int numberOfGuesses = 0;
        String[] answersYes = {"YES"}; String answerContinue; numberOfGames++;
        System.out.println("I'm thinking of a number between 1 and 100...");
        while (currentGuess != chosenInt) {numberOfGuesses++;System.out.println("Your Guess?"); currentGuess = input.nextInt();
            if (currentGuess == chosenInt) {System.out.println("You got it right in " + numberOfGuesses + " guesses!");}
            else if (currentGuess > chosenInt) {System.out.println("It's lower.");}
            else {System.out.println("It's higher.");}
        }
        if(numberOfGuesses < bestScore) {bestScore = numberOfGuesses;} totalGuesses += numberOfGuesses;
        System.out.println("Do you want to play again?"); input.nextLine();
        answerContinue = input.nextLine();
        for (int i = 0; i < answersYes.length; i++) {if (answersYes[i].equals(answerContinue)) {
                newGame();}}
        System.out.println("Overall results:"); System.out.println("Total games     = " + numberOfGames);
        System.out.println("Total guesses   = " + totalGuesses);
        System.out.println("Guesses/game    = " + totalGuesses/numberOfGuesses);
        System.out.println("Best game       = " + bestScore);
    }
    public static void main(String[] args) {newGame();}
}
