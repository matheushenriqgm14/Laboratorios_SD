package parte1_fisico;

import java.io.Serializable;

public class Mensagem implements Serializable {
    public int senderId;
    public long tempoFisico;

    public Mensagem(int senderId, long tempoFisico) {
        this.senderId = senderId;
        this.tempoFisico = tempoFisico;
    }
}
