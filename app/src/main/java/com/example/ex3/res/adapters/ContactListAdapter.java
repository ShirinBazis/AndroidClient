package com.example.ex3.res.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex3.R;
import com.example.ex3.res.entities.Contact;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder> {
    class ContactListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDisplayName;
        private TextView tvLast;
        private TextView tvLastDate;
        private ImageView tvImage;

        private ContactListViewHolder(View itemView) {
            super(itemView);
            tvDisplayName = itemView.findViewById(R.id.tvContactDisplayName);
            tvLast = itemView.findViewById(R.id.tvContactLastMessage);
            tvLastDate = itemView.findViewById(R.id.tvContactLastTime);
            tvImage = itemView.findViewById(R.id.ivContactImage);
        }
    }

    private LayoutInflater mInflater;
    private List<Contact> contacts;
    private ContactListener listener;

    public ContactListAdapter(Context context, ContactListener listener) {
        mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactListAdapter.ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_layout, parent, false);
        return new ContactListViewHolder(itemView);
    }

    private void setImage(ContactListAdapter.ContactListViewHolder holder) {
        Random rand = new Random();
        int range = 100;
        int int_random = rand.nextInt(range);
        switch (int_random % 6) {
            case 0: {
                holder.tvImage.setImageResource(R.drawable.avatar_1);
                break;
            }
            case 1: {
                holder.tvImage.setImageResource(R.drawable.avatar_2);
                break;
            }
            case 2: {
                holder.tvImage.setImageResource(R.drawable.avatar_3);
                break;
            }
            case 3: {
                holder.tvImage.setImageResource(R.drawable.avatar_4);
                break;
            }
            case 4: {
                holder.tvImage.setImageResource(R.drawable.avatar_5);
                break;
            }
            default:
                holder.tvImage.setImageResource(R.drawable.avatar_6);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactListViewHolder holder, int position) {
        if (contacts != null) {
            final Contact current = contacts.get(position);
            holder.tvDisplayName.setText(current.getName());
            setImage(holder);
            holder.itemView.setOnClickListener(v -> {
                listener.onClick(current.getId());
            });
            if (current.getLast() != null) {
                LocalDateTime date = LocalDateTime.parse(current.getLastdate());
                DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
                String time = format.format(date);
                holder.tvLast.setText(current.getLast());
                holder.tvLastDate.setText(time);
            }
        }
    }

    public void setContacts(List<Contact> c) {
        if (c == null) {
            return;
        }
        contacts = c;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (contacts != null) {
            return contacts.size();
        }
        return 0;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
