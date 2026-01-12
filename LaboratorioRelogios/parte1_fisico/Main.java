package parte1_fisico;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Processo(i).start();
        }
    }
}
