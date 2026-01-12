package parte3_vetorial;

import java.io.Serializable;

public class Mensagem implements Serializable {
    public int senderId;
    public int[] vetor;

    public Mensagem(int senderId, int[] vetor) {
        this.senderId = senderId;
        this.vetor = vetor;
    }
}
