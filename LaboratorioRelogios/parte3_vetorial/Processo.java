package parte3_vetorial;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Processo extends Thread {

    private static final int N = 3;
    private static final int[] PORTAS = {5000, 5001, 5002};

    private int id;
    private int[] vetor = new int[N];

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
        vetor[id]++;
        System.out.println("[P" + id + "] EVENTO LOCAL | Vetor: " + Arrays.toString(vetor));
    }

    private void enviarMensagem(int destino) {
        try {
            Thread.sleep((long) (Math.random() * 1000));

            vetor[id]++;
            Mensagem msg = new Mensagem(id, vetor.clone());

            Socket socket = new Socket("localhost", PORTAS[destino]);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(msg);
            socket.close();

            System.out.println("[P" + id + "] ENVIO | Vetor: " + Arrays.toString(vetor));

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

                for (int i = 0; i < N; i++) {
                    vetor[i] = Math.max(vetor[i], msg.vetor[i]);
                }
                vetor[id]++;

                System.out.println("[P" + id + "] RECEBIMENTO de P" +
                        msg.senderId + " | Vetor: " + Arrays.toString(vetor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
