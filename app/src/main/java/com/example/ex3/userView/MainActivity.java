package com.example.ex3.userView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.ex3.R;

public class MainActivity extends AppCompatActivity {
    ContactList contactList;
    Boolean isInMainPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        contactList = new ContactList();
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.horizontal_view);
            HorizontalMode();
        } else {
            setContentView(R.layout.portrait_view);
            replaceFragment(contactList);
        }
    }

    public void onBackPressed() {
        if (!isInMainPage) {
            replaceFragment(contactList);
        } else {
            super.onBackPressed();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.portrait_frag_placeholder, fragment);
        fragmentTransaction.commit();
        isInMainPage = fragment.getClass() == contactList.getClass();
    }

    private void HorizontalMode() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.horizontal_frag_placeholder_left, new ContactList());
        fragmentTransaction.replace(R.id.horizontal_frag_placeholder_right, new ChatView());
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
            replaceFragment(new ContactList());
        }
    }
}