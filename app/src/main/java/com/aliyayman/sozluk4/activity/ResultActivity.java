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
import android.widget.Toast;

import com.aliyayman.sozluk4.R;
import com.aliyayman.sozluk4.model.Uniteler;

public class ResultActivity extends AppCompatActivity {
    private Button buttonRes;
    private TextView textViewSonuc;
    private TextView textViewYuzde, textViewMessage;
    private ImageView imageViewSmile;
    private int dogruSayac;
    private Uniteler unite;
    private Animation animation;
    private Animation animation1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewSonuc = findViewById(R.id.textViewSonuc);
        textViewYuzde = findViewById(R.id.textViewYuzde);
        buttonRes = findViewById(R.id.buttonRes);
        textViewMessage = findViewById(R.id.textViewMessage);


        unite = (Uniteler) getIntent().getSerializableExtra("unite");
        dogruSayac = getIntent().getIntExtra("dogruSayac", 0);
        textViewSonuc.setText(dogruSayac + " DOĞRU " + (10 - dogruSayac) + " YANLIŞ");
        textViewYuzde.setText("%" + (dogruSayac * 10) + " BAŞARI");

        animation= AnimationUtils.loadAnimation(ResultActivity.this,R.anim.scale);
        animation1= AnimationUtils.loadAnimation(ResultActivity.this,R.anim.translate_uptodown);
        buttonRes.setAnimation(animation);
        textViewMessage.setAnimation(animation1);
        textViewSonuc.setAnimation(animation1);
        textViewYuzde.setAnimation(animation1);

        buttonRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(ResultActivity.this, QuizeActivity.class);
                intent.putExtra("unite_nesnem", unite);
                startActivity(intent);
                finish();
            }
        });

        if (dogruSayac > 9) {
            textViewMessage.setText("You're genius :)");
            //imageViewSmile.setImageResource(R.drawable.smile);
            return;

        }

        if (dogruSayac >= 8) {
            textViewMessage.setText("You're the best :)");
           // imageViewSmile.setImageResource(R.drawable.smile);

            return;
        }

            if (dogruSayac >= 6) {
                textViewMessage.setText("You're good.");
             //   imageViewSmile.setImageResource(R.drawable.smile);

                return;

            }
                if (dogruSayac <= 5) {
                    textViewMessage.setText("You can try again.");
                   // imageViewSmile.setImageResource(R.drawable.sad);
                    return;

                }


            }
        }
