package com.example.ex3.userView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ex3.R;
import com.example.ex3.res.adapters.ContactListAdapter;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.viewModels.ContactListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class contactList extends AppCompatActivity {
    private ContactListViewModel viewModel;

    private CallbackListener getListener(SwipeRefreshLayout refreshLayout) {
        return new CallbackListener() {
            @Override
            public void onResponse(int code) {
                if (code != 200) {
                    Toast.makeText(contactList.this, "Couldn't get contacts", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                Toast.makeText(contactList.this, "Can't reach server", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        };
    }

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
        refreshLayout.setOnRefreshListener(() -> viewModel.reload(getListener(refreshLayout)));

        viewModel.get().observe(this, contacts -> {
            if (contacts.size() != 0) {
                adapter.setContacts(contacts);
                refreshLayout.setRefreshing(false);
            }
            else {
                viewModel.reload(getListener(refreshLayout));
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