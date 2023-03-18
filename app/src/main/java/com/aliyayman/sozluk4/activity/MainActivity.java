package com.aliyayman.sozluk4.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aliyayman.sozluk4.adapter.UniteAdapter;
import com.aliyayman.sozluk4.dao.UnitelerDao;
import com.aliyayman.sozluk4.database.DatabaseCopyHelper;
import com.aliyayman.sozluk4.database.Veritabani;
import com.aliyayman.sozluk4.model.Uniteler;
import com.aliyayman.sozluk4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private Veritabani vt;
    private ArrayList<Uniteler> unitelerList;
    private UniteAdapter adapter;
    private ArrayList<Uniteler> unitelers;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);

        toolbar.setBackgroundColor(Color.BLUE);
        rv=findViewById(R.id.rv_kelime);


        veritabaniKopyala();
        vt=new Veritabani(this);

        unitelerList=new UnitelerDao().tumUniteler(vt);

        adapter=new UniteAdapter(this,unitelerList);

        toolbar.setTitle("YDS Hazırlık");
        setSupportActionBar(toolbar);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.unite_ekle_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.action_uniteEkle);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                alertGoster();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void veritabaniKopyala(){

        DatabaseCopyHelper helper=new DatabaseCopyHelper(this);
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();
    }

    public void alertGoster(){

        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.alert_unite_tasarim,null);

        EditText unite_ad=view.findViewById(R.id.uniteAd);


        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setTitle("Kelime Tablonu Oluştur");
        ad.setView(view);
        ad.setPositiveButton("oluştur", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String uAd=unite_ad.getText().toString().trim();

                vt=new Veritabani(MainActivity.this);
                new UnitelerDao().uniteEkle(vt,uAd);
                unitelers=new UnitelerDao().tumUniteler(vt);
                adapter=new UniteAdapter(MainActivity.this,unitelers);
                rv.setAdapter(adapter);


            }
        });
        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.create().show();

    }

}

