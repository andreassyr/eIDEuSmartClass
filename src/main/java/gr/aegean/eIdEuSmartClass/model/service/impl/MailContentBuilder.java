/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.model.service.impl;

import java.text.MessageFormat;
import org.springframework.stereotype.Service;

/**
 *
 * @author nikos
 */
@Service
public class MailContentBuilder {

    private final static String content = "<!DOCTYPE html>\n"
            + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">\n"
            + "    <head>\n"
            + "        <title>Registration email</title>\n"
            + "    </head>\n"
            + "    <body>\n"
            + "        <!--<span th:text=\"${message}\"></span>-->\n"
            + "\n"
            + "        <span th:text=\"${message}\">%s</span> "
            + "\n"
            + "        <p>\n"
            + "            Welcome the UAegean Smar Class. Your account has been created. \n"
            + "            However it is inactive until it is authorized by an administrator. \n"
            + "        </p>\n"
            + "\n"

            + "        <p>\n"
            + "            You're receiving this transactional email message because you have been registered with UAegean \n"
            + "            Online Community Program through your eID provider (via <a href=\"https://ec.europa.eu/cefdigital/wiki/display/EIDCOOPNET/eIDAS+Cooperation+Network\">\n"
            + "           eIDAS </a>  infastructure). \n"
            + "        </p>\n"
           
            + "        <p>\n"
            + "            This email is sent from an automated account which is not monitored and so we’re unable to respond to replies to this email..\n"
            + "        </p>\n"
            + "\n"
            + "        <p>\n"
            + "            Thank you,\n"
            + "            The administration team \n"
            + "        </p>\n"
            + "\n"
            + "        <div>\n"
            + "          UAegean Smart Class by i4m Lab\n"
            + "        </div>\n"
            + "    </body>\n"
            + "</html>";

    
    
    public String build(String userName) {
        return String.format(content, userName);
    }

}