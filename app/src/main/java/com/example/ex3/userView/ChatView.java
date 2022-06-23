package com.example.ex3.userView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.ex3.AppDB;
import com.example.ex3.Ex3;
import com.example.ex3.R;
import com.example.ex3.res.adapters.MessageListAdapter;
import com.example.ex3.res.api.CallbackListener;
import com.example.ex3.res.entities.Message;
import com.example.ex3.res.viewModels.ChatViewViewModel;

public class ChatView extends Fragment {
    View view;
    ChatViewViewModel viewModel;
    EditText messageText;
    String contactId;
    MainActivity mainActivity;

    public ChatView(MainActivity i) {
        this.mainActivity = i;
    }

    public void onContactChangeP(String id) {
        contactId = id;
    }

    public void onContactChangeH(String id) {
        if (contactId != id) {
            contactId = id;
            viewModel.reload(contactId, CallbackListener.getDefault());
        }
    }

    private CallbackListener getListener() {
        return new CallbackListener() {
            @Override
            public void onResponse(int code) {
                messageText.getText().clear();
                mainActivity.onMessageSend();
                viewModel.reload(contactId, CallbackListener.getDefault());
            }

            @Override
            public void onFailure() {
                Toast.makeText(Ex3.context, R.string.cant_reach, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chat_view, container, false);
        viewModel = new ViewModelProvider(this).get(ChatViewViewModel.class);
        startUtil(view);
        Button sendBtn = view.findViewById(R.id.button_gchat_send);
        messageText = view.findViewById(R.id.edit_gchat_message);
        sendBtn.setOnClickListener(v -> {
            if (messageText.getText().length() != 0 && contactId != null) {
                Message m = new Message(messageText.getText().toString(), "time", "true", contactId);
                viewModel.sendMessage(m, getListener());
            }
        });
        return view;
    }

    private void startUtil(View view) {
        String name = AppDB.getInstance().contactDao().get(contactId).getName();
        RecyclerView messageList = view.findViewById(R.id.lstMessages);
        MessageListAdapter adapter = new MessageListAdapter(getContext());
        messageList.setAdapter(adapter);
        messageList.setLayoutManager(new LinearLayoutManager(getContext()));
        SwipeRefreshLayout refreshLayoutMessages = view.findViewById(R.id.refreshLayoutMessages);
        refreshLayoutMessages.setOnRefreshListener(() -> viewModel.reload(contactId, CallbackListener.getDefault()));
        viewModel.get(contactId).observe(getViewLifecycleOwner(), messages -> {
            if (messages.size() != 0) {
                refreshLayoutMessages.setRefreshing(false);
            }
            adapter.setMessages(messages);
            messageList.scrollToPosition(adapter.getItemCount() - 1);
        });
    }
}