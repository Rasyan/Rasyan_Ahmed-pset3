package rasyan_native_app.rasyan_ahmed_pset3;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Rasyan on 23-9-2016.
 */

public class Apigetter extends AsyncTask<String,Integer,String> {
    private Context context;
    private JSONObject json;
    private String type;

    public Apigetter(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String imdb;
        type = params[0];
        if (Objects.equals(type, "search")) {
            imdb = "http://www.omdbapi.com/?s=%s";
        } else {
            imdb = "http://www.omdbapi.com/?i=%s";
        }

        try {
            String utf = URLEncoder.encode(params[1],"UTF-8");
            URL url = new URL(String.format(imdb,utf));
            System.out.println("stringURL = " + url.toString());
            InputStream stream = url.openStream();

            Scanner s = new Scanner(stream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";

            json = new JSONObject(result);
            System.out.println("json = " + json.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("akaka" + json.toString());
        switch (type) {
            case "search":
                try {
                    if (json.getString("Response").equals("True")) {
                        SearchResult searchresult = new SearchResult(json.getJSONArray("Search")
                                , Integer.parseInt(json.getString("totalResults")));
                        Intent intent = new Intent(context, ListView.class);
                        intent.putExtra("searched", searchresult);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, json.getString("Error"),
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return;
            case "id":
                Intent intent= new Intent(context, InfoScreen.class);
                intent.putExtra("json", json.toString());
                context.startActivity(intent);

        }
    }
}
