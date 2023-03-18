package com.aliyayman.sozluk4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyayman.sozluk4.dao.KelimelerDao;
import com.aliyayman.sozluk4.database.Veritabani;
import com.aliyayman.sozluk4.model.Kelimeler;
import com.aliyayman.sozluk4.R;
import com.aliyayman.sozluk4.model.Uniteler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Handler;

public class QuizeActivity extends AppCompatActivity {
    private Button buttonA, buttonB, buttonC, buttonD;
    private TextView textViewsoru, textViewdogru, textViewyanlis, textViewkelime,textViewAnswer;
    private ArrayList<Kelimeler> sorularList;
    private ArrayList<Kelimeler> yanlisListe;
    private Kelimeler dogruSoru;
    private Veritabani vt;
    private int soruSayac = 0;
    private int yanlisSayac = 0;
    private int dogruSayac = 0;
    private HashSet<Kelimeler> seceneklerKaristirmaList = new HashSet<>();
    private ArrayList<Kelimeler> seceneklerList = new ArrayList<>();
    private Uniteler unite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize);


        textViewdogru = findViewById(R.id.textViewdogru);
        textViewkelime = findViewById(R.id.textViewkelime);
        textViewsoru = findViewById(R.id.textViewsoru);
        textViewyanlis = findViewById(R.id.textViewyanlis);
        textViewAnswer=findViewById(R.id.textViewAnswer);
        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);

        unite= (Uniteler) getIntent().getSerializableExtra("unite_nesnem");
        Log.e("unite_id:",String.valueOf(unite.getUnite_id()));
        vt = new Veritabani(this);
        sorularList = new KelimelerDao().rastgele10Getir(vt,unite.getUnite_id());



        soruYukle();


        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dogruKontrol(buttonA);
                sayacKontrol();

            }
        });
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dogruKontrol(buttonB);
                sayacKontrol();

            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dogruKontrol(buttonC);
                sayacKontrol();


            }
        });



        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogruKontrol(buttonD);
                sayacKontrol();
            }
        });



            }




    public void soruYukle () {
        textViewsoru.setText((soruSayac + 1) + ". SORU");
        textViewdogru.setText("Doğru:" + dogruSayac);
        textViewyanlis.setText("Yanlış:" + yanlisSayac);


        dogruSoru = sorularList.get(soruSayac);
        yanlisListe = new KelimelerDao().rastgele3YanlisGetir(vt, dogruSoru.getKelime_id());


        textViewkelime.setText(dogruSoru.getIng());
        seceneklerKaristirmaList.clear();
        seceneklerKaristirmaList.add(dogruSoru);
        seceneklerKaristirmaList.add(yanlisListe.get(0));
        seceneklerKaristirmaList.add(yanlisListe.get(1));
        seceneklerKaristirmaList.add(yanlisListe.get(2));

        seceneklerList.clear();
        for (Kelimeler k : seceneklerKaristirmaList) {
            seceneklerList.add(k);
        }
        buttonA.setText(seceneklerList.get(0).getTc());
        buttonB.setText(seceneklerList.get(1).getTc());
        buttonC.setText(seceneklerList.get(2).getTc());
        buttonD.setText(seceneklerList.get(3).getTc());


    }
    public void dogruKontrol(Button button){
        String butonYazi=button.getText().toString();
        String dogruCevap=dogruSoru.getTc();
        if(butonYazi.equals(dogruCevap)){
            dogruSayac++;
        }
        else{

            textViewAnswer.setText("Doğru Cevap: "+dogruCevap);
           // Toast.makeText(getApplicationContext(),"CEVAP:"+dogruCevap,Toast.LENGTH_SHORT).show();
            yanlisSayac++;
        }



    }


    public void sayacKontrol(){

       soruSayac++;
        if(soruSayac!=10){
            soruYukle();

        }
        else{

            Intent intent= new Intent(QuizeActivity.this,ResultActivity.class);
            intent.putExtra("dogruSayac",dogruSayac);
            intent.putExtra("unite",unite);
            startActivity(intent);
            finish();




        }
    }

}
