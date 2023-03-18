package com.aliyayman.sozluk4.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
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

import com.aliyayman.sozluk4.adapter.KelimeAdapter;
import com.aliyayman.sozluk4.dao.KelimelerDao;
import com.aliyayman.sozluk4.database.Veritabani;
import com.aliyayman.sozluk4.model.Kelimeler;
import com.aliyayman.sozluk4.model.Uniteler;
import com.aliyayman.sozluk4.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class KelimeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar_kelime;
    private RecyclerView rv_kelime;
    private Button buttonQuize;
    private Button buttonTest;
    private ArrayList<Kelimeler> kelimelerList;
    private KelimeAdapter adapter;
    private Uniteler unite;
    private Veritabani vt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime);


        toolbar_kelime=findViewById(R.id.toolbar_kelime);
        rv_kelime=findViewById(R.id.rv_kelime);
        buttonQuize=findViewById(R.id.buttonQuize);
        buttonTest = findViewById(R.id.buttonTest);

        unite= (Uniteler) getIntent().getSerializableExtra("nesne");
        Log.e("kelime activity id:",String.valueOf(unite.getUnite_id()));
        vt=new Veritabani(this);

        kelimelerList=new KelimelerDao().tumKelimelerUniteId(vt,unite.getUnite_id());


       adapter=new KelimeAdapter(this,kelimelerList,vt);

        toolbar_kelime.setTitle(unite.getUnite_ad());
        toolbar_kelime.setBackgroundColor(Color.BLUE);
        setSupportActionBar(toolbar_kelime);
        rv_kelime.setHasFixedSize(true);
        rv_kelime.setLayoutManager(new LinearLayoutManager(this));
        rv_kelime.setAdapter(adapter);

        buttonQuize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KelimeActivity.this,QuizeAnaActivity.class);
                intent.putExtra("unite_nesne",unite);
                startActivity(intent);
                finish();
            }
        });

        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(KelimeActivity.this,QuizeAnaActivity.class);
                intent.putExtra("unite_nesne",unite);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.action_ara);
        MenuItem menuItem2=menu.findItem(R.id.action_ekle);


        menuItem2.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                alertGoster();
                return false;
            }
        });
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("kelime:",newText);
                kelimelerList=new KelimelerDao().kelimeAra(vt,unite.getUnite_id(),newText);
                adapter=new KelimeAdapter(KelimeActivity.this,kelimelerList,vt);
                rv_kelime.setAdapter(adapter);


                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    public void alertGoster(){

        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.alert_tasarim,null);

        EditText editIng=view.findViewById(R.id.editIng);
        EditText editTc=view.findViewById(R.id.editTc);

        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setTitle("Kelime Ekle");
        ad.setView(view);
        ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ing_kelime=editIng.getText().toString().trim();
                String tc_kelime=editTc.getText().toString().trim();

                Log.e("kelime eklendi",ing_kelime);

                vt=new Veritabani(KelimeActivity.this);
                new KelimelerDao().kelimeEkle(vt,ing_kelime,tc_kelime,unite.getUnite_id());

                kelimelerList=new KelimelerDao().tumKelimelerUniteId(vt,unite.getUnite_id());
                adapter=new KelimeAdapter(KelimeActivity.this,kelimelerList,vt);
                rv_kelime.setAdapter(adapter);


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