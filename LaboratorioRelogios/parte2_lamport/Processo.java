package parte2_lamport;

import java.io.*;
import java.net.*;

public class Processo extends Thread {

    private static final int[] PORTAS = {5000, 5001, 5002};
    private int id;
    private int lamport = 0;

    public Processo(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        new Thread(this::servidor).start();

        try {
            Thread.sleep(1000 + id * 500);
            eventoLocal();
            enviarMensagem((id + 1) % 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eventoLocal() {
        lamport++;
        System.out.println("[P" + id + "] EVENTO LOCAL | Lamport: " + lamport);
    }

    private void enviarMensagem(int destino) {
        try {
            Thread.sleep((long) (Math.random() * 1000));

            lamport++;
            Mensagem msg = new Mensagem(id, lamport);

            Socket socket = new Socket("localhost", PORTAS[destino]);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(msg);
            socket.close();

            System.out.println("[P" + id + "] ENVIO | Lamport: " + lamport);

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

                lamport = Math.max(lamport, msg.lamport) + 1;

                System.out.println("[P" + id + "] RECEBIMENTO de P" +
                        msg.senderId + " | Novo Lamport: " + lamport);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
