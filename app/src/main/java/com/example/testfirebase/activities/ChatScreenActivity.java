package com.example.testfirebase.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.testfirebase.R;
import com.example.testfirebase.databinding.ActivityChatBinding;
import com.example.testfirebase.databinding.ActivityChatScreenBinding;
import com.example.testfirebase.models.User;
import com.example.testfirebase.utilities.Constants;

public class ChatScreenActivity extends AppCompatActivity {

    private ActivityChatScreenBinding binding;
    private User recieverUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadRecieverDetails();
    }

    //Load User Name into chatting screen top
    private void loadRecieverDetails()
    {
        recieverUser=(User) getIntent().getSerializableExtra(Constants.KEY_USER);
        binding.textName.setText(recieverUser.name);
    }

    //Going back from chatting screen
    private void setListeners()
    {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }
}