package banyan.com.awesomebusiness.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import banyan.com.awesomebusiness.R;

/**
 * Created by Jo on 9/4/2017.
 */
public class Tab_Franchises_Profile extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    String TAG = "Business Profile";

    private ListView List;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tab_franchises, null);


        List = (ListView) rootview.findViewById(R.id.my_franchises_profile);
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.my_enq_swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                     /*   try {
                                            queue = Volley.newRequestQueue(getActivity());
                                            GetMyEnquries();

                                        } catch (Exception e) {
                                            // TODO: handle exceptions
                                        }*/
                                    }
                                }
        );



        return rootview;

    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
       /* try {
            enquiry_list.clear();
            queue = Volley.newRequestQueue(getActivity());
            GetMyEnquries();

        } catch (Exception e) {
            // TODO: handle exception
        }*/
    }
}
