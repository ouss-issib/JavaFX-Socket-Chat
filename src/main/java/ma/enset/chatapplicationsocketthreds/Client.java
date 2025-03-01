package ma.enset.chatapplicationsocketthreds;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",9090);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
        Scanner sc = new Scanner(System.in);
        String msg ;
        do{
            System.out.print("Ouss : ");
            msg = sc.nextLine();
            pw.println(msg);
            msg = br.readLine();
            System.out.println("Lui : "+msg);
        }while (!msg.equals("bye"));
    }
}
