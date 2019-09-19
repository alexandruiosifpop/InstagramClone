package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String allUsers;
    private Button signUpBtn;
    private TextView termsConds;
    private EditText emailEdt, fullNameEdt, usernameEdt, phoneEdt, passwordEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpBtn = (Button) findViewById(R.id.signUpBtnId);
        termsConds = (TextView) findViewById(R.id.termsConditionsId);
        emailEdt = (EditText) findViewById(R.id.emailId);
        fullNameEdt = (EditText) findViewById(R.id.usernameId);
        phoneEdt = (EditText) findViewById(R.id.phoneId);
        passwordEdt = (EditText) findViewById(R.id.passwordId);

        signUpBtn.setOnClickListener(MainActivity.this);
        termsConds.setOnClickListener(MainActivity.this);

        // Save the current Installation to Back4App
        //every time a user downloads the app this method is called
        ParseInstallation.getCurrentInstallation().saveInBackground();


    }

    @Override
    public void onClick(View v) {
        allUsers = null;
        switch (v.getId()) {
            case R.id.signUpBtnId:
            try {
                final ParseObject users = new ParseObject("Users");
                users.put("Email", emailEdt.getText().toString());
                users.put("username", fullNameEdt.getText().toString());
                //  users.put("Username", usernameEdt.getText().toString());
                users.put("PhoneNo", phoneEdt.getText().toString());
                users.put("Password", passwordEdt.getText().toString());
                users.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            FancyToast.makeText(MainActivity.this,
                                    users.get("username") + "is saved",
                                    Toast.LENGTH_LONG,
                                    FancyToast.SUCCESS,
                                    true)
                                    .show();
                        } else {
                            FancyToast.makeText(MainActivity.this,
                                    e.getMessage(),
                                    Toast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    false)
                                    .show();
                        }
                    }

                });
            } catch (Exception e) {
                FancyToast.makeText(MainActivity.this,
                        e.getMessage(),
                        Toast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false)
                        .show();
            }

            break;
            case R.id.termsConditionsId:
                try{

                    //get more info from a field
                    //use getInBackground in you want a specific user --- you need to have an ID
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Users") ;
                parseQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null){
                            if (objects != null){

                                for (ParseObject parseObject : objects) {
                                    allUsers += parseObject.get("U") + "\n";
                                }
                                termsConds.setText(allUsers);
                            }
                        }
                    }
                });

        }catch (Exception e){
                    FancyToast.makeText(MainActivity.this,
                            e.getMessage(),
                            Toast.LENGTH_LONG,
                            FancyToast.ERROR,
                            false)
                            .show();
                }
        }

    }
}
