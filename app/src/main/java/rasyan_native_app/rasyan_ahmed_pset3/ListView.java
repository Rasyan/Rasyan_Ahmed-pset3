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
import java.util.Arrays;

/*
the main class, this activity shows the recyclerview, it is both the screen that shows the
favorites that you have added and the list that shows the search results after a search
*/

public class ListView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String[] posters;
    String[] titles;
    String[] years;
    String[] ids;

    // ImageLoader is a dependency i added, it gets images from the web.
    ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);


        // if the intent searched is passed then show the search results, otherwise favorites
        Intent intent = getIntent();
        if (intent.hasExtra("searched")) {
            // get the search results
            SearchResult json = (SearchResult) intent.getSerializableExtra("searched");
            titles = json.getTitles();
            posters = json.getPosters();
            years = json.getYears();
            ids = json.getIds();

        } else {
            SharedPreferences sharedPref = this.getSharedPreferences(
                    "Data", Context.MODE_PRIVATE);
            // check if this is the first time this app is run, if so make some demo favorites
            if (sharedPref.getString("titles",null) == null) {
                Toast.makeText(getApplicationContext(),"welcome for the first time!", Toast.LENGTH_SHORT).show();
                initializeData();
            } else {
                // else show your the favorites that you have saved
                readData();
            }

        }

        // Create global configuration and initialize ImageLoader with this config,
        // this is required for it to work
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        //load recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // use the adapter
        mAdapter = new MyAdapter(titles,posters,years,ids);
        mRecyclerView.setAdapter(mAdapter);

    }


    // use the corresponding menu
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
                // make a popup where the user can input its search text.
                View view =  (LayoutInflater.from(ListView.this)).inflate(R.layout.userinput, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ListView.this);
                alertBuilder.setView(view);
                final EditText search = (EditText) view.findViewById(R.id.edit);

                // if search button pressed execute the asyncthread to get the results from the web,
                // if successful the asyncthread makes an intent to reload this list with the search results
                alertBuilder.setCancelable(true).setPositiveButton("Search", new DialogInterface.OnClickListener() {
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

    // make some initial data for testing and to demo.
    public void initializeData() {
        SharedPreferences sharedPref = this.getSharedPreferences(
                "Data", Context.MODE_PRIVATE);
        // hardcoded test data.
        titles = new String[]{"Harry Potter and the Deathly Hallows: Part 2",
                "Jaws","Bridge of Spies"};
        posters = new String[]{"http://ia.media-imdb.com/images/M/" +
                "MV5BMTY2MTk3MDQ1N15BMl5BanBnXkFtZTcwMzI4NzA2NQ@@._V1_SX300.jpg",
                "N/A","http://ia.media-imdb.com/images/" +
                "M/MV5BMjIxOTI0MjU5NV5BMl5BanBnXkFtZTgwNzM4OTk4NTE@._V1_SX300.jpg"};
        years = new String[]{"2011","1975","2015"};
        ids = new String[]{"tt1201607","tt0073195","tt3682448"};

        // convert the arrays to a json array, because a json array can be saved in sharedpreference
        // as a string.
        JSONArray titlesJs = new JSONArray(Arrays.asList(titles));
        JSONArray postersJs = new JSONArray(Arrays.asList(posters));
        JSONArray yearsJs = new JSONArray(Arrays.asList(years));
        JSONArray idsJs = new JSONArray(Arrays.asList(ids));

        SharedPreferences.Editor editor = sharedPref.edit();
        //save the jsonarrays as strings
        editor.putBoolean("init", true);
        editor.putString("titles", titlesJs.toString());
        editor.putString("posters", postersJs.toString());
        editor.putString("years", yearsJs.toString());
        editor.putString("ids", idsJs.toString());
        editor.commit();
    }
    // read the favorite list for saved in shared preference
    private void readData() {
        SharedPreferences sharedPref = this.getSharedPreferences(
                "Data", Context.MODE_PRIVATE);
        try {
            //load the strings
            String titlesStr = sharedPref.getString("titles",null);
            String postersStr = sharedPref.getString("posters",null);
            String yearsStr = sharedPref.getString("years",null);
            String idsStr = sharedPref.getString("ids",null);
            //convert to Jsonarray
            JSONArray titlesJs = new JSONArray(titlesStr);
            JSONArray postersJs = new JSONArray(postersStr);
            JSONArray yearsJs = new JSONArray(yearsStr);
            JSONArray idsJs = new JSONArray(idsStr);



            // if the list is not empty
            if (!titlesJs.isNull(0)) {
                // make the array to give to the search result class
                int len = titlesJs.length();
                titles = new String[len];
                posters = new String[len];
                years = new String[len];
                ids = new String[len];

                for (int i=0;i<len;i++){
                    titles[i] = (titlesJs.get(i).toString());
                    posters[i] = (postersJs.get(i).toString());
                    years[i] = (yearsJs.get(i).toString());
                    ids[i] = (idsJs.get(i).toString());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ListView.class));
    }
}
