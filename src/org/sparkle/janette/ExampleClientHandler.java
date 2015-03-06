package org.sparkle.janette;

import org.sparkle.janette.client.ClientHandler;
import org.sparkle.jbind.JBinD;

/**
 *
 * @author yew_mentzaki
 */
public class ExampleClientHandler extends ClientHandler{

    @Override
    public void in(JBinD data) {
        System.out.println(data.getPart("message").getDataAsString());
    }

}
