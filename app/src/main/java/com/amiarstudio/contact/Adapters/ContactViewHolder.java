package com.amiarstudio.contact.Adapters;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;
import com.amiarstudio.contact.Models.Contact;
import com.amiarstudio.contact.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import static com.amiarstudio.contact.Activities.MainActivity.Arr_contact;
import static com.amiarstudio.contact.Activities.MainActivity.context;
import static com.amiarstudio.contact.Activities.MainActivity.file;
import static com.amiarstudio.contact.Activities.MainActivity.uri;


public class ContactViewHolder extends RecyclerView.ViewHolder {
    Contact contact;
    ContactAdapter.OnItemClickListener listener;
    public ContactViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setData(Contact c, int position, ContactAdapter.OnItemClickListener listener) {
        this.listener = listener;
        contact = c;
        TextView contactName = itemView.findViewById(R.id.tvContactName);
        TextView contactNumber = itemView.findViewById(R.id.tvContactNumber);
        ImageView contactPicture = itemView.findViewById(R.id.ivContactPicture);

        contactName.setText(contact.getContactName());
        contactNumber.setText(contact.getContactNumber());

        try{
            Picasso
                    .get()
                    .load( Uri.parse(contact.getContactUriPicture()))
                    .placeholder(R.drawable.ic_launcher_background)
                    .resize(100, 100)
                    .into(contactPicture);
        }catch (Exception e){
            e.printStackTrace();
        }


        itemView.setOnLongClickListener((View v) -> {

            open(itemView, position);

            //Arr_contact.remove(position);

            return true;
        });


    }

    public void open(View view, int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("DELETE ?");

        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                            listener.onItemClick(position);
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}