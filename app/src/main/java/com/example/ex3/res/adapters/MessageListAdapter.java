package com.example.ex3.res.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex3.R;
import com.example.ex3.res.entities.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    class MessageMeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvContentMe;
        private TextView tvCreatedMe;

        private MessageMeViewHolder(View itemView) {
            super(itemView);
            tvContentMe = itemView.findViewById(R.id.tvContentMe);
            tvCreatedMe = itemView.findViewById(R.id.tvCreatedMe);
        }
    }

    class MessageOtherViewHolder extends RecyclerView.ViewHolder {
        private TextView tvContentOther;
        private TextView tvCreatedOther;

        private MessageOtherViewHolder(View itemView) {
            super(itemView);
            tvContentOther = itemView.findViewById(R.id.tvContentOther);
            tvCreatedOther = itemView.findViewById(R.id.tvCreatedOther);
        }
    }

    private LayoutInflater mInflater;
    private List<Message> messages;

    public MessageListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        String other = messages.get(position).getSent();
        if (Objects.equals(other, "false")) {
            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View itemViewMe = mInflater.inflate(R.layout.me_message_layout, parent, false);
            return new MessageMeViewHolder(itemViewMe);
        } else {
            View itemViewOther = mInflater.inflate(R.layout.other_message_layout, parent, false);
            return new MessageOtherViewHolder(itemViewOther);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (messages != null) {
            int type = holder.getItemViewType();
            Message current = messages.get(position);
            LocalDateTime date = LocalDateTime.parse(current.getCreated());
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
            String time = format.format(date);

            if (type == 0) {
                MessageMeViewHolder me = (MessageMeViewHolder) holder;
                me.tvContentMe.setText(current.getContent());
                me.tvCreatedMe.setText(time);
            } else {
                MessageOtherViewHolder other = (MessageOtherViewHolder) holder;
                other.tvContentOther.setText(current.getContent());
                other.tvCreatedOther.setText(time);
            }
        }

    }

    public void setMessages(List<Message> m) {
        if (m == null) {
            return;
        }
        messages = m;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (messages != null) {
            return messages.size();
        }
        return 0;
    }

    public List<Message> getMessages() {
        return messages;
    }
}


