import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Person1 {

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

                System.out.print("You: ");
                String msg = sc.nextLine();

                out.println(msg);

                if (msg.equalsIgnoreCase("end"))
                    break;

                String reply = in.readLine();
                System.out.println("Person2: " + reply);
            }

            socket.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}