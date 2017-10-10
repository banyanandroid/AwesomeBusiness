package banyan.com.awesomebusiness.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import banyan.com.awesomebusiness.R;

/**
 * Created by Jo on 9/4/2017.
 */
public class Tab_Profile_Bookmarks extends Fragment {

    public Tab_Profile_Bookmarks() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =   inflater.inflate(R.layout.tab_profile_bookmark, container, false);


        return rootview;
    }

}
