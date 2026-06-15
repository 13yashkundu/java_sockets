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

            String logOrSignin = in.readLine();
            System.out.println(logOrSignin);
            String logtype = sc.nextLine();
            out.println(logtype);

            if (logtype.equalsIgnoreCase("signin")) {

                Boolean loginSuccess = false;

                while (!loginSuccess) {

                    String enterUSer = in.readLine();
                    System.out.println(enterUSer);
                    String username = sc.nextLine();
                    out.println(username);
                    String enterPass = in.readLine();
                    System.out.println(enterPass);
                    String pass = sc.nextLine();
                    out.println(pass);
                    String response = in.readLine();
                    System.out.println(response);

                    if (response.equalsIgnoreCase("login succesfull")) {
                        loginSuccess = true;
                    } else {
                        String attempt = in.readLine();
                        System.out.println(attempt);
                        if (attempt.startsWith("0")) {
                            socket.close();
                            return;
                        }
                    }
                }

            } else if (logtype.equalsIgnoreCase("signup")) {
                String msg = in.readLine();
                System.out.println(msg); // Enter Email:

                String email = sc.nextLine();
                out.println(email);

                while (true) {

                    msg = in.readLine();

                    if (msg.equals("Enter password: ")) {
                        System.out.println(msg);

                        String password = sc.nextLine();
                        out.println(password);

                        break;
                    }

                    System.out.println(msg); // Email already registered

                    msg = in.readLine(); // Enter Email:
                    System.out.println(msg);

                    email = sc.nextLine();
                    out.println(email);
                }

            } else {
                System.out.println(in.readLine());
            }


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