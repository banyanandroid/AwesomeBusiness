package banyan.com.awesomebusiness.api;


import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;

public class RetroFitApi {

    public interface Register {
        @POST("/index.php/transaction")
        void registerapi(
                @Body JsonObject body,
                Callback<Response> callback);

    }


}
