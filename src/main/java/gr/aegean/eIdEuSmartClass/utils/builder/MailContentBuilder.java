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

    private final static String registrationBody = "    <body>"
            + "        <p th:text=\"${userName}\">Hi %1$s,<br/>"
            + "           Welcome to the UAegean-HP Smart Class Program. We have registered your request and created an account that will allow you to access our services. However this account is inactive until it is authorized by an administrator. Once this authorization provided, you will be notified by email!"
            + "       <br/>"
            + "           Note: You're receiving this transactional email message because you have been registered with UAegean Smart Class Programme through your eID provider (via <a href=\"https://ec.europa.eu/cefdigital/wiki/display/EIDCOOPNET/eIDAS+Cooperation+Network\">eIDAS</a> Network -- or, alternatively via an Academic Attribute Provider/AP or via your LinkedIn professional identity). This email is sent from an automated account which is not monitored, so we are not able to respond to replies to this email."
            + "        </p>";

    private final static String accountActivated = "    <body>\n"
            + "        <p th:text=\"${name}\">Hi %1$s, <br/> "
            + "            Your  UAegean SmartClass Account is now Active!"
            + " Please proceed to use UAegean-HP Smart Class services by visiting our site:<br/>"
            + "<a href=\"http://eideusmartclass.aegean.gr/ \">http://eideusmartclass.aegean.gr/</a>"
            + "</p>"
            + "<p>UAegean Smart Class Available Services</p>"
            + "<p><b>Online</b></p>"
            + "<ul>"
            + "<li>Join one of the Smart Class Online Activities (Login to an Online Class)</li>"
            + "<li>Join instantly a Video-Conference Session (Select from a List and Login)</li>"
            + "</ul>"
            + "<p><b>Physical</b> (Location: Maria Tsakos Public Foundation, 51 Michail Livanou, 82100 Chios)</p>"
            + "<ul><li> Access to Smart Class facilities via IoT (Get an eID Passport)</li></ul>"
            + "<br/>";

    private final static String rejectAccount = "<p>"
            + "Your request to join UAegean-HP Smart Class has been cosnidered but not accepted (your request does not match the profile of the Smart Class users)."
            + "<br/>"
            + "If you want to receive further details on our decision, please contact us by email:"
            + " <a href=\"mailto:eidapps@atlantis-group.gr\">eidapps@atlantis-group.gr</a>"
            + "<br/>"
            + "</p>";

    private final static String teamCredentialsBody = "    <body>"
            + "        <p th:text=\"${name}\">Hi %1$s, <br/>"
            + "         Welcome to Smart Class Activity entitled: %2$s "
            + "<br/>"
            + "        The class you have already joined is active on Microsfot Teams (MS Teams). "
            + "</p>"
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
            + "<br/>"
            + "Upon <b>successful</b> authentication, <b>you instantly access the Online Class you want to join!</b>"
            + "        </p>"
            + "<br/>";

    private final static String teamCredentialsBodyExisting = "    <body>\n"
            + "        <p th:text=\"${name}\">Hi %1$s, <br/>"
            + "         Welcome to Smart Class Activity entitled: %2$s "
            + "<br/>"
            + "        The class you have already joined is active on Microsfot Teams (MS Teams). "
            + "</p>"
            + "        <p>To Join MS Teams, please visit</p> "
            + "         <p><a href= \"http://teams.microsoft.com\">http://teams.microsoft.com</a> "
            + "        <p>"
            + "            UserName: %3$s"
            + "        </p>"
            + "        <p>"
            + "           Password: the password you have previously selected"
            + "        </p>"
            + "        <p>"
            + "            (Activation within 60 minutes - in your first visit, please change the password)"
            + "<br/>"
            + "Upon <b>successful</b> authentication, <b>you instantly access the Online Class you want to join!</b>"
            + "        </p>"
            + "<br/>";

    private final static String skypeAccessBody = "    <body>"
            + "        <p th:text=\"${name}\">Hi %1$s, "
            + "        Welcome to Video-Conference Session %2$s <br/>"
            + "<br/>"
            + "The Video-Conference you have joined is active on Skype for Business application. <br/>"
            + "You can join your Video-Conference Session by following the link: <br/>"
            + "%3$s"
            + "<br/>"
            + "It is simple, you will join the Session in <b>4 steps!</b>"
            + " <br/>"
            + "<ol>"
            + "<li>(after clicking on the above link) You are redirected to a window asking \"How you would you like to join your meeting?\"</li>"
            + "<li>Select the first option: <b>Install and join with Skype Meetings App (web)</b></li>"
            + "<li>Your browser will automatically download the Skype meeting plugin</li>"
            + "<li>(after successful installation) We request you to kindly enter your full name (for example, Ann Jaspers) and press join</li>"
            + "</ol>"
            + "<b>You are in!</b>"
            + "<br/>"
            + "<br/>"
            + "Note: In the case you will use the <b>Skype for Business desktop client</b> (see above, step 2), you would need an account. We have created one for you!"
            + "</p>"
            + "        <p>   UserName: %4$s\n"
            + "        </p>"
            + "        <p>"
            + "            Password: %5$s\n"
            + "        </p>\n"
            + "        <p>\n"
            + "            (Activation within 60 minutes - in your first visit, please change the password)"
            + "        </p> "
            + "Next:<br/>"
            + "<ol>"
            + "<li>Go to: <a href=\"https://products.office.com/en-us/skype-for-business/download-app?tab=tabs-3\">https://products.office.com/en-us/skype-for-business/download-app?tab=tabs-3</a></li>"
            + "<li> Select \"Computer\" (Windows or Mac) - in the case of Windows, please select the version of the desktop client entitled \"Download free basic client\"</li>"
            + "<li>Open the Skype for Business application you have downloaded and sign in with the above credentials (username and password)</li>"
            + "<li>Open a browser window to access the Video-Conference Session link you have received</li>"
            + "<li>Open Skype for Business (pop-up window)</li>"
            + "<li>Join Meeting Audio / Use Skype for Business (full audio and video experience) | pre-selected</li>"
            + "</ol>"
            + "<p>"
            + "<b>You are in!</b>"
            + "More information: <br/>"
            + "<a href=\"https://support.office.com/en-us/article/download-and-install-skype-for-business-on-windows-2da94a13-6d16-4d67-adf3-439f2b946994\">https://support.office.com/en-us/article/download-and-install-skype-for-business-on-windows-2da94a13-6d16-4d67-adf3-439f2b946994 </a>"
            + "</p>";

    private final static String skypeAccessBodyExisting = "    <body>\n"
            + "        <p th:text=\"${name}\">Hi %1$s, "
            + "        Welcome to Video-Conference Session %2$s <br/>"
            + "<br/>"
            + "The Video-Conference you have joined is active on Skype for Business application. <br/>"
            + "You can join your Video-Conference Session by following the link: <br/>"
            + "%3$s"
            + "<br/>"
            + "It is simple, you will join the Session in <b>4 steps!</b>"
            + " <br/>"
            + "<ol>"
            + "<li>(after clicking on the above link) You are redirected to a window asking \"How you would you like to join your meeting?\"</li>"
            + "<li>Select the first option: <b>Install and join with Skype Meetings App (web)</b></li>"
            + "<li>Your browser will automatically download the Skype meeting plugin</li>"
            + "<li>(after successful installation) We request you to kindly enter your full name (for example, Ann Jaspers) and press join</li>"
            + "</ol>"
            + "<b>You are in!</b>"
            + "<br/>"
            + "<br/>"
            + "Note: In the case you will use the <b>Skype for Business desktop client</b> (see above, step 2), you would need an account. We have created one for you!"
            + "</p>"
            + "        <p>   UserName: %4$s\n"
            + "        </p>"
            + "        <p>"
            + "            Password: the password you have previously selected"
            + "        </p>\n"
            + "        <p>\n"
            + "            (Activation within 60 minutes - in your first visit, please change the password)"
            + "        </p> "
            + "Next:<br/>"
            + "<ol>"
            + "<li>Go to: <a href=\"https://products.office.com/en-us/skype-for-business/download-app?tab=tabs-3\">https://products.office.com/en-us/skype-for-business/download-app?tab=tabs-3</a></li>"
            + "<li> Select \"Computer\" (Windows or Mac) - in the case of Windows, please select the version of the desktop client entitled \"Download free basic client\"</li>"
            + "<li>Open the Skype for Business application you have downloaded and sign in with the above credentials (username and password)</li>"
            + "<li>Open a browser window to access the Video-Conference Session link you have received</li>"
            + "<li>Open Skype for Business (pop-up window)</li>"
            + "<li>Join Meeting Audio / Use Skype for Business (full audio and video experience) | pre-selected</li>"
            + "</ol>"
            + "<p>"
            + "<b>You are in!</b>"
            + "More information: <br/>"
            + "<a href=\"https://support.office.com/en-us/article/download-and-install-skype-for-business-on-windows-2da94a13-6d16-4d67-adf3-439f2b946994\">https://support.office.com/en-us/article/download-and-install-skype-for-business-on-windows-2da94a13-6d16-4d67-adf3-439f2b946994 </a>"
            + "</p>";

    private final static String physicalNewAccount = "<body>"
            + "        <p th:text=\"${name}\">Hi %1$s, <br/>"
            + "Welcome to Smart Class Physical Facilities (accessed via IoT) <br/>"
            + "UAegean-HP Smart Class Address: Michail Livanou, 51 | 82100 Chios <br/>"
            + "Smart Class Geolocation: <a href=\"https://www.google.com/maps/place/Maria+Tsakos+Foundation/@38.3658432,26.1402465,17z/data=!3m1!4b1!4m5!3m4!1s0x14bb65d15028d83d:0xd1f4d841cd82f758!8m2!3d38.3658432!4d26.1424405\">Google Maps</a> <br/>"
            + "</p>"
            + "<p>"
            + "To enter the class, please use either:"
            + "<ul>"
            + "<li>The <b>QR code</b> you have received (please scan your QR code on the door)</li>"
            + "<li> The <b>PIN code</b> you have received (please use the KeyPad Reader on the door)</li>"
            + "</ul>"
            + "<b>Both are valid for 4 hours!</b>"
            + "</p>"
            + "<br/>"
            + "<p>"
            + "By entering the class, you can use the same codes to have access to the classroom network and hardware equipment:"
            + "<ul>"
            + "<li>Get instant Wi-Fi connectivity</li>"
            + "<li>Connect to Local Area Network</li>"
            + "<li>Use available PCs and presentation hardware (TV screens etc.)</li>"
            + "</ul>"
            + "In the case you need to access more services, we have created an account for you on:<br/>"
            + "<a href=\"https://www.office.com\">https://www.office.com</a> | Sign in <br/>"
            + "Username: %2$s <br/>"
            + "Password: %3$s <br/>"
            + "(Activation within 60 minutes - in your first visit, please change the password) <br/>"
            + "This account provide you with access to Office 365 apps."
            + "</p>";

    private final static String physicalExistingAccount = "<body>"
            + "        <p th:text=\"${name}\">Hi %1$s, <br/>"
            + "Welcome to Smart Class Physical Facilities (accessed via IoT) <br/>"
            + "UAegean-HP Smart Class Address: Michail Livanou, 51 | 82100 Chios <br/>"
            + "Smart Class Geolocation: <a href=\"https://www.google.com/maps/place/Maria+Tsakos+Foundation/@38.3658432,26.1402465,17z/data=!3m1!4b1!4m5!3m4!1s0x14bb65d15028d83d:0xd1f4d841cd82f758!8m2!3d38.3658432!4d26.1424405\">Google Maps</a> <br/>"
            + "</p>"
            + "<p>"
            + "To enter the class, please use either:"
            + "<ul>"
            + "<li>The <b>QR code</b> you have received (please scan your QR code on the door)</li>"
            + "<li> The <b>PIN code</b> you have received (please use the KeyPad Reader on the door)</li>"
            + "</ul>"
            + "<b>Both are valid for 4 hours!</b>"
            + "</p>"
            + "<br/>"
            + "<p>"
            + "By entering the class, you can use the same codes to have access to the classroom network and hardware equipment:"
            + "<ul>"
            + "<li>Get instant Wi-Fi connectivity</li>"
            + "<li>Connect to Local Area Network</li>"
            + "<li>Use available PCs and presentation hardware (TV screens etc.)</li>"
            + "</ul>"
            + "In the case you need to access more services, we can use your existing account:<br/>"
            + "<a href=\"https://www.office.com\">https://www.office.com</a> | Sign in <br/>"
            + "Username: %2$s <br/>"
            + "This account provide you with access to Office 365 apps."
            + "</p>";

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

    public static String buildAccountRejected(String name) {
        return String.format(header + rejectAccount + footer, name);
    }

    public static String buildNewAccountInfoAdmin(String name) {
        return "New account created in smart class for user " + name;
    }

    public static String buildPhysicalAccount(String name, String principalName, String password) {
        return String.format(header + physicalNewAccount + footer, name, principalName, password);
    }

    public static String buildPhysicalAccountExisting(String name, String principalName) {
        return String.format(header + physicalExistingAccount + footer, name, principalName);
    }

}
