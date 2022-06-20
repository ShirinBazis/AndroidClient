package com.example.ex3.userView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ex3.R;
import com.example.ex3.res.adapters.ContactListAdapter;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.viewModels.ContactListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ContactList extends Fragment {
    private ContactListViewModel viewModel;
    private ChatView frag2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        frag2 = new ChatView();
        View view = inflater.inflate(R.layout.activity_contact_list, container, false);
        viewModel = new ViewModelProvider(this).get(ContactListViewModel.class);
        startUtil(view);
        FloatingActionButton addContactBtn = view.findViewById(R.id.btnContactList);
        addContactBtn.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), AddContact.class);
            startActivity(i);
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void startUtil(View view) {
        RecyclerView contactList = view.findViewById(R.id.contactList);
        ContactListAdapter adapter = new ContactListAdapter(getContext(), username -> {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                ((MainActivity) requireActivity()).replaceFragment(frag2);
            else {

            }
        });
        contactList.setAdapter(adapter);
        contactList.setLayoutManager(new LinearLayoutManager(getContext()));
        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(() -> viewModel.reload(getListener(refreshLayout)));

        viewModel.get().observe(getViewLifecycleOwner(), contacts -> {
            if (contacts.size() != 0) {
                refreshLayout.setRefreshing(false);
            }
            adapter.setContacts(contacts);
        });
    }

    private CallbackListener getListener(SwipeRefreshLayout refreshLayout) {
        return new CallbackListener() {
            @Override
            public void onResponse(int code) {
                if (code != 200) {
                    Toast.makeText(getContext(), "Couldn't get contacts", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), R.string.cant_reach, Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        };
    }
}



