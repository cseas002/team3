package cseas002.team3.hw5;

public class MainClass {
    public static void main(String[] args) {
        Hangman.filename = args[0];
        new Frame(false, 800, 600) { protected void removeLabels() {}};
    }
}
