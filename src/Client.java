
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

   public static void main(String[] args) {
      
      final Socket clientSocket;
      final BufferedReader in;
      final PrintWriter out;
      final Scanner sc = new Scanner(System.in);//leer desde teclado
  
      try {
         /*
         * información del servidor (puerto y dirección IP o nombre del host)
         * 127.0.0.0.0.1 es la dirección local de la máquina
         */
         clientSocket = new Socket("127.0.0.1",5000);
   
         //flux pour envoyer
         out = new PrintWriter(clientSocket.getOutputStream());
         //flux pour recevoir
         in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
   
         Thread enviar = new Thread(new Runnable() {
             String msg;
              @Override
              public void run() {
                while(true){
                  msg = sc.nextLine();
                  out.println(msg);
                  out.flush();
                }
             }
         });
         enviar.start();
   
        Thread recibir = new Thread(new Runnable() {
            String msg;
            @Override
            public void run() {
               try {
                 msg = in.readLine();
                 while(msg!=null){
                    System.out.println("Servidor : "+msg);
                    msg = in.readLine();
                 }
                 System.out.println("Servidor desconectado");
                 out.close();
                 clientSocket.close();
               } catch (IOException e) {
                   
               }
            }
        });
        recibir.start();
   
      } catch (IOException e) {
          
      }
  }
}
