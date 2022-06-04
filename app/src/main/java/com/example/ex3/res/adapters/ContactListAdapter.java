package com.example.ex3.res.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ex3.R;
import com.example.ex3.res.entities.Contact;

import java.util.List;

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
            tvImage= itemView.findViewById(R.id.ivContactImage);
        }
    }

    private LayoutInflater mInflater;
    private List<Contact> contacts;

    public ContactListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContactListAdapter.ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.contact_layout, parent, false);
        return new ContactListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactListViewHolder holder, int position) {
        if (contacts != null) {
            final Contact current = contacts.get(position);
            holder.tvDisplayName.setText(current.getName());
            if(current.getLast()!=null){
                String[] date = current.getLastdate().split("\\.",0);
                String time = date[0].replace('T',' ');
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
