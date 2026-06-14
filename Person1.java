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
                            System.out.println(in.readLine());
                            socket.close();
                            return;
                        }
                    }
                }

            } else if (logtype.equalsIgnoreCase("signup")) {
                String enterMail = in.readLine();
                System.out.println(enterMail);
                String newmail = sc.nextLine();
                out.println(newmail);
                String enterpass = in.readLine();
                System.out.println(enterpass);
                String newpass = sc.nextLine();
                out.println(newpass);
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