package com.example.admin.learnenglish;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zookey.universalpreferences.UniversalPreferences;


public class EssayActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 12345;
    private BootstrapButton btnUpload, btnDownload;
    private Button btnChoose;
    private Uri filePath;
    private EditText etFilename, downloadLink;
    private StorageReference mStorageRef;
    private String res = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_essay );

        btnChoose = (Button) findViewById( R.id.btnChoose );
        btnUpload = (BootstrapButton) findViewById( R.id.btnUpload );
        etFilename = (EditText) findViewById( R.id.etFilename );
        etFilename.setMaxEms( 20 );
        btnDownload = (BootstrapButton) findViewById( R.id.btnDownload );
        downloadLink = (EditText) findViewById( R.id.downloadLink );
        mStorageRef = FirebaseStorage.getInstance().getReference();

        btnChoose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        } );

        btnUpload.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileUpload();
            }
        } );

        btnDownload.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadEssay();
            }
        } );


    }

    private void downloadEssay() {
        String username = UniversalPreferences.getInstance().get( "email", "" );
        username = username.substring( 0, username.indexOf( '@' ) );
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference( username );

        myRef.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue( String.class );
                if (value.length() > 0) {
                    downloadLink.setAutoLinkMask( Linkify.WEB_URLS );
                } else
                    value = "No Graded Essay Found";
                downloadLink.setText( value );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText( EssayActivity.this, "Failed", Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    private void fileUpload() {
        if (filePath != null) {

            StorageReference riversRef = mStorageRef.child( UniversalPreferences.getInstance().get( "email", "" ) + "/" + etFilename.getText().toString() + ".pdf" );

            final ProgressDialog progressDialog = new ProgressDialog( this );
            progressDialog.setTitle( "Uploading..." );
            progressDialog.show();

            riversRef.putFile( filePath )
                    .addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText( EssayActivity.this, "File uploaded successfully", Toast.LENGTH_SHORT ).show();
                        }
                    } )
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText( EssayActivity.this, "Network error", Toast.LENGTH_SHORT ).show();
                        }
                    } )
                    .addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            int progress = (int) (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setTitle( "Uploading... \n" + progress + "%" );
                        }
                    } );
        } else {
            Toast.makeText( this, "Please choose file first", Toast.LENGTH_SHORT ).show();
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent( Intent.ACTION_GET_CONTENT );
        intent.setType( "application/pdf" );
        intent.addCategory( Intent.CATEGORY_OPENABLE );
        try {
            startActivityForResult( Intent.createChooser( intent, "Select a File to Upload" ), FILE_SELECT_CODE );
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText( this, "Please install a File Manager.", Toast.LENGTH_SHORT ).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
        }

    }

}


