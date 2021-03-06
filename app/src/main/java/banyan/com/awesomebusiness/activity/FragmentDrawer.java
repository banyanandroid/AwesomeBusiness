package banyan.com.awesomebusiness.activity;

/**
 * Created by JO on 19/07/17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.adapter.NavigationDrawerAdapter;
import banyan.com.awesomebusiness.global.SessionManager;
import banyan.com.awesomebusiness.model.NavDrawerItem;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.isDebugEnabled;


public class FragmentDrawer extends Fragment {

    private static String TAG = FragmentDrawer.class.getSimpleName();

    Button btn_login;
    TextView txt_user_name, txt_user_email_id, txt_logout;
    CircleImageView img_profile;
    String str_name, str_id, str_email, str_photo = "empty";

    // Session Manager Class
    SessionManager session;

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        txt_user_name = (TextView) layout.findViewById(R.id.nav_draw_txt_name);
        txt_user_email_id = (TextView) layout.findViewById(R.id.nav_draw_txt_email);
        img_profile = (CircleImageView) layout.findViewById(R.id.nav_draw_img_profile);

        btn_login = (Button) layout.findViewById(R.id.nav_draw_btn_login);
        txt_logout = (TextView) layout.findViewById(R.id.nav_draw_txt_logout);

        session = new SessionManager(getApplicationContext());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_name = sharedPreferences.getString("str_user_name", "str_user_name");
        str_email = sharedPreferences.getString("str_user_email", "str_user_email");
        str_photo = sharedPreferences.getString("str_user_photo", "str_user_photo");
        str_id = sharedPreferences.getString("str_user_id", "str_user_id");

        System.out.println("USER ID : " + str_id);

        if (str_id.equals("str_user_id")) {

            btn_login.setVisibility(View.VISIBLE);
        } else if (str_name.equals("str_user_name")) {

            txt_user_name.setVisibility(View.GONE);
            txt_logout.setVisibility(View.GONE);

        } else if (str_name.equals("str_user_email")) {

            txt_user_email_id.setVisibility(View.GONE);
            txt_logout.setVisibility(View.GONE);
        } else {
            txt_user_name.setVisibility(View.VISIBLE);
            txt_user_email_id.setVisibility(View.VISIBLE);
            txt_logout.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.GONE);

            txt_user_name.setText("" + str_name);
            txt_user_email_id.setText("" + str_email);
        }

        if (str_photo.equals("str_user_photo")) {

        } else {

            try {
                String str_img_path = str_photo;

                Glide.with(getApplicationContext())
                        .load(str_img_path)
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img_profile);

            } catch (Exception e) {

            }
        }


        adapter = new NavigationDrawerAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), Activity_Login.class);
                startActivity(i);
            }
        });

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (str_id.equals("str_user_id")) {

                    TastyToast.makeText(getActivity(), "Please Login", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else {

                    Intent i = new Intent(getActivity(), Activity_UserProfile.class);
                    startActivity(i);

                }

            }
        });

        txt_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (str_id.equals("str_user_id")) {

                    TastyToast.makeText(getActivity(), "Please Login", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else {

                    Intent i = new Intent(getActivity(), Activity_UserProfile.class);
                    startActivity(i);
                }
            }
        });

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}
