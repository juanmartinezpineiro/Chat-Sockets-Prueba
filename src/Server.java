import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
/*
 * www.codeurjava.com
 */
public class Server {
 
   public static void main(String[] test) {
  
     final ServerSocket Chat  ;
     final Socket clientSocket ;
     final BufferedReader in;
     final PrintWriter out;
     final Scanner sc=new Scanner(System.in);
  
     try {
       Chat = new ServerSocket(5000);
       clientSocket = Chat.accept();
       out = new PrintWriter(clientSocket.getOutputStream());
       in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
       Thread envoi= new Thread(new Runnable() {
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
       envoi.start();
   
       Thread recibir= new Thread(new Runnable() {
          String msg ;
          @Override
          public void run() {
             try {
                msg = in.readLine();
                //siempre y cuando el cliente esté conectado
                while(msg!=null){
                   System.out.println("Cliente : "+msg);
                   msg = in.readLine();
                }
                //salir del bucle si el cliente se ha desconectado
                System.out.println("Cliente desconectado");
                //cerrar la sesión de flujo del socket
                out.close();
                clientSocket.close();
                Chat.close();
             } catch (IOException e) {
                 
             }
         }
      });
      recibir.start();
      }catch (IOException e) {
      
      }
   }
}