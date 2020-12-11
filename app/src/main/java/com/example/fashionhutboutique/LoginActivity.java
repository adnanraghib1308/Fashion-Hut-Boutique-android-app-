package com.example.fashionhutboutique;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static int AUTH_REQUEST_CODE = 7192;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;
    private List<AuthUI.IdpConfig> providers;

    TextView name;
    TextView email;
    TextView phoneNo;
    Button buttonLogout, buttonChangeProfilePic;
    ImageView profilepic;
    FirebaseUser user;
    ProgressBar progressBar;


    StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        buttonLogout = findViewById(R.id.logoutButton);
        buttonChangeProfilePic = findViewById(R.id.changeProfilePic);
        progressBar = findViewById(R.id.pbar);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(LoginActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });

        buttonChangeProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1000);
            }
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNo = findViewById(R.id.phone);
        profilepic = findViewById(R.id.profilePicture);


        storageReference = FirebaseStorage.getInstance().getReference();


        if(user != null){
            String n = user.getDisplayName();
            String e = user.getEmail();
            String p = user.getPhoneNumber();
            Uri photoUrl = user.getPhotoUrl();
            //if(!n.equals(""))
                name.setText(n);
            //if(!e.equals(""))
                email.setText(e);
            //if(!p.equals(""))
                phoneNo.setText(p);
            if(photoUrl!=null){
                Glide.with(this)
                        .load(user.getPhotoUrl())
                        .into(profilepic);
            }
           // profilepic.setImageURI(photoUrl);

        }
        else{
            providers = Arrays.asList(
                    new AuthUI.IdpConfig.AppleBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.EmailBuilder().build()
                    //new AuthUI.IdpConfig.FacebookBuilder().build(),
                    //new AuthUI.IdpConfig.PhoneBuilder().build()
            );
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(R.drawable.fhlogo)
                    .setTheme(R.style.firebaselogin)
                    .build(), AUTH_REQUEST_CODE);
        }





    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            if(resultCode == RESULT_OK){
                Uri image = data.getData();
                //profilepic.setImageURI(image);
                progressBar.setVisibility(View.VISIBLE);
                uploadProfilePic(image);

            }
        }

        if(requestCode == AUTH_REQUEST_CODE){
            if (resultCode == RESULT_OK) {
                //user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(getApplicationContext(), "Sign In Success:  " , Toast.LENGTH_SHORT).show();

                Intent loginToDashboardIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(loginToDashboardIntent);

            } else {
                Intent loginToDashboardIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(loginToDashboardIntent);
            }
        }


    }

    private void uploadProfilePic(final Uri image) {
        final StorageReference ref = storageReference.child("profileImages").child(user.getUid()+".jpeg");
        ref.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                profilepic.setImageURI(image);
                progressBar.setVisibility(View.GONE);
                getDownloadUrl(ref);
            }
        });
    }
        private void getDownloadUrl(StorageReference reference){
            reference.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            setUserProfileUrl(uri);
                        }
                    });
        }

    private void setUserProfileUrl(Uri uri) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "User profile updated.");
                            Toast.makeText(LoginActivity.this, "Profile Pic updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}