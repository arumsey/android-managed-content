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

public class HomeFragment extends Fragment { //TODO: add CordovaInterface

    /**
     * Cordova
     */

    //
    // TODO: add cordova
    //


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


        //TODO: add cordova

        View rootView = inflater.inflate(R.layout.home_view_frag, container, false);
        return rootView;
    }

    /**
     * Cordova
     */

    //
    // TODO: add cordova
    //

}
