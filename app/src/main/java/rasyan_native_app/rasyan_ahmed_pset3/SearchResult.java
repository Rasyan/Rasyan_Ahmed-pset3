package rasyan_native_app.rasyan_ahmed_pset3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Rasyan on 23-9-2016.
 */

public class SearchResult implements Serializable {
    private String[] posters;
    private String[] titles;
    private String[] years;
    private String[] ids;

    public SearchResult(JSONArray jsons, int size) {
        JSONObject json;
        posters = new String[size];
        titles = new String[size];
        years = new String[size];
        ids = new String[size];
        try {
            for (int i = 0; i < size;i++) {
                json = jsons.getJSONObject(i);
                titles[i] = json.getString("Title");
                posters[i] = json.getString("Poster");
                years[i] = json.getString("Year");
                ids[i] = json.getString("imdbID");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public String[] getPosters() {
        return posters;
    }

    public String[] getTitles() {
        return titles;
    }

    public String[] getIds() {
        return ids;
    }

    public String[] getYears() {
        return years;
    }
}
