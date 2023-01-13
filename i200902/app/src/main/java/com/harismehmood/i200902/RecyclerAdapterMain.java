package com.harismehmood.i200902;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class RecyclerAdapterMain extends RecyclerView.Adapter<RecyclerAdapterMain.viewholder> {
    Context context;
    ArrayList<contact_model> arraycontacts;
    RecyclerAdapterMain(Context context, ArrayList<contact_model> array_contact){
        this.context=context;
        this.arraycontacts=array_contact;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item=LayoutInflater.from(context).inflate(R.layout.contact_row, parent,false);
        viewholder view_holder=new viewholder(item);
        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtname.setText(arraycontacts.get(position).name);
        holder.txttype.setText(arraycontacts.get(position).note_type);
        holder.txtcategory.setText(arraycontacts.get(position).category);
        holder.txtdata.setText(arraycontacts.get(position).data);
        holder.img.setImageURI(arraycontacts.get(position).img);


        holder.single_contact.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //-----------------------------dialog for password builder---------------------------
                Dialog builder = new Dialog(context);
                builder.setContentView(R.layout.add_new_category);

                TextView addNewCategoryHead = builder.findViewById(R.id.add_new_category_head);
                EditText new_category = builder.findViewById(R.id.new_category_editText);
                Button add_new_category_btn = builder.findViewById(R.id.add_new_category_btn);

                addNewCategoryHead.setText("Enter password to Delete");
                add_new_category_btn.setText("Delete");
                new_category.setHint("Password");
                //change input type of new category to password
                new_category.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                
                //on click on button append new category to the string of dropdown items
                add_new_category_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (new_category.getText().toString().equals(arraycontacts.get(position).password)) {
                            //delete contact
                            Db_helper myDB = new Db_helper(context);
                            myDB.delete_note(arraycontacts.get(position).id);
                            arraycontacts.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(context, "Note deleted Successfully", Toast.LENGTH_SHORT).show();
                            builder.dismiss();
                        } else
                            Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return true;
            }


        });

    }
    @Override
    public int getItemCount() {
        return arraycontacts.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtname, txttype,txtcategory,txtdata;
        LinearLayout single_contact;
        RoundedImageView img;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            // photo = itemView.findViewById(R.id.contact_img);
            txtname=itemView.findViewById(R.id.contact_text);
            txttype=itemView.findViewById(R.id.date_textView_contactRow);
            txtcategory=itemView.findViewById(R.id.category_text);
            txtdata=itemView.findViewById(R.id.data_text_contactRow_textView);
            single_contact=itemView.findViewById(R.id.single_item_layout);
            img=itemView.findViewById(R.id.contact_img);
        }
    }

}

