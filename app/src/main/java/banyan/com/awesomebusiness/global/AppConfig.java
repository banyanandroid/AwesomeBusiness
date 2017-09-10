package banyan.com.awesomebusiness.global;

public class AppConfig {
    public static String BASE_URL = "http://www.epictech.in/api_awesome";

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


    //TEMPORARY URL's

    //Investor Profile  // CHANGE URL'S LATER (THESE R TEST URL'S)
    public static String url_investor_iam = BASE_URL + "/index.php/Apicontroller/listallinvestoran";
    public static String url_investor_interested = BASE_URL + "/index.php/Apicontroller/listallinvestorinterest";
    public static String url_investor_profile_add = BASE_URL + "/index.php/Apicontroller/addinvestorprofile";

    //FILTER MENU
    public static String url_investor_buyer_type = BASE_URL + "/index.php/Apicontroller/listallinvestoran";

    //User Profiles
    public static String url_user_business_profiles = BASE_URL + "/index.php/Apicontroller/listuserbusinessprofile";
    public static String url_user_investor_profiles = BASE_URL + "/index.php/Apicontroller/listuserinvestorprofile";
    public static String url_user_franchise_profiles = BASE_URL + "/index.php/Apicontroller/listuserfranchiseprofile";
    //User Profiles Update
    public static String url_user_business_profile_update = BASE_URL + "/index.php/Apicontroller/listusersinglebusinessprofile";
    public static String url_user_investor_profile_update = BASE_URL + "/index.php/Apicontroller/listuserinvestorprofile";
    public static String url_user_franchise_profile_update = BASE_URL + "/index.php/Apicontroller/listuserfranchiseprofile";


}


