package com.example.yaatris;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddAdventure extends AppCompatActivity {

    Button ch, up;
    ImageView imgview;
    EditText name, location, checkpoint, from, to, price, description;
    DatabaseReference databaseReference;
    StorageReference storageReference ;
    private StorageTask uploadTask;
    public Uri FilePathUri;
    private ProgressDialog progressDialog;
    int Image_Request_Code = 7;
    private String cmail;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adventure);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cmail = extras.getString("companyEmail");
            TextView t = (TextView) findViewById(R.id.companymail);
            t.setText(cmail);
        }
        storageReference  = FirebaseStorage.getInstance().getReference("AdventureCompanyImages");
        databaseReference = FirebaseDatabase.getInstance().getReference("AdventureCompanyImages");
        ch = (Button) findViewById(R.id.btnChooseImage);
        up = (Button) findViewById(R.id.btnAddAdventure);
        imgview = (ImageView) findViewById(R.id.adventureImage);
        name = (EditText) findViewById(R.id.adventName);
        location = (EditText) findViewById(R.id.adventLocation);
        checkpoint = (EditText) findViewById(R.id.adventCheckpoints);
        from = (EditText) findViewById(R.id.adventFrom);
        to = (EditText) findViewById(R.id.adventTo);
        price = (EditText) findViewById(R.id.adventPrice);
        description = (EditText) findViewById(R.id.adventDesc);
        progressDialog = new ProgressDialog(AddAdventure.this);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddAdventure.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelTo();
            }
        };

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddAdventure.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });
    }



    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        from.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelTo() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        to.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }


    public void UploadImage() {

        if (FilePathUri != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String TempImageName = name.getText().toString().trim();
                            String adname = name.getText().toString();
                            String adlocation = location.getText().toString();
                            String adcheckpoint = checkpoint.getText().toString();
                            String adfrom = from.getText().toString();
                            String adto = to.getText().toString();
                            String adprice = price.getText().toString();
                            String addescription = description.getText().toString();

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            AdventureData imageUploadInfo = new AdventureData(cmail, adname, adlocation, adcheckpoint, adfrom, adto, adprice , addescription,  TempImageName, taskSnapshot.getStorage().getDownloadUrl().toString());
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
        }
        else {
            Toast.makeText(AddAdventure.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }



}