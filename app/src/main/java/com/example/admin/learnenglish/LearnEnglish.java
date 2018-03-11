package com.example.admin.learnenglish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvingResultCallbacks;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.zookey.universalpreferences.UniversalPreferences;


public class LearnEnglish extends AppCompatActivity{

    private static final int RC_SIGN_IN = 1;
    public static GoogleApiClient googleApiClient;
    SignInButton btnSignIn;
    private static final String TAG = "LearnEnglish";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_english);

        UniversalPreferences.initialize( this );
        btnSignIn = (SignInButton) findViewById( R.id.btn_signIn );

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null ){
                    UniversalPreferences.getInstance().put( "email",  firebaseAuth.getCurrentUser().getEmail());
                    startActivity( new Intent( LearnEnglish.this, MainActivity.class ) );
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder( getApplicationContext())
                .enableAutoManage( this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText( LearnEnglish.this, "Connection Failed", Toast.LENGTH_SHORT ).show();
                    }
                } )
                .addApi( Auth.GOOGLE_SIGN_IN_API, gso )
                .build();

        btnSignIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        } );

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener( mAuthListener );
    }

    private void SignIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent( googleApiClient );
        startActivityForResult( intent, RC_SIGN_IN );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent( data );
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle( account );
            }
            else
                Toast.makeText( this, "Authentication Failed", Toast.LENGTH_SHORT ).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText( LearnEnglish.this, "Failed", Toast.LENGTH_SHORT ).show();

                        }

                        // ...
                    }
                });
    }

    public static void signOut(){
        Auth.GoogleSignInApi.signOut( googleApiClient ).setResultCallback( new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        } );
    }

}
