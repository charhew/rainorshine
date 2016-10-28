package com.example.veronica.rainorshine;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Charlene on 2016-10-27.
 */
public class NavBarFragment extends Fragment implements View.OnClickListener {

    ImageButton homeBtn, cameraBtn, uploadBtn;
    Communicate comm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navbar, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeBtn = (ImageButton) getActivity().findViewById(R.id.tab_home);
        cameraBtn = (ImageButton) getActivity().findViewById(R.id.tab_camera);
        uploadBtn = (ImageButton) getActivity().findViewById(R.id.tab_upload);

        comm = (Communicate) getActivity();
    }

    @Override
    public void onClick(View v) {
        comm.transmit("hello world");
    }
}
