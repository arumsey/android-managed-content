package ch.connectcon.app.plugin;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import ch.connectcon.app.MainActivity;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

public class Navigation extends CordovaPlugin {

    private static final String LOG_TAG = "Navigation";

    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        return false;
    }

    //TODO: add cordova plugin

}
