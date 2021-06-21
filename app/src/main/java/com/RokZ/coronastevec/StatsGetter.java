
package com.RokZ.coronastevec;
import android.os.StrictMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public class StatsGetter {

    public static String api = "";
    public static JSONObject obj;

    public static void get_coronaAPI_String() throws IOException, JSONException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String read;

        URL url = new URL("https://api.sledilnik.org/api/summary");
        URLConnection yc = url.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

        while ((read = in.readLine()) != null)
            api += read;

        in.close();
        obj = new JSONObject(api);
    }


    public static String getDatum() throws IOException, JSONException {
        get_coronaAPI_String();
        return String.format("%s. %s. %s", obj.getJSONObject("testsToday").getInt("day"), obj.getJSONObject("testsToday").getInt("month"), obj.getJSONObject("testsToday").getInt("year"));
    }

    public static String getDanesOkuzeni() throws IOException, JSONException {
        return obj.getJSONObject("testsToday").getJSONObject("subValues").getInt("positive") + "";
    }

    public static String getDanesMrtvi() throws IOException, JSONException {
        get_coronaAPI_String();
        return obj.getJSONObject("deceasedToDate").getJSONObject("subValues").getInt("deceased") + "";
    }

    public static String get7day() throws IOException, JSONException {
        get_coronaAPI_String();
        return ((int)obj.getJSONObject("casesAvg7Days").getDouble("value")) + "";
    }

    public static String getTestirani() throws IOException, JSONException {
        get_coronaAPI_String();
        return (obj.getJSONObject("testsToday").getInt("value") + obj.getJSONObject("testsTodayHAT").getInt("value") ) + "";
    }
}
