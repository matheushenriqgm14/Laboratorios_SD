package parte2_lamport;

import java.io.Serializable;

public class Mensagem implements Serializable {
    public int senderId;
    public int lamport;

    public Mensagem(int senderId, int lamport) {
        this.senderId = senderId;
        this.lamport = lamport;
    }
}
