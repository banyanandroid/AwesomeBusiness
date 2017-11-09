package banyan.com.awesomebusiness.global;

public class AppConfig {

    //public static String BASE_URL = "http://www.epictech.in/api_awesome";

    public static String BASE_URL = "http://awesomebusinesses.com/api";

    public static String url_ip_registration = BASE_URL + "/index.php?Apicontroller/";

    public static String url_registration = BASE_URL + "/index.php?apicontroller/createUsers";
    public static String url_login = BASE_URL + "/index.php?Apicontroller/loginusers";
    public static String url_social_login = BASE_URL + "/index.php?Apicontroller/sociallogin";
    public static String url_user_profile = BASE_URL + "/index.php?Apicontroller/detailusersedit";
    public static String url_user_profile_update = BASE_URL + "/index.php?Apicontroller/updateprofile";
    public static String url_change_password = BASE_URL + "/index.php?apicontroller/userchangepassword";
    public static String url_recent_activities = BASE_URL + "/index.php?apicontroller/usersactivity";
    public static String url_recent_contacted_activities = BASE_URL + "/index.php?Apicontroller/listusercontact";
    public static String url_popup_contact_business_again = BASE_URL + "/index.php?apicontroller/contactbusinessagain";

    /*********  Bookmark activities *********/
    public static String url_add_bookmark = BASE_URL + "/index.php?Apicontroller/add_bookmarks";
    public static String url_list_bookmarks = BASE_URL + "/index.php?Apicontroller/list_bookmarks";

    // Notification Deatails..
    public static String url_notifications = BASE_URL + "/index.php?apicontroller/getusernotification";

    //Country & Currency
    public static String url_country = BASE_URL + "/index.php?Apicontroller/allcountry";

    //Filter Menu
    public static String url_investor_buyer_type = BASE_URL + "/index.php?Apicontroller/listallinvestoran";
    public static String url_filter_seekbar_values = BASE_URL + "/index.php?Apicontroller/filters";

    //Autocomplete
    public static String url_iam = BASE_URL + "/index.php?apicontroller/listallbusinessrole";
    public static String url_interested_in = BASE_URL + "/index.php?Apicontroller/getalltransaction";
    public static String url_business = BASE_URL + "/index.php?Apicontroller/getallbusiness";
    public static String url_business_location = BASE_URL + "/index.php?Apicontroller/alllocation";

    //Investor Profile
    public static String url_investor_iam = BASE_URL + "/index.php?Apicontroller/listallinvestoran";
    public static String url_investor_interested = BASE_URL + "/index.php?Apicontroller/listallinvestorinterest";

    // Add Profiles
    public static String url_add_business_profile = BASE_URL + "/index.php?Apicontroller/addbusinessprofile";
    public static String url_add_franchise_profile = BASE_URL + "/index.php?Apicontroller/addfranchiseprofile";
    public static String url_investor_profile_add = BASE_URL + "/index.php?Apicontroller/addinvestorprofile";

    // Update Profiles
    public static String url_update_business_profile = BASE_URL + "/index.php?Apicontroller/updatebusinessprofile";
    public static String url_update_investor_profile = BASE_URL + "/index.php?Apicontroller/updateinvestorprofile";
    public static String url_update_franchise_profile = BASE_URL + "/index.php?Apicontroller/updatefranchiseprofile";

    //User Profiles List
    public static String url_user_business_profiles = BASE_URL + "/index.php?Apicontroller/listuserbusinessprofile";
    public static String url_user_investor_profiles = BASE_URL + "/index.php?Apicontroller/listuserinvestorprofile";
    public static String url_user_franchise_profiles = BASE_URL + "/index.php?Apicontroller/listuserfranchiseprofile";

    //User Profiles Detailed view
    public static String url_user_business_profile_update = BASE_URL + "/index.php?Apicontroller/listusersinglebusinessprofileedit";
    public static String url_user_investor_profile_update = BASE_URL + "/index.php?Apicontroller/listusersingleinvestorprofileedit";
    public static String url_user_franchise_profile_update = BASE_URL + "/index.php?Apicontroller/listusersinglefranchiseprofileedit";

    //Contact Business
    public static String url_contact_business_for_sale = BASE_URL + "/index.php?apicontroller/contactbusinessform";
    public static String url_contact_business_investors_buyers = BASE_URL + "/index.php?apicontroller/investorform";
    public static String url_contact_business_franchise = BASE_URL + "/index.php?apicontroller/franchisform";

    // Dashboard Search
    public static String url_dashboard_search_autofil = BASE_URL + "/index.php?Apicontroller/search";

    /*********  Business Profile *********/
    public static String url_dashboard_search_result_business_profile = BASE_URL + "/index.php?Apicontroller/listallbusinessprofile";
    public static String url_dashboard_search_result_business_profile_detail = BASE_URL + "/index.php?Apicontroller/listbusinessprofile";

    /*********  Franchise Profile *********/
    public static String url_dashboard_search_result_franchise_profile = BASE_URL + "/index.php?Apicontroller/listallfranchiseprofile";
    public static String url_dashboard_search_result_franchise_profile_detail = BASE_URL + "/index.php?Apicontroller/listsinglefranchiseprofile";

    /*********  Investor Profile *********/
    public static String url_dashboard_search_result_investor_profile = BASE_URL + "/index.php?Apicontroller/listallinvestorprofile";
    public static String url_dashboard_search_result_investor_profile_detail = BASE_URL + "/index.php?Apicontroller/listsingleinvestorprofile";

}


