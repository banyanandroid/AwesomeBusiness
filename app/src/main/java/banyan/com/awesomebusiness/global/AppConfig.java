package banyan.com.awesomebusiness.global;

public class   AppConfig {
    public static String BASE_URL="http://epictech.in/apiawesome";

    public static String url_registration = BASE_URL + "/index.php/apicontroller/createUsers";
    public static String url_login = BASE_URL + "/index.php/apicontroller/loginusers";
    public static String url_social_login = BASE_URL + "/index.php/apicontroller/sociallogin";

    //Autocomplete
    public static String url_iam = BASE_URL + "/index.php/apicontroller/listallbusinessrole";
    public static String url_interested_in = BASE_URL + "/index.php/Apicontroller/getalltransaction";
    public static String url_business = BASE_URL + "/index.php/Apicontroller/getallbusiness";
    public static String url_business_location = BASE_URL + "/index.php/Apicontroller/alllocation";
    public static String url_add_business_profile = BASE_URL + "/index.php/Apicontroller/addbusinessprofile";


}
