package tto;

public class Main {
    public static void main(String[] args) {
        for (String print : new TextToObject().run(args)) {
            System.out.println(print);
        }
    }
}
