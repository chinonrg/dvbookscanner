package com.example.dvbookscanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.*;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private Button btnCapture;
    private EditText nom, cantidad;
    private int cant = 0;
    private int count = 0;
    private static final int Image_Capture_Code = 1;
    private DatabaseReference mDatabaseShare;
    private StorageReference mStorageShare;
    private Uri mFileUri;
    private String carpeta;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCapture = findViewById(R.id.btnTakePicture);
        mDatabaseShare = FirebaseDatabase.getInstance().getReference().child("SharedMedia");
        mStorageShare = FirebaseStorage.getInstance().getReference();
        mDatabaseShare.keepSynced(true);
        mProgress = new ProgressDialog(this);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacarFoto();
            }
        });
    }
    private void sacarFoto(){
        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cInt.putExtra(MediaStore.EXTRA_OUTPUT, "scan" + count + ".jpg");
        cInt.putExtra("android.intent.extra.quickCapture", true);
        nom = (EditText)findViewById(R.id.nombreCarpeta);
        cantidad = (EditText)findViewById(R.id.cantidad);
        cant = Integer.parseInt(cantidad.getText().toString());
        carpeta = nom.getText().toString();
        startActivityForResult(cInt,Image_Capture_Code);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {
                while(cant > count) {
                    sacarFoto();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
                finish();
            }
            finish();
        }
    }
}