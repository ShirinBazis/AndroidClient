package com.example.ex3.userView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.ex3.R;

public class MainActivity extends AppCompatActivity {
    ContactList contactListP;
    ContactList contactListH;
    ChatView newChatView;
    Boolean isInMainPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contactListP = new ContactList(this, null);
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.horizontal_view);
            HorizontalMode();
        } else {
            setContentView(R.layout.portrait_view);
            replaceFragment(contactListP);
        }
    }

    public void onMessageSend() {
        if (contactListP != null) {
            contactListP.refresh();
        }
        if (contactListH != null) {
            contactListH.refresh();
        }
    }

    public void onBackPressed() {
        if (!isInMainPage) {
            replaceFragment(contactListP);
        } else {
            super.onBackPressed();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.portrait_frag_placeholder, fragment);
        fragmentTransaction.commit();
        isInMainPage = fragment.getClass() == contactListP.getClass();
    }

    private void HorizontalMode() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        newChatView = new ChatView(this);
        contactListH = new ContactList(this, newChatView);
        fragmentTransaction.replace(R.id.horizontal_frag_placeholder_left, contactListH);
        fragmentTransaction.replace(R.id.horizontal_frag_placeholder_right, newChatView);
        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.horizontal_view);
            HorizontalMode();
            isInMainPage = true;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.portrait_view);
            replaceFragment(new ContactList(this, null));
        }
    }
}