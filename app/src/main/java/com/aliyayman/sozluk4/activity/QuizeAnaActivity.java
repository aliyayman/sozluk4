package com.aliyayman.sozluk4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliyayman.sozluk4.database.DatabaseCopyHelper;
import com.aliyayman.sozluk4.database.Veritabani;
import com.aliyayman.sozluk4.R;
import com.aliyayman.sozluk4.model.Uniteler;

import java.io.IOException;

public class QuizeAnaActivity extends AppCompatActivity {

    private Button buttonBasla;
    private Veritabani vt;
    private Uniteler unite;
    private Animation animation;
    private Animation animation1;
    private Animation animation2;
    private ImageView imageViewQuize;
    private TextView textViewQuize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize_ana);


        unite= (Uniteler) getIntent().getSerializableExtra("unite_nesne");
        int unitenin_id=unite.getUnite_id();
        Log.e("qize ana unite_id:",String.valueOf(unitenin_id));

        buttonBasla=findViewById(R.id.buttonBasla);
        textViewQuize=findViewById(R.id.textViewQuize);
        imageViewQuize=findViewById(R.id.imageViewQuize);
        vt=new Veritabani(this);
        veritabaniKopyala();

        animation= AnimationUtils.loadAnimation(QuizeAnaActivity.this,R.anim.scale);
        animation1= AnimationUtils.loadAnimation(QuizeAnaActivity.this,R.anim.translate_lefttoright);
        animation2= AnimationUtils.loadAnimation(QuizeAnaActivity.this,R.anim.translate_righttoleft);
        buttonBasla.setAnimation(animation);
        textViewQuize.setAnimation(animation1);
        imageViewQuize.setAnimation(animation2);

        buttonBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(QuizeAnaActivity.this,QuizeActivity.class);
                intent.putExtra("unite_nesnem",unite);

                startActivity(intent);
                finish();



            }
        });
    }
    public void veritabaniKopyala(){
        DatabaseCopyHelper helper= new DatabaseCopyHelper(this);
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();

    }
    }
