
import java.io.*;
import java.net.*;


public class Main implements Runnable {

    public OutputStream output;
    public ServerSocket serverSocket;
    public Socket socket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(5000);

            try {

                while (true) {
                    socket = serverSocket.accept();

                    System.out.println("New client connected");

                    output = socket.getOutputStream();

                    Authenticator.setDefault(new Authenticator() {

                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("admin", "Vlada123".toCharArray());
                        }
                    });

                    st();
                    sv();

                    socket.close();
                    System.out.println("CLOSE");

                }

            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void st() throws IOException
    {
        new MjpegRunner(output, new URL("http://192.168.0.211//video/mjpg.cgi")).run();
    }

    public void sv() throws IOException
    {
        new MjpegRunner(output, new URL("http://192.168.0.180//video/mjpg.cgi")).run();
    }


    public static void main(String[] args) {
        Main r1 = new Main();
        Thread t1 = new Thread(r1);

        t1.start();
    }
}
