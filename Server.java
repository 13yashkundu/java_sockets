import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

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

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("person1@email.com", "123456");
            map.put("person2@email.com", "78910");

            try {

                System.out.println("Waiting for User1...");

                obj.writeFile1("Sign in or Sign up");
                String login = obj.fileReader1();

                if (login.equalsIgnoreCase("signin")) {

                    int attempt = 3;
                    Boolean status = false;
                    while (attempt != 0 && status == false) {
                        obj.writeFile1("Enter username: ");
                        String person1 = obj.fileReader1();

                        obj.writeFile1("Enter password: ");
                        String password1 = obj.fileReader1();

                        if (map.containsKey(person1) && map.get(person1).equals(password1)) {
                            System.out.println("User1 Connected");
                            obj.writeFile1("Login succesfull");
                            status = true;
                        } else {
                            obj.writeFile1("User not found");
                            attempt--;
                            obj.writeFile1(attempt + "attempts left");
                            if (attempt == 0) {
                                socket1.close();
                                return;
                            }
                        }
                    }
                } else if (login.equalsIgnoreCase("signup")) {
                    obj.writeFile1("Enter Email: ");
                    String newUser = obj.fileReader1();

                    while (map.containsKey(newUser)) {
                        obj.writeFile1("Email already registered. Try another Email");
                        obj.writeFile1("Enter Email: ");
                        newUser = obj.fileReader1();
                    }
                    
                    obj.writeFile1("Enter password: ");
                    String newPass2 = obj.fileReader1();
                    map.put(newUser, newPass2);    

                } else {
                    obj.writeFile1("Invalid login type");
                    
                }

            } catch (Exception ex) {
                System.out.println("Error in person1 validation!");
            }

            System.out.println("Waiting for User2...");

            try {

                obj.writeFile2("Sign in or Sign up");
                String login = obj.fileReader2();

                if (login.equalsIgnoreCase("signin")) {

                    int attempt = 3;
                    Boolean status = false;
                    while (attempt != 0 && status == false) {
                        obj.writeFile2("Enter username: ");
                        String person2 = obj.fileReader2();

                        obj.writeFile2("Enter password: ");
                        String password2 = obj.fileReader2();

                        if (map.containsKey(person2) && map.get(person2).equals(password2)) {
                            System.out.println("User2 Connected");
                            status = true;
                            obj.writeFile2("Login succesfull");
                            status = true;
                        } else {
                            obj.writeFile2("User not found");
                            attempt--;
                            obj.writeFile2(attempt + " attemps left");
                            if (attempt == 0) {
                                socket2.close();
                                return;
                            }
                        }

                    }
                } else if (login.equalsIgnoreCase("signup")) {
                    obj.writeFile2("Enter Email: ");
                    String newUser2 = obj.fileReader2();

                    while (map.containsKey(newUser2)) {
                        obj.writeFile2("Email already registered. Try another Email");
                        obj.writeFile2("Enter Email: ");
                        newUser2 = obj.fileReader2();
                    }
                    
                    obj.writeFile2("Enter password: ");
                    String newPass2 = obj.fileReader2();
                    map.put(newUser2, newPass2);

                } else {
                    obj.writeFile2("Invalid login type");
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