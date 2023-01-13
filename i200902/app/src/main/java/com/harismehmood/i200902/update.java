package com.harismehmood.i200902;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class update extends AppCompatActivity {

EditText updateName, updateData;
Button updateButton;
    RecyclerView viewSearch ;
    RecyclerAdapterMain adapterSearch;
    ArrayList<contact_model> arrayContactsSearch;
    Db_helper myDB;
    Button upload_image_btn;
    Uri uri=null;
    RoundedImageView image;

    public void createRecyclerView(){
        viewSearch = findViewById(R.id.searchRecyclerView);
        viewSearch.setLayoutManager(new LinearLayoutManager(this));
        adapterSearch = new RecyclerAdapterMain(this,arrayContactsSearch);
        viewSearch.setAdapter(adapterSearch);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        uri = Uri.parse("android.resource://com.harismehmood.i200902/drawable/ic_baseline_assignment_24");
        //image.setImageURI(uri);


        updateName = findViewById(R.id.name_ip);
        updateData = findViewById(R.id.data_entry_in_add_note_editText);
        updateButton = findViewById(R.id.add_button);
        image= findViewById(R.id.add_contact_upload_image);
        upload_image_btn= findViewById(R.id.add_contact_upload_image_btn);
        TextView textView = findViewById(R.id.enter_information_head);
        RecyclerView rv= findViewById(R.id.searchRecyclerView);
        TextInputLayout categoryMainBox = findViewById(R.id.category_main_box);


        upload_image_btn.setText("Update Image");
        updateButton.setText("Update Contact");
        image.setMaxWidth(50);
        image.setMaxHeight(50);
        textView.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
        categoryMainBox.setVisibility(View.GONE);


        myDB = new Db_helper(this);
        arrayContactsSearch= new ArrayList<contact_model>();
        arrayContactsSearch=myDB.select_all();
        createRecyclerView();

        //drop down recycler view for category

        ImageButton searchButton = findViewById(R.id.searchButton);
        EditText search= findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search.getText().toString().equals("")){
                    arrayContactsSearch = myDB.select_all();
                }else{
                        //if it is text string
                        arrayContactsSearch = myDB.search(search.getText().toString());
                    if(arrayContactsSearch.size()==0){
                        Toast.makeText(update.this, "No Search found\n Try other keyword", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        updateName.setText(arrayContactsSearch.get(0).name);
                        updateData.setText(arrayContactsSearch.get(0).data);
                        image.setImageURI(arrayContactsSearch.get(0).img);
                    }

                }
                createRecyclerView();
                search.setText("");
            }

        });

        upload_image_btn.setOnClickListener(v->{
            Intent iGallery=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            //iGallery.setType("image/*");
            // iGallery.ACTION_OPEN_DOCUMENT;
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery,1000);
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Db_helper myDBUpdate = new Db_helper(update.this);
                myDBUpdate.update_note_name(arrayContactsSearch.get(0).id,updateName.getText().toString());
                myDBUpdate.update_data(arrayContactsSearch.get(0).id,updateData.getText().toString());
                myDBUpdate.update_image(arrayContactsSearch.get(0).id,uri.toString());
                myDBUpdate.update_date(arrayContactsSearch.get(0).id);
                myDBUpdate.update_type(arrayContactsSearch.get(0).id,conversionOfDate(myDBUpdate.get_date(arrayContactsSearch.get(0).id)));
//                search.arrayContactsSearch = myDBUpdate.select_all();
//                search.createRecyclerView();
                finish();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri =null;
        if(resultCode==RESULT_OK)
        {
            if(requestCode==1000)

            {
                image.setImageURI(data.getData());
                uri=(Uri)data.getData();  //save uri for further processing
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
            }
        }

        image.setImageURI(uri);

    }
    public String conversionOfDate(String curr_Date) {

        //if month is 01 than convert it into jan and so on
        String month = curr_Date.substring(5, 7);
        String day = curr_Date.substring(8, 10);
        if (month.equals("01")) {
            curr_Date = day + " Jan";
        } else if (month.equals("02")) {
            curr_Date = day + " Feb";
        } else if (month.equals("03")) {
            curr_Date = day + " Mar";
        } else if (month.equals("04")) {
            curr_Date = day + " Apr";
        } else if (month.equals("05")) {
            curr_Date = day + " May";
        } else if (month.equals("06")) {
            curr_Date = day + " Jun";
        } else if (month.equals("07")) {
            curr_Date = day + " Jul";
        } else if (month.equals("08")) {
            curr_Date = day + " Aug";
        } else if (month.equals("09")) {
            curr_Date = day + " Sep";
        } else if (month.equals("10")) {
            curr_Date = day + " Oct";
        } else if (month.equals("11")) {
            curr_Date = day + " Nov";
        } else if (month.equals("12")) {
            curr_Date = day + " Dec";
        }
        return  curr_Date;
    }
}