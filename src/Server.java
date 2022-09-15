import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;

    DigitalSignature digitalSignature = new DigitalSignature();

    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));

            String line = "";
//            KeyPair keyPair = DigitalSignature.Generate_RSA_KeyPair();
//            System.out.println(
//                    "Verification: "
//                            + DigitalSignature.Verify_Digital_Signature(
//                            line.getBytes(),
//                            line.getBytes(), keyPair.getPublic()));

            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {
                    line = in.readUTF();
                    System.out.println(line);

                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}
