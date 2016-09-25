package rasyan_native_app.rasyan_ahmed_pset3;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //JSONObject json;

    String[] posters;
    String[] titles;
    String[] years;
    String[] ids;


    ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Intent intent = getIntent();
        if (intent.hasExtra("searched")) {
            SearchResult json = (SearchResult) intent.getSerializableExtra("searched");
            titles = json.getTitles();
            posters = json.getPosters();
            years = json.getYears();
            ids = json.getIds();

        } else {
            SharedPreferences sharedPref = this.getSharedPreferences(
                    "PREFENCESsssss", Context.MODE_PRIVATE);
            if (sharedPref.getString("titles",null) == null) {
                Toast.makeText(getApplicationContext(),"welcome for the first time!", Toast.LENGTH_SHORT).show();
                initializeData();
            } else {
                readData();
            }





            //posters = new String[] {"http://ia.media-imdb.com/images/M/MV5BMTY2MTk3MDQ1N15BMl5BanBnXkFtZTcwMzI4NzA2NQ@@._V1_SX300.jpg","N/A","http://ia.media-imdb.com/images/M/MV5BMjIxOTI0MjU5NV5BMl5BanBnXkFtZTgwNzM4OTk4NTE@._V1_SX300.jpg"};
            //titles = new String[] {"Harry Potter and the Deathly Hallows: Part 2","Jaws","Bridge of Spies"};
            //ids = new String[] {"tt1201607","tt0073195","tt3682448"};
        }

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);


        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(titles,posters,years,ids);
        mRecyclerView.setAdapter(mAdapter);

        //ImageView v = (ImageView) findViewById(R.id.ddd);
        //imageLoader.displayImage("http://ia.media-imdb.com/images/M/MV5BMTY2MTk3MDQ1N15BMl5BanBnXkFtZTcwMzI4NzA2NQ@@._V1_SX300.jpg", v);
    }


    // use the corrosponding menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                //View view = (Layout)
                View view =  (LayoutInflater.from(ListView.this)).inflate(R.layout.userinput, null);

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ListView.this);
                alertBuilder.setView(view);
                final EditText search = (EditText) view.findViewById(R.id.edit);

                alertBuilder.setCancelable(true).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Apigetter getter = new Apigetter(ListView.this);
                        getter.execute("search",search.getText().toString());
                        search.getText().clear();
                    }
                });
                Dialog dialog = alertBuilder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void initializeData() {
        SharedPreferences sharedPref = this.getSharedPreferences(
                "PREFENCESsssss", Context.MODE_PRIVATE);
        titles = new String[]{"Harry Potter and the Deathly Hallows: Part 2",
                "Jaws","Bridge of Spies"};
        posters = new String[]{"http://ia.media-imdb.com/images/M/" +
                "MV5BMTY2MTk3MDQ1N15BMl5BanBnXkFtZTcwMzI4NzA2NQ@@._V1_SX300.jpg",
                "N/A","http://ia.media-imdb.com/images/" +
                "M/MV5BMjIxOTI0MjU5NV5BMl5BanBnXkFtZTgwNzM4OTk4NTE@._V1_SX300.jpg"};
        years = new String[]{"2011","1975","2015"};
        ids = new String[]{"tt1201607","tt0073195","tt3682448"};

//        ArrayList<String> titleList = new ArrayList<String>();
//        titleList.add("Harry Potter and the Deathly Hallows: Part 2");
//        titleList.add("Jaws");
//        titleList.add("Bridge of Spies");
//
//        ArrayList<String> posterList = new ArrayList<String>();
//        posterList.add("http://ia.media-imdb.com/images/M/" +
//                "MV5BMTY2MTk3MDQ1N15BMl5BanBnXkFtZTcwMzI4NzA2NQ@@._V1_SX300.jpg");
//        posterList.add("N/A");
//        posterList.add("http://ia.media-imdb.com/images/" +
//                "M/MV5BMjIxOTI0MjU5NV5BMl5BanBnXkFtZTgwNzM4OTk4NTE@._V1_SX300.jpg");
//
//        ArrayList<String> yearList = new ArrayList<String>();
//        yearList.add("2011");
//        yearList.add("1975");
//        yearList.add("2015");
//
//        ArrayList<String> idList = new ArrayList<String>();
//        idList.add("tt1201607");
//        idList.add("tt0073195");
//        idList.add("tt3682448");
//
//        titles = titleList.toArray(new String[0]);
//        posters = posterList.toArray(new String[0]);
//        years = yearList.toArray(new String[0]);
//        ids = idList.toArray(new String[0]);

        JSONArray titlesJs = new JSONArray(Arrays.asList(titles));
        JSONArray postersJs = new JSONArray(Arrays.asList(posters));
        JSONArray yearsJs = new JSONArray(Arrays.asList(years));
        JSONArray idsJs = new JSONArray(Arrays.asList(ids));

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("init", true);
        editor.putString("titles", titlesJs.toString());
        editor.putString("posters", postersJs.toString());
        editor.putString("years", yearsJs.toString());
        editor.putString("ids", idsJs.toString());
        editor.commit();

        //LinkedHashSet<String> titleSet = new LinkedHashSet<String>(Arrays.asList(titles));
        //LinkedHashSet<String> posterSet = new LinkedHashSet<String>(Arrays.asList(posters));
        //LinkedHashSet<String> yearSet = new LinkedHashSet<String>(Arrays.asList(years));
        //LinkedHashSet<String> idSet = new LinkedHashSet<String>(Arrays.asList(ids));
        //SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putStringSet("titles", titleSet);
        //editor.putStringSet("posters", posterSet);
        //editor.putStringSet("years", yearSet);
        //editor.putStringSet("ids", idSet);
        //editor.commit();
    }

    private void readData() {
        SharedPreferences sharedPref = this.getSharedPreferences(
                "PREFENCESsssss", Context.MODE_PRIVATE);
//        LinkedHashSet<String> titleSet = (LinkedHashSet<String>) sharedPref.getStringSet("titles",null);
//        LinkedHashSet<String> posterSet = (LinkedHashSet<String>) sharedPref.getStringSet("posters",null);
//        LinkedHashSet<String> yearSet = (LinkedHashSet<String>) sharedPref.getStringSet("years",null);
//        LinkedHashSet<String> idSet = (LinkedHashSet<String>) sharedPref.getStringSet("ids",null);
        try {

            System.out.println("kabutos" + sharedPref.getAll());
            String titlesStr = sharedPref.getString("titles",null);
            String postersStr = sharedPref.getString("posters",null);
            String yearsStr = sharedPref.getString("years",null);
            String idsStr = sharedPref.getString("ids",null);
            JSONArray titlesJs = new JSONArray(titlesStr);
            JSONArray postersJs = new JSONArray(postersStr);
            JSONArray yearsJs = new JSONArray(yearsStr);
            JSONArray idsJs = new JSONArray(idsStr);




            if (!titlesJs.isNull(0)) {
                int len = titlesJs.length();
                titles = new String[len];
                posters = new String[len];
                years = new String[len];
                ids = new String[len];

                System.out.println("zepdosz");
                for (int i=0;i<len;i++){
                    titles[i] = (titlesJs.get(i).toString());
                    posters[i] = (postersJs.get(i).toString());
                    years[i] = (yearsJs.get(i).toString());
                    ids[i] = (idsJs.get(i).toString());
                }
            } else {
                System.out.println("test no list");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
