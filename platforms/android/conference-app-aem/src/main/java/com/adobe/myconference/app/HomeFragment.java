package com.adobe.myconference.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.adobe.myconference.app.aem.R;
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

    // Plugin to call when activity result is received
    protected CordovaPlugin activityResultCallback = null;
    protected boolean activityResultKeepRunning;

    // Keep app running when pause is received. (default = true)
    // If true, then the JavaScript and native code continue to run in the background
    // when another application (activity) is started.
    protected boolean keepRunning = true;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    CordovaWebView aemView;


    /**
     * Returns a new instance of this fragment
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

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

    /**
     * Called when a message is sent to plugin.
     *
     * @param id
     *            The message id
     * @param data
     *            The message data
     * @return Object or null
     */
    public Object onMessage(String id, Object data) {
        return null;
    }

    public void onDestroy() {
        super.onDestroy();
        if (aemView.pluginManager != null) {
            aemView.pluginManager.onDestroy();
        }
    }

    // Cordova Interface Events
    @Override
    public ExecutorService getThreadPool() {
        return threadPool;
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin plugin) {
        this.activityResultCallback = plugin;
    }

    /**
     * Launch an activity for which you would like a result when it finished. When this activity exits,
     * your onActivityResult() method is called.
     *
     * @param command           The command object
     * @param intent            The intent to start
     * @param requestCode       The request code that is passed to callback to identify the activity
     */
    public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
        this.activityResultCallback = command;
        this.activityResultKeepRunning = this.keepRunning;

        // If multitasking turned on, then disable it for activities that return results
        if (command != null) {
            this.keepRunning = false;
        }

        // Start activity
        super.startActivityForResult(intent, requestCode);

    }

    @Override
    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode       The request code originally supplied to startActivityForResult(),
     *                          allowing you to identify who this result came from.
     * @param resultCode        The integer result code returned by the child activity through its setResult().
     * @param data              An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        CordovaPlugin callback = this.activityResultCallback;
        if (callback != null) {
            callback.onActivityResult(requestCode, resultCode, intent);
        }
    }

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
