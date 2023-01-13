package com.harismehmood.i200902;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private  long pressedTime;
    RecyclerView view ;
    RecyclerAdapterMain adapter;
    ArrayList<contact_model> arraycontacts;
    Db_helper myDB;
    DrawerLayout drawer_layout;
    Toolbar toolbar;
    ImageButton changeLayout_btn;
    Integer layoutType = 1;


    protected void createRecyclerView(){
        view = findViewById(R.id.recyclerView);

        if(layoutType == 1)
            view.setLayoutManager(new LinearLayoutManager(this));
        else if(layoutType == 2)
            view.setLayoutManager(new GridLayoutManager(this,2));

        adapter = new RecyclerAdapterMain(this,arraycontacts);
        view.setAdapter(adapter);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new Db_helper(this);
        view = findViewById(R.id.recyclerView);

        //read value from shared preference and store in layoutType
        SharedPreferences sharedPreferences = getSharedPreferences("layoutType",MODE_PRIVATE);
        layoutType = sharedPreferences.getInt("layoutType",1);

        FloatingActionButton new_contact= findViewById(R.id.new_contact);

        arraycontacts= new ArrayList<contact_model>();
        arraycontacts = myDB.select_all();
        createRecyclerView();

        //new contact button listener
        new_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, add.class);
                startActivity(intent);
            }
        });



//---------------------toolbar code----------------------------
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //actionBar.set(R.id.app_bar_nav_menu);

        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);

        //--------------------change layout code----------------------
        changeLayout_btn= findViewById(R.id.app_bar_grid_on);
        changeLayout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutType+=1;
                if(layoutType>2)
                    layoutType=1;
                if(layoutType == 1){
                    changeLayout_btn.setImageResource(R.drawable.ic_baseline_view_list_24);
                }
                else if (layoutType == 2){
                    changeLayout_btn.setImageResource(R.drawable.ic_baseline_grid_on_24);
                }

                createRecyclerView();
            }

        });
        //-------------------------side bar navigation menue code-----------------------

        drawer_layout= findViewById(R.id.drawerLayout);
        NavigationView navigationview = findViewById(R.id.sidebar_navigation);
//        toolbar= findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.nav_add) {
                    Intent add_intent=new Intent(MainActivity.this, add.class);
                    startActivity(add_intent);
                }
                else if(id==R.id.nav_update){
                    Intent add_intent=new Intent(MainActivity.this, update.class);
                    startActivity(add_intent);
                }
                else if(id==R.id.nav_search){
                    Intent add_intent=new Intent(MainActivity.this, search.class);
                    startActivity(add_intent);
                }
                else if(id==R.id.about_page){
                    Intent add_intent=new Intent(MainActivity.this, aboutScreen.class);
                    startActivity(add_intent);
                }
                else if(id==R.id.nav_Logout){
                    finish();
                }
                else
                    Toast.makeText(MainActivity.this, "Page not found!", Toast.LENGTH_SHORT).show();

                drawer_layout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        arraycontacts=myDB.select_all();
        createRecyclerView();
    }
    @Override
    public void onBackPressed() {
        drawer_layout=findViewById(R.id.drawerLayout);
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
            return;
        } else {
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();
        }
    }
//for toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.appbar_nav,menu);
        return super.onCreateOptionsMenu(menu);
    }
//for sidebar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      Integer item_id=item.getItemId();

       if(item_id==android.R.id.home)
                drawer_layout.openDrawer(GravityCompat.START);
        else if(item_id==R.id.sort_new_created) {
            arraycontacts = myDB.sort_new_created();
            createRecyclerView();
          Toast.makeText(this, "sort_new_click", Toast.LENGTH_SHORT).show();
            item.setChecked(true);
            //set all othes items unchecked



        }
      else if(item_id==R.id.sort_old_created) {
          Toast.makeText(this, "sort_old_click", Toast.LENGTH_SHORT).show();
            arraycontacts = myDB.sort_old_created();
            createRecyclerView();
          item.setChecked(true);


      }
      else if(item_id==R.id.sort_new_update) {
          Toast.makeText(this, "sort_new_update", Toast.LENGTH_SHORT).show();
          item.setChecked(true);
            arraycontacts = myDB.sort_new_created();
            createRecyclerView();

      }
      else if(item_id==R.id.sort_old_update) {
          Toast.makeText(this, "sort_old_update", Toast.LENGTH_SHORT).show();
            arraycontacts = myDB.sort_old_created();
            createRecyclerView();
          item.setChecked(true);

      }
        return super.onOptionsItemSelected(item);
    }

   @Override
    public void onPause() {
        //saved layoutType integer in shared preferences as a key value pair
        SharedPreferences sharedPreferences = getSharedPreferences("layoutType", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("layoutType", layoutType);
        editor.apply();
        super.onPause();
    }
}