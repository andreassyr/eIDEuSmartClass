/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.generators;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nikos
 */
public class QRGenerator {

    private static Logger log = LoggerFactory.getLogger(QRGenerator.class);

    public static String generateQR(String roomId, String userEid, String pinCode) throws IOException, IOException, IOException {
        ByteArrayOutputStream bout
                = QRCode.from(pinCode)
                        .withSize(250, 250)
                        .to(ImageType.PNG)
                        .stream();
        try {
            StringBuilder path = new StringBuilder();
            path.append(System.getProperty("java.io.tmpdir")).append("/").append(roomId).append("-").append(userEid.hashCode()).append(".png");
            OutputStream out = new FileOutputStream(path.toString());
            bout.writeTo(out);
            out.flush();
            out.close();

            return path.toString();
            
        } catch (FileNotFoundException e) {
            log.info("ERROR " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("ERROR " + e.getMessage());
        }
        return null;
    }

}
