package rasyan_native_app.rasyan_ahmed_pset3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.util.Objects;
import java.util.Scanner;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private String[] images;
    private String[] names;
    private String[] years;
    private View.OnClickListener listener;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    public MyAdapter(String[] names, String[] imageURLs,String[] years, final String[] ids){
        this.images = imageURLs;
        this.names = names;
        this.years = years;


        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int position = ((ViewGroup) view.getParent()).indexOfChild(view);
                Apigetter getter = new Apigetter(context);
                getter.execute("id",ids[position]);
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView myName;
        public TextView myTime;
        public ImageView myPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            myName = (TextView) itemView.findViewById(R.id.name);
            myTime = (TextView) itemView.findViewById(R.id.time);
            myPhoto = (ImageView) itemView.findViewById(R.id.photo);

        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleview, parent, false);
        ViewHolder vh = new ViewHolder(view);
        context = view.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder vh, int i) {
        vh.myName.setText(names[i]);
        vh.myTime.setText(years[i]);
        vh.itemView.setOnClickListener(listener);
        if (!images[i].equals("N/A")){
            imageLoader.displayImage(images[i], vh.myPhoto);
        }
    }

    @Override
    public int getItemCount() {
        if (names != null) {
            return names.length;
        } else {
            return 0;
        }
    }
}