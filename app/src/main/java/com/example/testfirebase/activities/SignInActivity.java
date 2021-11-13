package com.example.testfirebase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testfirebase.databinding.ActivitySignInBinding;
import com.example.testfirebase.utilities.Constants;
import com.example.testfirebase.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManager =new PreferenceManager(getApplicationContext());
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN))
        {
            Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
            startActivity(intent);
            finish();
        }
        binding=ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners()
    {
        binding.textCreateAccount.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),CreatePassword.class)));
        binding.btnSignIn.setOnClickListener(v -> {
            if(isValidSigninDetails())
            {
                signIn();
            }
        });

    }

    private void signIn()
    {
        loading(true);
        FirebaseFirestore database=FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_ENROLLMENT,binding.inputEnrollment.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD,binding.inputPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() !=null
                    && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot=task.getResult().getDocuments().get(0);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                        preferenceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_ENROLLMENT,documentSnapshot.getString(Constants.KEY_ENROLLMENT));
                        preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE));
                        Intent intent =new Intent(getApplicationContext(), ChatActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else
                    {
                        loading(false);
                        showToast("Unable to Sign In");
                    }
                });

    }


    private void loading(Boolean isLoading)
    {
        if(isLoading){
            binding.btnSignIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else{
            binding.btnSignIn.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    //Toast objet
    private void showToast(String message)
    {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
    }

    //Creating Toast Validation
    private Boolean isValidSigninDetails()
    {
        if(binding.inputEnrollment.getText().toString().trim().isEmpty())
        {
            showToast("Enter Email");
            return false;
        } else if(binding.inputPassword.getText().toString().trim().isEmpty()){
            showToast("Enter Password");
            return false;
        }
        else{
            return true;
        }

    }



}