package org.sparkle.janette.client;

/**
 *
 * @author yew_mentzaki
 */
public final class ClientConnection {

    String server;
    int port;
    Class handler;
    int sendingDelay;

    public ClientConnection(String server, int port, Class handler, int sendingDelay) {
        this.server = server;
        this.port = port;
        this.handler = handler;
        this.sendingDelay = sendingDelay;
    }

    public void connect() throws InstantiationException, IllegalAccessException {
        ClientHandler handler = (ClientHandler)this.handler.newInstance();
        
    }
}
