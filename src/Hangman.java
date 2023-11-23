import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

    static final String[] HANGMAN = {
        "\n\t    _____    ",
        "\n\t    |/   |   ",
        "\n\t    |  (x_x) ",
        "\n\t    |  / | \\",
        "\n\t    |    |   ",
        "\n\t    |   / \\  ",
        "\n\t    |        ",
        "\n\t   _|____    "
    };

    public static void main(String[] args) {

        boolean gameState = true;

        System.out.print("\n\tTHANK YOU FOR JOINING ME IN A GAME OF HANGMAN! \n\tA NEW WORD HAS BEEN CHOSEN FOR YOU!");

        //RUN THE GAME
        gameLoop(gameState);

    }

    private static void gameLoop(boolean gameState){

        //SET UP WORD
        String[] wordPool = {"Sacrifice", "Imitation", "Mirror", "Wizard", "Transmutation", "Wonderful", "Situation",
                "Chocolate", "Meaningless", "Romance", "Gamification"};
        int wordUsedIndex = new Random().nextInt(wordPool.length - 1);
        String wordUsed = wordPool[wordUsedIndex];

        //THE HANGMAN AND LOOSING
        int countDown = 0;
        int maxGuesses = HANGMAN.length;

        //PLAYER INVENTORY
        char[] letterPool = new char[wordUsed.length()];
        for (int i = 0; i < wordUsed.length(); i++) {
            letterPool[i] = '*';
        }
        List<String> guessedLetters = new ArrayList<>();

        while (gameState) {
            printWord(letterPool);
            countDown = checkLetterpool(letterPool, wordUsed, getLetter(guessedLetters), countDown, HANGMAN);
            gameState = winningCondition(letterPool, wordUsed);
            if (gameState) {
                gameState = losingCondition(countDown, maxGuesses);
            }
        }
    }

    /**
     * Prints the word or stars
     *
     * @param letterPool chars already guessed and/or stars
     */
    public static void printWord(char[] letterPool) {

        System.out.print("\n\t");

        for (char c : letterPool) {
            System.out.print(c);
        }
    }

    /**
     * Let's the player guess a letter
     *
     * @return the guessed letter
     */
    public static char getLetter(List<String> guessedLetter) {
        Scanner letterGuesser = new Scanner(System.in);
        System.out.print("\n\t\n\tPLEASE GUESS A LETTER! (USE LOWER CASE)");
        System.out.printf("\n\tTHE LETTERS YOU GUESSED SO FAR: %s\n\t", guessedLetter.toString());
        String letterGuessedUnformatted = letterGuesser.nextLine();
        guessedLetter.add(letterGuessedUnformatted.toUpperCase());
        return letterGuessedUnformatted.charAt(0);
    }

    /**
     * Checks if the new letter is in the word
     *
     * @param letterPool chars already guessed and/or stars
     * @param wordUsed   the word to be guessed
     * @param newLetter  the letter guessed
     */
    public static int checkLetterpool(char[] letterPool, String wordUsed, char newLetter, int countDown, String[] hangman) {

        boolean letterIsRight = false;

        for (int i = 0; i < wordUsed.length(); i++) {
            wordUsed = wordUsed.toLowerCase();
            if (wordUsed.charAt(i) == newLetter) {
                letterPool[i] = newLetter;
                letterIsRight = true;
            }
        }

        if (!letterIsRight) {
            countDown++;
            printTheMan(hangman, countDown);
        }
        return countDown;
    }

    public static boolean winningCondition(char[] letterPool, String wordUsed) {

        boolean gameState = true;
        int starCounter = 0;

        for (char c : letterPool) {
            if (c != '*') {
                starCounter ++;
            }
        }

        if (starCounter == wordUsed.length()){
            System.out.printf("\n\tYOU HAVE WON THE GAME!\n\tTHE WINNING WORD WAS %s", wordUsed.toUpperCase());
            gameState = isGameState();
        }

        return gameState;
    }

    private static boolean isGameState() {
        boolean gameState;
        Scanner letterGuesser = new Scanner(System.in);
        System.out.print("\n\tDO YOU WANT TO CONTINUE? [y/n]\n\t");
        String playerChoice = letterGuesser.nextLine();
        if (playerChoice.equals("y")){
            gameState = true;
            gameLoop(true);
        }
        else {
            gameState = false;
        }
        return gameState;
    }

    public static void printTheMan(String[] hangman, int countDown) {
        for (int i = 0; i < countDown; i++) {
            System.out.print(hangman[i]);
        }
    }

    public static boolean losingCondition(int countdown, int maxGuesses) {
        boolean gameState = false;
        if (countdown < maxGuesses) {
            gameState = true;
        } else {
            System.out.print("\n\tYOU HAVE LOST THE GAME!");
            gameState = isGameState();
        }
        return gameState;
    }
}