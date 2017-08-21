package banyan.com.awesomebusiness.global;

public class AppConfig {
    public static String BASE_URL = "http://www.epictech.in/apiawesome";

    public static String url_registration = BASE_URL + "/index.php/apicontroller/createUsers";
    public static String url_login = BASE_URL + "/index.php/apicontroller/loginusers";
    public static String url_social_login = BASE_URL + "/index.php/apicontroller/sociallogin";
    public static String url_user_profile = BASE_URL + "/index.php/apicontroller/detailusers";
    public static String url_user_profile_update = BASE_URL + "/index.php/Apicontroller/updateprofile";
    public static String url_change_password = BASE_URL + "/index.php/apicontroller/userchangepassword";

    //Country & Currency
    public static String url_country = BASE_URL + "/index.php/Apicontroller/allcountry";

    //Autocomplete
    public static String url_iam = BASE_URL + "/index.php/apicontroller/listallbusinessrole";
    public static String url_interested_in = BASE_URL + "/index.php/Apicontroller/getalltransaction";
    public static String url_business = BASE_URL + "/index.php/Apicontroller/getallbusiness";
    public static String url_business_location = BASE_URL + "/index.php/Apicontroller/alllocation";
    public static String url_add_business_profile = BASE_URL + "/index.php/Apicontroller/addbusinessprofile";

}
