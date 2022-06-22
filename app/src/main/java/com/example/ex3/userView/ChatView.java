package com.example.ex3.userView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.res.entities.Message;
import com.example.ex3.res.viewModels.ChatViewViewModel;
import com.example.ex3.res.viewModels.ContactListViewModel;

import java.util.List;

public class ChatView extends Fragment {
    View view;
    ChatViewViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chat_view, container, false);
        viewModel =  new ViewModelProvider(this).get(ChatViewViewModel.class);
        viewModel.get().observe(getViewLifecycleOwner(), messages -> {
            Toast.makeText(getContext(), messages.get(0).getContent(), Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}