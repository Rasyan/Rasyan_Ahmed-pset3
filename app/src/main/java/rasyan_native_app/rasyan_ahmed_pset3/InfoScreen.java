package rasyan_native_app.rasyan_ahmed_pset3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class InfoScreen extends AppCompatActivity {
    String[] info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);

        try {
            JSONObject json = new JSONObject(getIntent().getStringExtra("json"));
            TextView title = (TextView) findViewById(R.id.titles);
            title.setText(json.getString("Title"));
            TextView lang = (TextView) findViewById(R.id.lang);
            lang.setText("Language:   " + json.getString("Language"));
            TextView score = (TextView) findViewById(R.id.score);
            score.setText("ImdbRating:   " + json.getString("imdbRating"));
            TextView year = (TextView) findViewById(R.id.year);
            year.setText("Year:   " + json.getString("Year"));
            TextView genre = (TextView) findViewById(R.id.genre);
            genre.setText("Genre:   " + json.getString("Genre"));
            TextView plot = (TextView) findViewById(R.id.plot);
            plot.setText(json.getString("Plot"));
            String image = json.getString("Poster");
            if (!image.equals("N/A")){
                ImageView poster = (ImageView) findViewById(R.id.poster);
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(image, poster);
            }
            info = new String[]{json.getString("Title"),json.getString("Poster"),json.getString("Year"),json.getString("imdbID")};


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:

                SharedPreferences sharedPref = this.getSharedPreferences(
                        "Data", Context.MODE_PRIVATE);

                boolean duplicate = false;
                try {
                    String titlesStr = sharedPref.getString("titles", null);
                    String postersStr = sharedPref.getString("posters", null);
                    String yearsStr = sharedPref.getString("years", null);
                    String idsStr = sharedPref.getString("ids", null);
                    JSONArray titlesJs = new JSONArray(titlesStr);
                    JSONArray postersJs = new JSONArray(postersStr);
                    JSONArray yearsJs = new JSONArray(yearsStr);
                    JSONArray idsJs = new JSONArray(idsStr);


                    if (!titlesJs.isNull(0)) {
                        ArrayList<String> titles = new ArrayList<String>();
                        ArrayList<String> posters = new ArrayList<String>();
                        ArrayList<String> years = new ArrayList<String>();
                        ArrayList<String> ids = new ArrayList<String>();


                        int len = titlesJs.length();
                        for (int i = 0; i < len; i++) {
                            String id = idsJs.get(i).toString();
                            if (id.equals(info[3])) {
                                duplicate = true;
                            } else {
                                titles.add(titlesJs.get(i).toString());
                                posters.add(postersJs.get(i).toString());
                                years.add(yearsJs.get(i).toString());
                                ids.add(id);
                            }
                        }
                        if (!duplicate) {
                            titles.add(info[0]);
                            posters.add(info[1]);
                            years.add(info[2]);
                            ids.add(info[3]);
                        }
                        titlesJs = new JSONArray(titles);
                        postersJs = new JSONArray(posters);
                        yearsJs = new JSONArray(years);
                        idsJs = new JSONArray(ids);

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("init", true);
                        editor.putString("titles", titlesJs.toString());
                        editor.putString("posters", postersJs.toString());
                        editor.putString("years", yearsJs.toString());
                        editor.putString("ids", idsJs.toString());
                        editor.commit();
                    } else {

                        ArrayList<String> titles = new ArrayList<String>();
                        ArrayList<String> posters = new ArrayList<String>();
                        ArrayList<String> years = new ArrayList<String>();
                        ArrayList<String> ids = new ArrayList<String>();


                        titles.add(info[0]);
                        posters.add(info[1]);
                        years.add(info[2]);
                        ids.add(info[3]);


                        titlesJs = new JSONArray(titles);
                        postersJs = new JSONArray(posters);
                        yearsJs = new JSONArray(years);
                        idsJs = new JSONArray(ids);

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("init", true);

                        editor.putString("titles", titlesJs.toString());
                        editor.putString("posters", postersJs.toString());
                        editor.putString("years", yearsJs.toString());
                        editor.putString("ids", idsJs.toString());
                        editor.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent= new Intent(InfoScreen.this,ListView.class);
                startActivity(intent);
                if (!duplicate) {
                    Toast.makeText(getApplicationContext(),info[0] +
                            " Added to favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),info[0] +
                            " removed from favorites", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
