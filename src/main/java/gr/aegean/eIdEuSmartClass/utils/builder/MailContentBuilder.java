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
            + "        <span th:text=\"${userName}\">Hi %1$s,</span> "
            + "\n"
            + "        <p>\n"
            + "            Welcome to the UAegean-HP Smart Class. Your account has been created."
            + " However it is inactive until it is authorized by an administrator. Once this authorization provided, you will be notified by email!"
            + "        </p>"
            + "        <p> "
            + "            Note: You're receiving this transactional email message because you have been registered with UAegean-HP Smart Class"
            + "           through your eID provider (via <a href=\"https://ec.europa.eu/cefdigital/wiki/display/EIDCOOPNET/eIDAS+Cooperation+Network\">\n"
            + "           eIDAS </a>  infastructure). "
            + "            This email is sent from an automated account which is not monitored and so we’re unable to respond to replies to this email.\n"
            + "        </p>";

    private final static String teamCredentialsBody = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">Hi %1$s,</span> "
            + "        <p>Welcome to %2$s</p>   " //class name
            + "        <p> The class you have already joined is active on Microsfot Teams (MS Teams). </p> <br/> "
            + "        <p>To Join MS Teams, please visit</p> "
            + "         <p><a href= \"http://teams.microsoft.com\">http://teams.microsoft.com</a> "
            + "        <p>"
            + "            UserName: %3$s"
            + "        </p>"
            + "        <p>"
            + "            Password: %4$s"
            + "        </p>"
            + "        <p>"
            + "            (Activation within 60 minutes - in your first visit, please change the password)"
            + "        </p>"
            + "<br/>";

    private final static String teamCredentialsBodyExisting = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">Hi %1$s,</span> "
            + "        <p>Welcome to %2$s</p>   " //class name
            + "        <p> The class you have already joined is active on Microsfot Teams (MS Teams). </p> <br/> "
            + "        <p>To Join MS Teams, please visit</p> "
            + "         <p><a href= \"http://teams.microsoft.com\">http://teams.microsoft.com</a> "
            + "        <p>"
            + "            UserName: %3$s"
            + "        </p>"
            + "        <p>"
            + "            Password: the password you have previously selected"
            + "        </p>"
            + "        <p>"
            + "            (Activation within 60 minutes - in your first visit, please change the password)"
            + "        </p>"
            + "<br/>";

    private final static String accountActivated = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">Hi %1$s,</span> "
            + "\n"
            + "        <p>\n"
            + "            Your  UAegean SmartClass Account is now Active!\n"
            + "        </p>"
            + "<p>Please proceed to use UAeegan-HP Smart Class servives by visiting<br/>"
            + "<a href=\"http://eideusmartclass.aegean.gr/ \">http://eideusmartclass.aegean.gr/</a>"
            + "</p>"
            + "<p>UAegean Smart Class Available Services</p>"
            + "<p><b>Online</b></p>"
            + "<ul>"
            + "<li>Join one of the Smart Class Online Activities (Login to an Online Class)</li>"
            + "<li>Join instantly a Video-Conference Session (Select from a List and Login)</li>"
            + "</ul>"
            + "<p><b>Physical</b> (Location: Maria Tsakos Public Foundation, 51 Michail Livanou, 82100 Chios)</p>"
            + "<ul><li>Access to Smart Class facilities via IoT (Get en eID Passport)</li></ul>"
            + "<br/>";

    private final static String skypeAccessBody = "    <body>\n"
            + "\n"
            + "        <span th:text=\"${name}\">Hi %1$s,</span> "
            + "        <p>Welcome to conference room %2$s </p> <br/>"
            + "        <p>"
            + "            You can join your conference from the following link:  %3$s"
            + "</p>"
            + "        <p> with Skype web meeting app or with Skype for Business desktop client </p> <br/>"
            + "         <p>In case you use Skype for Business desktop client you need</p>"
            + "        <p>   UserName is : %4$s\n"
            + "        </p>"
            + "        <p>"
            + "            Password is : %5$s\n"
            + "        </p>\n"
            + "        <p>\n"
            + "            (Activation within 60 minutes - in your first visit, please change the password)"
            + "        </p> <br/>";

    private final static String skypeAccessBodyExisting = "    <body>\n"
            + "        <span th:text=\"${name}\">Hi %1$s,</span> "
            + "        <p>Welcome to conference room %2$s </p> <br/>"
            + "        <p>"
            + "            You can join your conference from the following link:  %3$s"
            + "</p>"
            + "        <p> with Skype web meeting app or with Skype for Business desktop client </p> <br/>"
            + "         <p>In case you use Skype for Business desktop client you need</p>"
            + "        <p>   UserName is : %4$s\n"
            + "        </p>"
            + "        <p>"
            + "            Password: the password you have previously selected"
            + "        </p>\n"
            + "        <p>\n"
            + "            (Activation within 60 minutes - in your first visit, please change the password)"
            + "        </p> <br/>";

    private final static String footer
            = "        <p>\n"
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

    public static String buildSkypeForBusinessContent(String name, String confRoom, String link, String principalName, String password) {
        return String.format(header + skypeAccessBody + footer, name, confRoom, link, principalName, password);
    }

    public static String buildTeamRegistrationExisting(String name, String teamName, String principalName) {
        return String.format(header + teamCredentialsBodyExisting + footer, name, teamName, principalName);
    }

    public static String buildSkypeForBusinessContenExisting(String name, String confRoom, String link, String principalName) {
        return String.format(header + skypeAccessBodyExisting + footer, name, confRoom, link, principalName);
    }

    public static String buildAccountActivated(String name) {
        return String.format(header + accountActivated + footer, name);
    }

    
    public static String buildNewAccountInfoAdmin(String name){
        return "New account created in smart class for user " + name;
    }
    
}
