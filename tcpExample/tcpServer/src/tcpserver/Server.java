package tcpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int MAX = 6;
    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        new Server().begin(4444);
    }
    
    public void begin(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) {
            System.out.println("Waiting for clients to connect on port " + port + "...");
            new ProtocolThread(serverSocket.accept()).start();
            //Thread.start() calls Thread.run()
        }
    }

    class ProtocolThread extends Thread {

        Socket socket;
        PrintWriter out_socket;
        BufferedReader in_socket;

        public ProtocolThread(Socket socket) {
            System.out.println("Accepting connection from " + socket.getInetAddress() + "...");
            this.socket = socket;
            try {
                out_socket = new PrintWriter(socket.getOutputStream(), true);
                in_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            boolean numberFound = false;
            String clientReponseString = null; // le serveur recoit le nombre sous forme de string
            int clientReponse;
            
            try {
                System.out.println("Expecting Number from client...");
                //sleep(5000);
                int nbAleatoire = (int) (Math.random() * (MAX));
                System.out.println("nbAleatoire = " + nbAleatoire);
                
                while (numberFound == false) {  // tant que l'utilisateur n'a pas trouvé le bon nombre, on boucle
                clientReponseString = in_socket.readLine();
                clientReponse = Integer.parseInt(clientReponseString);  // on transforme le string reçu en int, pour le comparer

                if (clientReponse == nbAleatoire) {
                    out_socket.println("Bien joué, tu as trouvé le bon numéro");
                    out_socket.println("END");
                    numberFound = true;
                } else {
                   if (clientReponse < nbAleatoire) {
                        out_socket.println("dommage, c'est +");
                    }
                   else if (clientReponse > nbAleatoire) {
                    out_socket.println("dommage, c'est -");
                }
                   out_socket.println("NOTEND");
                }
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    System.out.println("Closing connection.");
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
