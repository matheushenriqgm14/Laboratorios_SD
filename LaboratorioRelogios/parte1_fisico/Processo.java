package parte1_fisico;

import java.io.*;
import java.net.*;

public class Processo extends Thread {

    private static final int[] PORTAS = {5000, 5001, 5002};
    private int id;

    public Processo(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        new Thread(this::servidor).start();

        try {
            Thread.sleep(1000 + id * 500);
            enviarMensagem((id + 1) % 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviarMensagem(int destino) {
        try {
            Thread.sleep((long) (Math.random() * 1000));

            long tempo = System.currentTimeMillis();
            Mensagem msg = new Mensagem(id, tempo);

            Socket socket = new Socket("localhost", PORTAS[destino]);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(msg);
            socket.close();

            System.out.println("[P" + id + "] ENVIO | Tempo físico: " + tempo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void servidor() {
        try (ServerSocket server = new ServerSocket(PORTAS[id])) {
            while (true) {
                Socket socket = server.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Mensagem msg = (Mensagem) in.readObject();
                socket.close();

                System.out.println("[P" + id + "] RECEBIMENTO de P" +
                        msg.senderId + " | Tempo físico recebido: " + msg.tempoFisico);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
