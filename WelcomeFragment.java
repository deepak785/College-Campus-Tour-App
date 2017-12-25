/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.devrel.vrviewapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import static android.R.attr.x;

/**
 * Fragment for handling the Welcome tab.
 */
public class WelcomeFragment extends Fragment {
    private ImageLoaderTask backgroundImageLoaderTask;
    private VrPanoramaView panoWidgetView;
    private TextView textView;
      double h;

    public WelcomeFragment(double h) {
        this.h = h;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.welcome_fragment, container,false);
        panoWidgetView = (VrPanoramaView) v.findViewById(R.id.pano_view);


        return v;
    }
    @Override
    public void onPause() {
        panoWidgetView.pauseRendering();
        super.onPause();
    }

    @Override
    public void onResume() {
        panoWidgetView.resumeRendering();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // Destroy the widget and free memory.
        panoWidgetView.shutdown();
        super.onDestroy();
    }
    private synchronized void loadPanoImage() {
        ImageLoaderTask task = backgroundImageLoaderTask;
        if (task != null && !task.isCancelled()) {
            // Cancel any task from a previous loading.
            task.cancel(true);
        }

        // pass in the name of the image to load from assets.
        VrPanoramaView.Options viewOptions = new VrPanoramaView.Options();
        viewOptions.inputType = VrPanoramaView.Options.TYPE_STEREO_OVER_UNDER;
        String panoImageName = "pano.jpg";
        String pano2ImageName="converted.jpg";
        String pano3ImageName="pano2.jpg";
        task = new ImageLoaderTask(panoWidgetView, viewOptions, panoImageName);
        task.execute(getActivity().getAssets());
        backgroundImageLoaderTask = task;



        // use the name of the image in the assets/ directory.
        double l =0;
         l= x*0.45*h;
        if(h>10.0 && h<30.0 )
        {task = new ImageLoaderTask(panoWidgetView, viewOptions, pano2ImageName);
             task.execute(getActivity().getAssets());
              backgroundImageLoaderTask = task;}
        else if(h>=30.0)
          {  task = new ImageLoaderTask(panoWidgetView, viewOptions, pano3ImageName);
        task.execute(getActivity().getAssets());
        backgroundImageLoaderTask = task;}


        // create the task passing the widget view and call execute to start.

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadPanoImage();
    }
}
