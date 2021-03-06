package org.sparkle.janette.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import org.sparkle.jbind.JBinD;
import org.sparkle.jbind.Reader;
import org.sparkle.jbind.Writer;

/**
 *
 * @author yew_mentzaki
 */
public final class ServerConnection {

    private class clientConnection {

        Socket client;
        ServerHandler serverhandler;

        public clientConnection(Socket client) {
            try {
                this.client = client;
                serverhandler = (ServerHandler) handler.newInstance();
            } catch (InstantiationException ex) {
                Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void send() {
            try {
                byte[] bytes = Writer.write(serverhandler.out());
                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                dos.writeInt(bytes.length);
                dos.write(bytes, 0, bytes.length);
            } catch (IOException ex) {
                Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        Thread get = new Thread() {

            @Override
            public void run() {
                InputStream inputStream = null;
                try {
                    inputStream = client.getInputStream();
                    DataInputStream dis = new DataInputStream(inputStream);
                    while (true) {
                        int length = dis.readInt();
                        if (length <= 0) {
                            System.out.println("Got empty message...");
                            continue;
                        }
                        byte[] message = new byte[length];
                        dis.read(message);
                        JBinD bind = Reader.read(message);
                        serverhandler.in(bind);
                    }
                } catch (IOException ex) {

                } finally {

                }
            }

        };

        public void close() {
            get.stop();
        }

    }

    private ArrayList<clientConnection> connections = new ArrayList<clientConnection>();

    int port;
    Class handler;
    int sendingDelay;

    public ServerConnection(int port, Class handler, int sendingDelay) {
        this.port = port;
        this.handler = handler;
        this.sendingDelay = sendingDelay;
    }

    @Override
    protected void finalize() throws Throwable {
        close();
    }

    public void close() {
        try {
            timer.stop();
            newClients.stop();
        } catch (Exception e) {
        }
        for (clientConnection c : connections) {
            c.close();
        }
    }
    Timer timer = new Timer(sendingDelay, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            for (clientConnection c : connections) {
                c.send();
            }
        }
    });
    Thread newClients = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    Socket client = socket.accept();
                    connections.add(new clientConnection(client));
                } catch (IOException ex) {
                    Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };
    ServerSocket socket;

    public void open() throws IOException {
        socket = new ServerSocket(port);
        newClients.start();
        timer.start();
    }
}
