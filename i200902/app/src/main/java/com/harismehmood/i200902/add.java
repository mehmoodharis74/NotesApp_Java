package com.harismehmood.i200902;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class add extends AppCompatActivity {
    EditText name,data_entry;
    String category,password="";
    Button add;
    RecyclerView view ;
    RecyclerAdapterMain adapter;
    ArrayList<contact_model> arraycontacts;
    Db_helper myDB;
    Button upload_image_btn;
    Uri uri;
    RoundedImageView image;
    AutoCompleteTextView dropdown;
    ArrayAdapter<String> dropdown_adapter;
    String[] string_item;



    protected void createRecyclerView(){
        view = findViewById(R.id.recyclerView);
        view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapterMain(this,arraycontacts);
        view.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final Context currentContext= add.this;

         add = findViewById(R.id.add_button);
         name = findViewById(R.id.name_ip);
         data_entry=findViewById(R.id.data_entry_in_add_note_editText);

        image = findViewById(R.id.add_contact_upload_image);
        upload_image_btn= findViewById(R.id.add_contact_upload_image_btn);
        dropdown= findViewById(R.id.auto_complete_text);

myDB = new Db_helper(currentContext);

        //dropdown adapter code
        string_item= new String[100];
        string_item= myDB.selectAllCategories();
       // createDropDownAdapter();
        dropdown_adapter= new ArrayAdapter<String>(currentContext,R.layout.dropdownitems,string_item);
        dropdown.setAdapter(dropdown_adapter);



                selectCategoryFunction(currentContext);
//set default image if user will not take image from the gallery
        uri = Uri.parse("android.resource://com.harismehmood.i200902/drawable/ic_baseline_assignment_24");
        image.setImageURI(uri);

        upload_image_btn.setOnClickListener(v->{
            Intent iGallery=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            //iGallery.setType("image/*");
            // iGallery.ACTION_OPEN_DOCUMENT;
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery,1000);
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Db_helper myDB = new Db_helper(add.this);
                if(name.getText().toString().equals("") || data_entry.getText().toString().equals("") || category.equals("")|| category.equals("ADD NEW CATEGORY")){
                    //do nothing
                    Toast.makeText(add.this, "Provide some information", Toast.LENGTH_SHORT).show();
                }
                else if(password.equals("")){
                    getPasswordFunction(currentContext);
                }
                else{
//                    Date date=new Date();
//                    SimpleDateFormat formatter= new SimpleDateFormat("dd/MM");
//                    String curr_Date=formatter.format(date);
                    //get current date of system in dd/oct format
                    Date date=new Date();
                    SimpleDateFormat formatter= new SimpleDateFormat("dd/MM");
                    String curr_Date=formatter.format(date);
                    //if month is 01 than convert it into jan and so on
                    String month=curr_Date.substring(3,5);
                    if(month.equals("01")){
                        curr_Date=curr_Date.substring(0,2)+" Jan";
                    }
                    else if(month.equals("02")){
                        curr_Date=curr_Date.substring(0,2)+" Feb";
                    }
                    else if(month.equals("03")){
                        curr_Date=curr_Date.substring(0,2)+" Mar";
                    }
                    else if(month.equals("04")){
                        curr_Date=curr_Date.substring(0,2)+" Apr";
                    }
                    else if(month.equals("05")){
                        curr_Date=curr_Date.substring(0,2)+" May";
                    }
                    else if(month.equals("06")){
                        curr_Date=curr_Date.substring(0,2)+" Jun";
                    }
                    else if(month.equals("07")){
                        curr_Date=curr_Date.substring(0,2)+" Jul";
                    }
                    else if(month.equals("08")){
                        curr_Date=curr_Date.substring(0,2)+" Aug";
                    }
                    else if(month.equals("09")){
                        curr_Date=curr_Date.substring(0,2)+" Sep";
                    }
                    else if(month.equals("10")){
                        curr_Date=curr_Date.substring(0,2)+" Oct";
                    }
                    else if(month.equals("11")){
                        curr_Date=curr_Date.substring(0,2)+" Nov";
                    }
                    else if(month.equals("12")){
                        curr_Date=curr_Date.substring(0,2)+" Dec";
                    }

//curr_Date=currDateTime();

                myDB.insert(name.getText().toString(),curr_Date,category,data_entry.getText().toString(), uri.toString(),password);
                finish();
                }
            }
        });

    }
//    public String conversionOfDate(String curr_Date) {
//
//        //if month is 01 than convert it into jan and so on
//        String month = curr_Date.substring(5, 7);
//        String day = curr_Date.substring(8, 10);
//        if (month.equals("01")) {
//            curr_Date = day + " Jan";
//        } else if (month.equals("02")) {
//            curr_Date = day + " Feb";
//        } else if (month.equals("03")) {
//            curr_Date = day + " Mar";
//        } else if (month.equals("04")) {
//            curr_Date = day + " Apr";
//        } else if (month.equals("05")) {
//            curr_Date = day + " May";
//        } else if (month.equals("06")) {
//            curr_Date = day + " Jun";
//        } else if (month.equals("07")) {
//            curr_Date = day + " Jul";
//        } else if (month.equals("08")) {
//            curr_Date = day + " Aug";
//        } else if (month.equals("09")) {
//            curr_Date = day + " Sep";
//        } else if (month.equals("10")) {
//            curr_Date = day + " Oct";
//        } else if (month.equals("11")) {
//            curr_Date = day + " Nov";
//        } else if (month.equals("12")) {
//            curr_Date = day + " Dec";
//        }
//        return  curr_Date;
//    }
    public void getPasswordFunction(Context currentContext){


        //-----------------------------dialog for password builder---------------------------
        Dialog builder = new Dialog(currentContext);
        builder.setContentView(R.layout.add_new_category);

        TextView addNewCategoryHead= builder.findViewById(R.id.add_new_category_head);
        EditText new_category= builder.findViewById(R.id.new_category_editText);
        Button add_new_category_btn= builder.findViewById(R.id.add_new_category_btn);

        addNewCategoryHead.setText("Enter Password");
        add_new_category_btn.setText("Add");
        new_category.setHint("Password");
        //change input type of new category to password
        new_category.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        //on click on button append new category to the string of dropdown items
        add_new_category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String new_category_string= new_category.getText().toString();
//                //add new category item to dropdown items
//                myDB.insertCategory(new_category_string);
//                string_item= myDB.selectAllCategories();
//                dropdown_adapter= new ArrayAdapter<String>(currentContext,R.layout.dropdownitems,string_item);
//                dropdown.setAdapter(dropdown_adapter);
                //if input string is empty generate message of type provide password
                if(new_category.getText().toString().equals("")){
                    Toast.makeText(currentContext, "Provide Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(currentContext, "Password Set Successfully", Toast.LENGTH_SHORT).show();
                    password =new_category.getText().toString();
                    builder.dismiss();
                }
            }
        });
        builder.show();
        //return new_category.getText().toString();
    }
    public  void selectCategoryFunction(Context currentContext){
        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category=parent.getItemAtPosition(position).toString();

                if(category.equals("ADD NEW CATEGORY")){
                    Dialog builder = new Dialog(currentContext);
                    builder.setContentView(R.layout.add_new_category);
                    EditText new_category= builder.findViewById(R.id.new_category_editText);
                    Button add_new_category_btn= builder.findViewById(R.id.add_new_category_btn);

                    //on click on button append new category to the string of dropdown items
                    add_new_category_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String new_category_string= new_category.getText().toString();
                            //add new category item to dropdown items
                            myDB.insertCategory(new_category_string);
                            string_item= myDB.selectAllCategories();
                            dropdown_adapter= new ArrayAdapter<String>(currentContext,R.layout.dropdownitems,string_item);
                            dropdown.setAdapter(dropdown_adapter);

                            Toast.makeText(add.this, "Category Added Successfully", Toast.LENGTH_SHORT).show();
                            builder.dismiss();
                        }
                    });
                    builder.show();
                    //if(new_category.getText().toString().equals(""))
                }
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}


