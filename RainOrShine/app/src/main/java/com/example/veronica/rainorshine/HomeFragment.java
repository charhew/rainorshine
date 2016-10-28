package com.example.veronica.rainorshine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Charlene on 2016-10-27.
 */
public class HomeFragment extends Fragment {

    TextView helloName;
    String firstName;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent i = getActivity().getIntent();
        firstName = i.getStringExtra("name");
        Toast.makeText(getActivity(), firstName, Toast.LENGTH_SHORT).show();

        helloName = (TextView) getActivity().findViewById(R.id.helloTextView);
        helloName.setText("Hello,\n" + firstName + ".");
    }
}
