import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Person2 {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            String enterUSer = in.readLine();
            System.out.println(enterUSer);
            String username = sc.nextLine();
            out.println(username);
            String enterPass = in.readLine();
            System.out.println(enterPass);
            String pass = sc.nextLine();
            out.println(pass);

            
            while (true) {

                String msg = in.readLine();

                if (msg == null || msg.equalsIgnoreCase("end"))
                    break;

                System.out.println("Person1: " + msg);

                System.out.print("You: ");
                String reply = sc.nextLine();

                out.println(reply);

                if (reply.equalsIgnoreCase("end"))
                    break;
            }

            socket.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}