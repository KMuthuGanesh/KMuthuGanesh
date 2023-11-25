import java.util.Arrays;

public class PlayfairCipher {
    private char[][] keySquare;

    public PlayfairCipher(String key) {
        initializeKeySquare(key);
    }

    private void initializeKeySquare(String key) {
        // Remove duplicate characters from the key
        String cleanedKey = key.replaceAll("[^a-zA-Z]", "").toUpperCase();
        cleanedKey = cleanedKey.replace("J", "I");

        // Initialize the key square
        keySquare = new char[5][5];
        String keyConcat = cleanedKey + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                keySquare[i][j] = keyConcat.charAt(index);
                index++;
            }
        }
    }

    private String encryptPair(char a, char b) {
        StringBuilder result = new StringBuilder();

        // Find positions of characters in the key square
        int[] posA = findPosition(a);
        int[] posB = findPosition(b);

        // Same row, shift columns to the right
        if (posA[0] == posB[0]) {
            result.append(keySquare[posA[0]][(posA[1] + 1) % 5]);
            result.append(keySquare[posB[0]][(posB[1] + 1) % 5]);
        }
        // Same column, shift rows down
        else if (posA[1] == posB[1]) {
            result.append(keySquare[(posA[0] + 1) % 5][posA[1]]);
            result.append(keySquare[(posB[0] + 1) % 5][posB[1]]);
        }
        // Form a rectangle, swap columns
        else {
            result.append(keySquare[posA[0]][posB[1]]);
            result.append(keySquare[posB[0]][posA[1]]);
        }

        return result.toString();
    }

    private int[] findPosition(char c) {
        int[] result = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keySquare[i][j] == c) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return null;
    }

    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();

        // Prepare the plaintext by removing non-alphabetic characters and replacing J with I
        plaintext = plaintext.replaceAll("[^a-zA-Z]", "").toUpperCase();
        plaintext = plaintext.replace("J", "I");

        // Split the plaintext into pairs of characters
        for (int i = 0; i < plaintext.length(); i += 2) {
            char a = plaintext.charAt(i);
            char b = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';

            ciphertext.append(encryptPair(a, b));
        }

        return ciphertext.toString();
    }

    public static void main(String[] args) {
        // Example usage with the keyword "Hello World"
        PlayfairCipher playfair = new PlayfairCipher("Hello World");
        String plaintext = "HELLO";
        String ciphertext = playfair.encrypt(plaintext);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + ciphertext);
    }
}
