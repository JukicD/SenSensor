/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sensensor;

import com.forall.modell.DataProxy;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author jd
 */
public class DataProxyEncoder implements Encoder.Binary<DataProxy>{

    @Override
    public ByteBuffer encode(DataProxy object) throws EncodeException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        try {
            ObjectOutputStream o = new ObjectOutputStream(b);
            o.writeObject(object);

        } catch (IOException ex) {
            Logger.getLogger(DataProxyEncoder.class.getName()).log(Level.SEVERE, null, ex);
        }


         return ByteBuffer.wrap(b.toByteArray());
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

}
