package com.amiarstudio.contact.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amiarstudio.contact.Models.Contact;
import com.amiarstudio.contact.R;

import java.util.ArrayList;

import io.realm.RealmResults;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    OnItemClickListener listener;
    public ContactAdapter(RealmResults<Contact> arr_Contact, OnItemClickListener listener) {
        Arr_Contact = arr_Contact;
        this.listener = listener;
    }

    RealmResults<Contact> Arr_Contact;
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item,parent,false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
    holder.setData(Arr_Contact.get(position), position, listener);
    }

    @Override
    public int getItemCount() {
        return Arr_Contact.size();
    }
}
