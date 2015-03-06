package org.sparkle.janette;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.sparkle.janette.server.ServerHandler;
import org.sparkle.jbind.JBinD;
import org.sparkle.jbind.JBinDException;
import org.sparkle.jbind.Part;

/**
 *
 * @author yew_mentzaki
 */
public class ExampleServerHandler extends ServerHandler{

    @Override
    public JBinD out() {
        JBinD bind = new JBinD();
        try {
            bind.addPart(new Part("message", "Hello!"));
        } catch (JBinDException ex) {
            Logger.getLogger(ExampleServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bind;
    }
    
}
