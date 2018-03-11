package com.example.admin.learnenglish;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.zookey.universalpreferences.UniversalPreferences;

public class PremiumActivity extends AppCompatActivity {

    private RadioButton rad1, rad2;
    private BootstrapEditText editText;
    private BootstrapButton btnActivate, btnPay, btnCancel, btnOk;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_premium );

        editText = (BootstrapEditText) findViewById( R.id.etCardNumber );
        btnActivate = (BootstrapButton) findViewById( R.id.btnActivate );

        if(UniversalPreferences.getInstance().get( "isPremium", false )){
            btnActivate.setEnabled( false );
            editText.setText( UniversalPreferences.getInstance().get( "cardNumber", "1111222233334444" ).toString() );
            editText.setEnabled( false );
        }

        btnActivate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().length() == 16) {
                    UniversalPreferences.getInstance().put( "isPremium", true );
                    UniversalPreferences.getInstance().put( "cardNumber", editText.getText().toString() );
                    Toast.makeText( PremiumActivity.this, "Activated Successfully", Toast.LENGTH_SHORT ).show();
                    editText.setEnabled( false );
                    btnActivate.setEnabled( false );
                }
            }
        } );

        btnPay = (BootstrapButton) findViewById( R.id.btnPay );
        btnPay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        } );

    }

    private void showPopUp() {
        if (UniversalPreferences.getInstance().get( "isPremium", false )) {

            final AlertDialog.Builder helpBuilder = new AlertDialog.Builder( this );
            helpBuilder.setTitle( "Learn English" );

            LayoutInflater inflater = getLayoutInflater();
            final View PopupLayout = inflater.inflate( R.layout.payment, null );
            helpBuilder.setView( PopupLayout );

            final AlertDialog helpDialog = helpBuilder.create();
            helpDialog.show();

            btnCancel = (BootstrapButton) PopupLayout.findViewById( R.id.btnCancel );
            btnOk = (BootstrapButton) PopupLayout.findViewById( R.id.btnOK );
            rad1 = (RadioButton) findViewById( R.id.rad1 );
            rad2 = (RadioButton) findViewById( R.id.rad2 );

            btnCancel.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helpDialog.hide();
                }
            } );

            btnOk.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UniversalPreferences.getInstance().get( "isActivated", false )) {
                        Toast.makeText( PremiumActivity.this, "You already have a membership.", Toast.LENGTH_SHORT ).show();
                    } else {
                        paymentOnProgress();
                        helpDialog.hide();
                    }
                }
            } );
        } else {
            Toast.makeText( this, "Please add your mastercard.", Toast.LENGTH_SHORT ).show();
        }

    }

    private void paymentOnProgress() {
        final ProgressDialog dialog = ProgressDialog.show( this, "", "Payment on progress...", true );
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 3000 );
        UniversalPreferences.getInstance().put( "isActivated", true );
        Toast.makeText( PremiumActivity.this, "Payment successful", Toast.LENGTH_SHORT ).show();
    }

}
