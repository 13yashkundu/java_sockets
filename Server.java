import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket server;
    BufferedReader input1;
    BufferedReader input2;
    PrintWriter output1;
    PrintWriter output2;
    static Socket socket1;
    static Socket socket2;

    void connections() {
        try {
            server = new ServerSocket(5000);
            socket1 = server.accept();
            socket2 = server.accept();

            input1 = new BufferedReader(
                    new InputStreamReader(socket1.getInputStream()));

            input2 = new BufferedReader(
                    new InputStreamReader(socket2.getInputStream()));

            output1 = new PrintWriter(
                    socket1.getOutputStream(), true);

            output2 = new PrintWriter(
                    socket2.getOutputStream(), true);
        } catch (Exception ex) {
            System.out.println("Error in creating connections obj");
        }
    }

    void writeFile1(String msg) throws Exception {
        output1.println(msg);
    }

    void writeFile2(String msg) throws Exception {
        output2.println(msg);
    }

    String fileReader1() throws Exception {
        return input1.readLine();
    }

    String fileReader2() throws Exception {
        return input2.readLine();
    }

    public static void main(String[] args) {

        try {

            Server obj = new Server();

            obj.connections();

            String username1 = "person1@email.com";
            String pass1 = "123456";

            String username2 = "person2@email.com";
            String pass2 = "78910";

            try {

                System.out.println("Waiting for User1...");
                obj.writeFile1("Enter username: ");
                String person1 = obj.fileReader1();

                obj.writeFile1("Enter password: ");
                String password1 = obj.fileReader1();

                if (person1.equals(username1) && password1.equals(pass1)) {

                    System.out.println("User1 Connected");
                } else {
                    obj.writeFile1("User not found");
                    socket1.close();
                    return;
                }

            } catch (Exception ex) {
                System.out.println("Error in person1 validation!");
            }

            System.out.println("Waiting for User2...");

            try {

                obj.writeFile2("Enter username: ");
                String person2 = obj.fileReader2();

                obj.writeFile2("Enter password: ");
                String password2 = obj.fileReader2();

                if (person2.equals(username2) && password2.equals(pass2)) {
                    System.out.println("User2 Connected");
                } else {
                    obj.writeFile2("User not found");
                    socket2.close();
                    return;

                }

            } catch (Exception e) {
                System.out.println("Error in person2 validation!");
            }

            try {

                while (true) {

                    String msg1 = obj.fileReader1();

                    if (msg1 == null || msg1.equalsIgnoreCase("end"))
                        break;

                    obj.writeFile2(msg1);
                    System.out.println("person1: " + msg1);

                    // User2 -> User1
                    String msg2 = obj.fileReader2();

                    if (msg2 == null || msg2.equalsIgnoreCase("end"))
                        break;

                    obj.writeFile1(msg2);
                    System.out.println("person2: " + msg2);
                }
            } catch (Exception ex) {
                System.out.println("Error in messaging!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}