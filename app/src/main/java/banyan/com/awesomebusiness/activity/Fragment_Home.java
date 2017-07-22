package banyan.com.awesomebusiness.activity;

/**
 * Created by Jo on 19/07/17.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.aromajoin.actionsheet.ActionSheet;
import com.aromajoin.actionsheet.OnActionListener;

import banyan.com.awesomebusiness.R;


public class Fragment_Home extends Fragment {

    public Fragment_Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        Button button_sort = (Button) rootView.findViewById(R.id.frag_btn_sort);
        button_sort.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                showActionSheet(view);
            }
        });
        Button button_filter = (Button) rootView.findViewById(R.id.frag_btn_filter);
        button_filter.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent i = new Intent(getActivity(),Activity_Filter.class);
                startActivity(i);

            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }

    private void showActionSheet(View anchor) {
        ActionSheet actionSheet = new ActionSheet(getActivity());
        actionSheet.setTitle("sort by");
        actionSheet.setSourceView(anchor);
        actionSheet.addAction("Newest", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });
        actionSheet.addAction("Oldest", ActionSheet.Style.DEFAULT, new OnActionListener() {
            @Override public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });

        actionSheet.addAction("Relevant", ActionSheet.Style.DESTRUCTIVE, new OnActionListener() {
            @Override public void onSelected(ActionSheet actionSheet, String title) {
                performAction(title);
                actionSheet.dismiss();
            }
        });

        actionSheet.show();
    }

    private void performAction(String title) {
      /*  Snackbar.make(MainActivity.this.findViewById(android.R.id.content), title,
               Snackbar.LENGTH_SHORT).show();*/
        Toast.makeText(getActivity(), "Sort By "+ title + " Content ", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
