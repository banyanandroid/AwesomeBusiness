package banyan.com.awesomebusiness.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import banyan.com.awesomebusiness.R;
import banyan.com.awesomebusiness.model.Image_Model;

public class Horizondal_Image_Adapter extends RecyclerView.Adapter<Horizondal_Image_Adapter.MyViewHolder> {

    private List<Image_Model> moviesList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_photo;

        public MyViewHolder(View view) {
            super(view);
            img_photo = (ImageView) view.findViewById(R.id.movie_list_img);
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
        Image_Model movie = moviesList.get(position);
        //holder.title.setText(movie.getTitle());

        Glide.with(mContext)
                .load(movie.getTitle())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.mipmap.ic_delete)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.img_photo);

          /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
