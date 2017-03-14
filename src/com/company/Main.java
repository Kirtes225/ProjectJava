package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) throws Exception {
        final ArrayList<String> msg = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        //String person = scanner.toString();
        String linetmp;
        boolean isHost = true;

        while ((linetmp = scanner.nextLine()) != null) {
            if (linetmp.contentEquals("h")) {
                isHost = true;
                break;
            } else {
                isHost = false;
                break;
            }
        }
        Socket s;
        if (isHost) {
            ServerSocket serverSocket = new ServerSocket(8888);
            s = serverSocket.accept(); //
        } else {
            s = new Socket("127.0.0.1", 8888);
        }

        //InputStream inputStream = s.getInputStream();

        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));

        //OutputStream outputStream = s.getOutputStream();

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream())); //wysyłamy bitowo, ale odczytujemy tekstowo

        Runnable out = new Runnable() {
            @Override
            public void run() {
                System.out.println("#DBG x4");

                try {

                    while (true) {
                        byte[] b = new byte[1];
                        b[0] = ' ';

                        String tmp = "";

                        while (s.getInputStream().read(b, 0, 1) > 0) {

                            tmp += String.valueOf((char) b[0]);

                            if (b[0] == '\n') /*\n - enter przy wiadomości*/ {
                                //msg.add(ANSI_CYAN+tmp.substring(0, tmp.length() - 1)+ANSI_RESET);
                                //f5(msg);
                                System.out.println(ANSI_CYAN + tmp.substring(0, tmp.length() - 1) + ANSI_RESET);
                                tmp = "";
                            }

                        }

                        Thread.sleep(505);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        s.close();
                        System.exit(-1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        new Thread(out).start();

        //Scanner scanner = new Scanner(System.in);


        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // synchronized (msg){
            // msg.add(ANSI_GREEN+line+ANSI_RESET);
            //f5(msg);
            // }
            bufferedWriter.write(line + '\n');
            bufferedWriter.flush();
        }

    }

    /*static void f5(final ArrayList<String> data) {
        //System.out.print("\b");
        //System.out.print("\033[H\033[2J");
        //System.out.flush();
//        try {
//            Runtime.getRuntime().exec("cls");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        for(String s: data){
            System.out.println(s);
        }
        //System.out.println(data);
    }
*/
}
