package banyan.com.awesomebusiness.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.util.ArrayList;
import java.util.List;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.model.Image_Model;

public class Horizondal_Image_Adapter extends RecyclerView.Adapter<Horizondal_Image_Adapter.MyViewHolder> {

    private List<Image_Model> moviesList;
    private Context mContext;
    ArrayList<String> Arraylist_image_delete = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_photo, img_delete;

        public MyViewHolder(View view) {
            super(view);
            img_photo = (ImageView) view.findViewById(R.id.movie_list_img);
            img_delete = (ImageView) view.findViewById(R.id.horizondal_delete);
        }
    }


    public Horizondal_Image_Adapter(Context context, List<Image_Model> moviesList) {
        this.moviesList = moviesList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Image_Model movie = moviesList.get(position);
        //holder.title.setText(movie.getTitle());

        Glide.with(mContext)
                .load(movie.getTitle())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.mipmap.ic_delete)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img_photo);


        Arraylist_image_delete = new ArrayList<String>();
        Arraylist_image_delete.clear();

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = movie.getYear();
                Arraylist_image_delete.add(id);
                String str_final_delete_img = "";
                str_final_delete_img = TextUtils.join(", ", Arraylist_image_delete);

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("str_delete_imgs", str_final_delete_img);
                editor.commit();

            }
        });
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
