/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.aegean.eIdEuSmartClass.utils.builder;

/**
 *
 * @author nikos
 */
public class MailContentBuilder {

    private final static String header = "<!DOCTYPE html>\n"
            + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">\n"
            + "    <head>\n"
            + "        <title th:text=\"${title}\">%1$s</title>\n"
            + "    </head>\n";

    private final static String registrationBody = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${userName}\">%2$s</span> "
            + "\n"
            + "        <p>\n"
            + "            Welcome the UAegean Smar Class. Your account has been created. \n"
            + "            However it is inactive until it is authorized by an administrator. \n"
            + "        </p>\n"
            + "        <p>\n"
            + "           Once activated you can use MS Team and Skype for Business with\n"
            + "           UserName: %2$s \n"
            + "           PrincipalName: %3$s \n"
            + "           Password: %4$s \n"
            + "           Please remember to change your password as soon as possible \n"
            + "        </p>\n"
            + "\n";

    private final static String teamCredentialsBody = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">%2$s</span> "
            + "\n"
            + "        <p>\n"
            + "            Your user name to enter MS TEAMS is: \n"
            + "                     %3$s \n"
            + "            Your password is: "
            + "                     %4$s \n"
            + "        </p>\n"
            + "        <p>\n"
            + "           Please remeber to change these information as soon as possible"
            + "        </p>\n"
            + "\n";

    private final static String skypeAccessBody = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">%2$s</span> "
            + "\n"
            + "        <p>\n"
            + "            You can enter your conference from the followin link: \n"
            + "                     %3$s \n"
            + "        </p>\n";

    private final static String footer
            = "        <p>\n"
            + "            You're receiving this transactional email message because you have been registered with UAegean \n"
            + "           Smart Class Program through your eID provider (via <a href=\"https://ec.europa.eu/cefdigital/wiki/display/EIDCOOPNET/eIDAS+Cooperation+Network\">\n"
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

    public static String buildWelcome(String userName, String adPrincipal, String adPass) {
        return String.format(header + registrationBody + footer, "Registration Email", userName,adPrincipal,adPass);
    }

    public static String buildTeamRegistration(String userName, String password, String name) {
        return String.format(header + teamCredentialsBody + footer, "MS Teams Details", name, userName, password);
    }

    public static String buildSkypeForBusinessContent(String name, String link) {
        return String.format(header + skypeAccessBody + footer, "Skype for Business link", name, link);
    }

}
