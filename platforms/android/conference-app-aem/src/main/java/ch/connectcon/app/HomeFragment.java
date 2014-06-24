package ch.connectcon.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ch.connectcon.app.aem.R;
import org.apache.cordova.Config;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment implements CordovaInterface {

    /**
     * Cordova
     */
    CordovaWebView aemView;


    /**
     * Returns a new instance of this fragment
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LayoutInflater localInflater = inflater.cloneInContext(new CordovaContext(getActivity(), this));

        View rootView = localInflater.inflate(R.layout.home_view_frag, container, false);
        aemView = (CordovaWebView) rootView.findViewById(R.id.aemWebView);
        Config.init(getActivity());
        aemView.loadUrl(Config.getStartUrl());
        return rootView;

    }

    /**
     * Cordova
     */

    //
    // TODO: add cordova
    //

    private class CordovaContext extends ContextWrapper implements CordovaInterface
    {
        CordovaInterface ci;

        // Hold two objects. Method call to Context will be proxied by ContextWrapper, so only delegate CordovaInterface.
        // You should add/modify when method in CordovaInterface changed
        public CordovaContext(Context base, CordovaInterface ci) {
            super(base);
            this.ci = ci;
        }
        public void startActivityForResult(CordovaPlugin command,
                                           Intent intent, int requestCode) {
            ci.startActivityForResult(command, intent, requestCode);
        }
        public void setActivityResultCallback(CordovaPlugin plugin) {
            ci.setActivityResultCallback(plugin);
        }
        public Activity getActivity() {
            return ci.getActivity();
        }
        public Object onMessage(String id, Object data) {
            return ci.onMessage(id, data);
        }
        public ExecutorService getThreadPool() {
            return ci.getThreadPool();
        }

    }

}
