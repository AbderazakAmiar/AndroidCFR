package com.amiarstudio.contact.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amiarstudio.contact.Adapters.ContactAdapter;
import com.amiarstudio.contact.Models.Contact;
import com.amiarstudio.contact.R;
import com.amiarstudio.contact.Utils.Permissions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE_PROFILE = 4;
    private int RESULT_LOAD_IMAGE = 1;
public static  Context context;
public static RealmResults<Contact> Arr_contact;
public static ContactAdapter ContactAdapter;
private String TAG ="zak";
public static String Path;
public static Uri uri;
public static File file;
private RecyclerView rv_contact;
private RealmResults<Contact> result1;
private ImageView contactPicture;
private TextView contactName, contactNumber;
private ImageButton add_picture;
private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        rv_contact = findViewById(R.id.rv_contact);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

       // llm.setReverseLayout(true);
        rv_contact.setLayoutManager(llm);

        RealmQuery<Contact> query = realm.where(Contact.class);
        result1 = query.findAll();
        Log.i(TAG, "SIZE: "+result1.size());
        ContactAdapter = new ContactAdapter(result1,position -> {
            realm.beginTransaction();
            result1.deleteFromRealm(position);
            realm.commitTransaction();
            ContactAdapter.notifyDataSetChanged();

        });

        rv_contact.setAdapter(ContactAdapter);

        rv_contact.smoothScrollToPosition(result1.size());

        add_picture = findViewById(R.id.add_picture);

        add_picture.setOnClickListener((View v)->{
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);

        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE ){
            Toast.makeText(context,"Picture SELECTED", Toast.LENGTH_LONG).show();
            //Log.i(TAG, "requestCode: "+requestCode);
           // Log.i(TAG, "resultCode: "+resultCode);
            uri = data.getData();
            Picasso
                    .get()
                    .load( uri)
                    .placeholder(R.drawable.ic_launcher_background)
                    .resize(100, 100)
                    .into(add_picture);
            try {
                addContact(uri);
            }catch (Exception e){
                e.printStackTrace();
            }

            if (!Permissions.checkStoragePermission(this)) {
                Permissions.requestStoragePermission(this, STORAGE_PERMISSION_CODE_PROFILE);

            }

        }

    }

    public void addContact(Uri uri){
        Button addContact = findViewById(R.id.addContact);
        TextView ptName = findViewById(R.id.ptName);
        TextView etPhone = findViewById(R.id.etPhone);

        realm.beginTransaction();
        Contact contact =  new Contact(""+ptName.getText(),uri.toString(),""+etPhone.getText());
        Log.i(TAG, "<uri>: "+uri.toString());
        addContact.setOnClickListener((View v)->{

            try {
                realm.insertOrUpdate(contact);
                realm.commitTransaction();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this,"This Contact Exists",Toast.LENGTH_LONG).show();
            }
            rv_contact.smoothScrollToPosition(result1.size());
            ContactAdapter.notifyDataSetChanged();

        });



    }
}