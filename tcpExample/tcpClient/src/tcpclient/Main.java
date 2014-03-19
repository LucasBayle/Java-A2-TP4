package tcpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 4444);
        //Socket socket = new Socket("127.0.0.1", 4444);
        //to get the ip address
        System.out.println((java.net.InetAddress.getLocalHost()).toString());

        //true: it will flush the output buffer
        PrintWriter outSocket = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
       // Thread.sleep(1000);

        
        String serverReponse = null;
        String serverEND = null;
        boolean isNumber;
        int number =0;
        
        Scanner sc = new Scanner(System.in);
        
        while (!"END".equals(serverEND)) {
        System.out.println("Rentre un nombre entre 0 et 5 compris");
        isNumber = false;
        
        // on vérifie que le client rentre bien un nombre
        while (isNumber == false){
        try {
            number = sc.nextInt();
            isNumber = true;
        }
        catch( Exception isNotNumber)
        {
            System.out.println("On a dit un nombre couillon !");
            sc.next();
        }
        }
        
        outSocket.println(number);  // on envois au serveur, le nombre que l'on a tapé
        serverReponse = inSocket.readLine(); // on lit la premiere ligne (si c'est bon ou pas)
        serverEND = inSocket.readLine();  // la seconde ligne qu'on envois coté serveur
        System.out.println(serverReponse);
        }

        System.out.println("End.");
    }
}

