package com.example.ex3.userView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ex3.R;
import com.example.ex3.res.adapters.ContactListAdapter;
import com.example.ex3.res.viewModels.ContactListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class contactList extends AppCompatActivity {
    private ContactListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        viewModel = new ViewModelProvider(this).get(ContactListViewModel.class);

        RecyclerView contactList = findViewById(R.id.contactList);
        final ContactListAdapter adapter = new ContactListAdapter(this);
        contactList.setAdapter(adapter);
        contactList.setLayoutManager(new LinearLayoutManager(this));

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> viewModel.reload());
        viewModel.get().observe(this, contacts -> {
            if (contacts.size() != 0) {
                adapter.setContacts(contacts);
                refreshLayout.setRefreshing(false);
            }
        });

        FloatingActionButton addContactBtn = findViewById(R.id.btnContactList);
        addContactBtn.setOnClickListener((v) -> {
            Intent i = new Intent(this, AddContact.class);
            startActivity(i);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}