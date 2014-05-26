package ch.connectcon.app.aem.plugin;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import ch.connectcon.app.aem.MainActivity;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;

public class Navigation extends CordovaPlugin {

    private static final String LOG_TAG = "Navigation";

    @Override
    public boolean execute(String action, CordovaArgs args, final CallbackContext callbackContext) throws JSONException {
        if ("navigate".equals(action)) {
            navigate(args, callbackContext);
            return true;
        }
        return false;
    }

    private void navigate(final CordovaArgs args, final CallbackContext callbackContext) {
        this.cordova.getActivity().runOnUiThread(new Runnable() {
            public void run() {
                navigateSync(args, callbackContext);
            }
        });
    }

    private void navigateSync(CordovaArgs args, final CallbackContext ctx) {
        try {
            MainActivity homeActivity = (MainActivity) this.cordova.getActivity();
            Context context = homeActivity.getApplicationContext();

            String bannerType = args.getString(0);
            String bannerValue = args.getString(1);

            if (bannerType.equals("INTERNAL_NAV")) {
                homeActivity.goToPage(Integer.valueOf(bannerValue).intValue());
            } else if (bannerType.equals("EXTERNAL_LINK")) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(bannerValue));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);
            }

            ctx.success();

        } catch (Exception e) {
            String errorMessage = "An error occurred during navigation.";
            ctx.error(errorMessage);
            Log.e(LOG_TAG, errorMessage, e);
        }
    }
}
