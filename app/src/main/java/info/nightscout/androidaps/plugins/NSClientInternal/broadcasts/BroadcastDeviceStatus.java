package info.nightscout.androidaps.plugins.NSClientInternal.broadcasts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import info.nightscout.androidaps.MainApp;
import info.nightscout.androidaps.R;
import info.nightscout.androidaps.Services.Intents;
import info.nightscout.utils.SP;


public class BroadcastDeviceStatus {
    public static void handleNewDeviceStatus(JSONArray statuses, Context context, boolean isDelta) {

        List<JSONArray> splitted = BroadcastTreatment.splitArray(statuses);
        for (JSONArray part: splitted) {
            Bundle bundle = new Bundle();
            bundle.putString("devicestatuses", part.toString());
            bundle.putBoolean("delta", isDelta);
            Intent intent = new Intent(Intents.ACTION_NEW_DEVICESTATUS);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            LocalBroadcastManager.getInstance(MainApp.instance()).sendBroadcast(intent);
        }

        if(SP.getBoolean(R.string.key_nsclient_localbroadcasts, true)) {
            splitted = BroadcastTreatment.splitArray(statuses);
            for (JSONArray part : splitted) {
                Bundle bundle = new Bundle();
                bundle.putString("devicestatuses", part.toString());
                bundle.putBoolean("delta", isDelta);
                Intent intent = new Intent(Intents.ACTION_NEW_DEVICESTATUS);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                context.sendBroadcast(intent);
            }
        }
    }
}
