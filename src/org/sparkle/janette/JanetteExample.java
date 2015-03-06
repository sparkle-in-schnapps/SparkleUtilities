package org.sparkle.janette;

import org.sparkle.janette.server.*;
import org.sparkle.janette.client.*;
/**
 *
 * @author yew_mentzaki
 */
public class JanetteExample {
    public static void main(String[] args) {
        ServerConnection sc = new ServerConnection(20150, ExampleServerHandler.class, 1000);
        try {
            sc.open();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ClientConnection cc = new ClientConnection("localhost", 20150, ExampleClientHandler.class, 1000);
        try {
            cc.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
