package com.RokZ.coronastevec;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import org.json.JSONException;
import java.io.IOException;


public class CoronaWidget extends AppWidgetProvider {



    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) throws IOException, JSONException {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.coronawidget); // Pridobi vse objekte
        /*  Nastavitve za gradnike v widgetu */
        views.setTextViewText(R.id.set_date, StatsGetter.getDatum());
        views.setTextViewText(R.id.st_poz,String.format("+%s",StatsGetter.getDanesOkuzeni()));
        views.setTextViewText(R.id.st_umrli, String.format("+%s",StatsGetter.getDanesMrtvi()));
        views.setTextViewText(R.id.st_test,StatsGetter.getTestirani());
        views.setTextViewText(R.id.sedemDni_povprecje,StatsGetter.get7day());

        /*   */

        //Refresh button
        Intent intentSync = new Intent(context, CoronaWidget.class);
        intentSync.putExtra( AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] { appWidgetId } );
        intentSync.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        PendingIntent pendingSync = PendingIntent.getBroadcast(context,appWidgetId, intentSync, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button,pendingSync);


        //Go on app
        Intent intent2 = new Intent(context,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,appWidgetId,intent2,0);
        views.setOnClickPendingIntent(R.id.mainLay, pi);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds)
            try {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

}