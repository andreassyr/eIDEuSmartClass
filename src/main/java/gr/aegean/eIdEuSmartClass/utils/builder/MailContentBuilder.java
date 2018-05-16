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
            + "        <span th:text=\"${userName}\">%1$s,</span> "
            + "\n"
            + "        <p>\n"
            + "            Welcome to the UAegean-HP Smart Class. Your account has been created."
            +             " However it is inactive until it is authorized by an administrator. Once this authorization provided, you will be notified by email!"
            + "        </p>"
            + "        <p> "
            + "            Note: You're receiving this transactional email message because you have been registered with UAegean-HP Smart Class"
            + "           through your eID provider (via <a href=\"https://ec.europa.eu/cefdigital/wiki/display/EIDCOOPNET/eIDAS+Cooperation+Network\">\n"
            + "           eIDAS </a>  infastructure). "
            + "        </p>";
            

    private final static String teamCredentialsBody = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">%1$s,</span> "
            + "\n"
            + "        <p>\n"
            + "            You have been added to the Team : %2$s!!\n"
            + "        </p>\n"
            + "        <p>\n"
            + "            Your userName is : %3$s\n"
            + "        </p>\n"
            + "        <p>\n"
            + "            Your password is : %4$s\n"
            + "        </p>\n"
            + "        <p>\n"
            + "            Please change the password as soon as possible!\n"
            + "        </p>\n"
            + "\n";

    private final static String teamCredentialsBodyExisting = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">%1$s,</span> "
            + "\n"
            + "        <p>\n"
            + "            You have been added to the Team : %2$s!!\n"
            + "        </p>\n"
            + "        <p>\n"
            + "            Your userName is : %3$s\n"
            + "        </p>\n"
            + "\n";

    private final static String accountActivated = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">%1$s,</span> "
            + "\n"
            + "        <p>\n"
            + "            Your  UAegean SmartClass Account is now Active!\n"
            + "        </p>"
            + "<p>Please proceed to use UAeegan-HP Smart Class servives by visiting<br/>"
            + "<a href=\"http://eideusmartclass.aegean.gr/ \">http://eideusmartclass.aegean.gr/</a>"
            + "</p>"
            + "<p>UAegean Smart Class Available Services</p>"
            + "<p><b>Online</b></p>"
            +"<ul>"
            + "<li>Join one of the Smart Class Online Activities (Login to an Online Class)</li>"
            + "<li>Join instantly a Video-Conference Session (Select from a List and Login)</li>"
            + "</ul>"
            + "<p><b>Physical</b> (Location: Maria Tsakos Public Foundation, 51 Michail Livanou, 82100 Chios)</p>"
            +"<ul><li>Access to Smart Class facilities via IoT (Get en eID Passport)</li></ul>"
            +"<br/>";
            

    //return String.format(header + skypeAccessBody + footer, "Skype for Business link", name, link, principalName, password);
    private final static String skypeAccessBody = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">%1$s,</span> "
            + "\n"
            + "        <p>\n"
            + "            You can enter your conference from the followin link: \n"
            + "                     %2$s \n"
            + "            Your userName is : %3$s\n"
            + "        </p>\n"
            + "        <p>\n"
            + "            Your password is : %4$s\n"
            + "        </p>\n"
            + "        <p>\n"
            + "            Please change the password as soon as possible!\n"
            + "        </p>\n"
            + "        </p>\n";

    private final static String skypeAccessBodyExisting = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">%1$s,</span> "
            + "\n"
            + "        <p>\n"
            + "            You can enter your conference from the following link: \n"
            + "                     %2$s \n"
            + "        <p>"
            + "            Your userName is : %3$s\n"
            + "        </p>\n"
            + "        </p>\n";

    private final static String footer
            = 
            "        <p>"
            + "            This email is sent from an automated account which is not monitored and so we’re unable to respond to replies to this email.\n"
            + "        </p>\n"
            + "        <p>\n"
            + "            Thank you, The administration team <br/>"
            + "          UAegean HP-Smart Class by i4m Lab <br/>"
            + "          email:  <a href=\"mailto:eidapps@atlantis-group.gr\">eidapps@atlantis-group.gr</a>"
            + "        </p>\n"
            + "    </body>\n"
            + "</html>";

    public static String buildWelcome(String userName) {
        return String.format(header + registrationBody + footer, userName);
    }

    public static String buildTeamRegistration(String name, String teamName, String principalName, String password) {
        return String.format(header + teamCredentialsBody + footer, name, teamName, principalName, password);
    }

    public static String buildSkypeForBusinessContent(String name, String link, String principalName, String password) {
        return String.format(header + skypeAccessBody + footer, name, link, principalName, password);
    }

    public static String buildTeamRegistrationExisting(String name, String teamName, String principalName) {
        return String.format(header + teamCredentialsBodyExisting + footer, name, teamName, principalName);
    }

    public static String buildSkypeForBusinessContenExisting(String name, String link, String principalName) {
        return String.format(header + skypeAccessBodyExisting + footer, name, link, principalName);
    }

    public static String buildAccountActivated(String name) {
        return String.format(header + accountActivated + footer, name);
    }

}
