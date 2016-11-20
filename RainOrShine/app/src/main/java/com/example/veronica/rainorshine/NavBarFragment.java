package com.example.veronica.rainorshine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Charlene on 2016-10-27.
 */
public class NavBarFragment extends Fragment implements View.OnClickListener {

    ImageButton homeBtn, cameraBtn, uploadBtn;
    Communicate comm;

    public static final int GET_FROM_GALLERY = 3;

    private static final int CAMERA_REQUEST = 1;

    byte[] imageName;
    int imageId;
    Bitmap theImage;
    ImageDatabaseHelper db;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * create DatabaseHandler object
         */
        db = new ImageDatabaseHelper(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* code to show camera option dialog */

        String[] option = new String[] {"Take from Camera"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Log.e("Selected Item", String.valueOf(which));
                if (which == 0) {
                    callCamera();
                }
            }
        });

        final AlertDialog dialog = builder.create();

        View view = inflater.inflate(R.layout.fragment_navbar, container, false);

        cameraBtn = (ImageButton) view.findViewById(R.id.tab_camera);

        // what happens when you tap the CAMERA BUTTON
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeBtn = (ImageButton) getActivity().findViewById(R.id.tab_home);
        uploadBtn = (ImageButton) getActivity().findViewById(R.id.tab_upload);


        // what happens when you tap the UPLOAD BUTTON
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), "upload btn clicked", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                Toast.makeText(getActivity(), "gallery intent started", Toast.LENGTH_SHORT).show();
            }
        });

        comm = (Communicate) getActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap uploadBitmap = null;
            try {
                uploadBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                Toast.makeText(getActivity(), "bitmap instantiated", Toast.LENGTH_SHORT).show();

                // convert bitmap to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                uploadBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte imageInByte[] = stream.toByteArray();

                // Inserting camera input
                db.addCameraInput(new CameraInput(imageInByte, "caption", 0, "weather condition"));
                Intent i = new Intent(getActivity(), CreatePost.class);
                startActivity(i);
                getActivity().finish();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        else if (resultCode != RESULT_OK)
        return;

        switch (requestCode) {
            case CAMERA_REQUEST:

                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();

                    // Inserting camera input
                    db.addCameraInput(new CameraInput(imageInByte, "caption", 0, "weather condition"));
                    Intent i = new Intent(getActivity(), CreatePost.class);
                    startActivity(i);
                    getActivity().finish();

                }
                break;
        }
    }

    public void callCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 200);
    }

    @Override
    public void onClick(View v) {
        comm.transmit("hello world");
    }
}
