package com.example.projekt2;

import static android.widget.Toast.makeText;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    private EditText lKostek;
    private Button btnLosuj;
    private GridLayout grid;
    private TextView wyniki;
    private Button btnRos;
    private Button btnMal;

    private List<Integer> wynikiKostkek = new ArrayList<>();
    private Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lKostek = findViewById(R.id.editTextLiczbaKostek);
        btnLosuj = findViewById(R.id.buttonLosuj);
        grid = findViewById(R.id.gridKostki);
        wyniki = findViewById(R.id.textViewWyniki);
        btnRos = findViewById(R.id.buttonRosnaco);
        btnMal = findViewById(R.id.buttonMalejaco);

        btnLosuj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                losujKostki();
            }
        });

        btnRos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (wynikiKostkek.isEmpty()){
                    makeText(MainActivity.this, "Najpierw wylosuj kostki", Toast.LENGTH_SHORT).show();
                }else{
                    sortujRosnaco();
                    wyswietlWyniki();
                }
            }
        });

        btnMal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(wynikiKostkek.isEmpty()){
                    makeText(MainActivity.this, "Najpierw wylosuj kostki", Toast.LENGTH_SHORT).show();
                }else{
                    sortujMalejaco();
                    wyswietlWyniki();
                }
            }
        });
    }

    private void losujKostki(){
        String lKostekStr = lKostek.getText().toString();
        if(lKostekStr.isEmpty()){
            makeText(this, "Wpisz liczbę kostek", Toast.LENGTH_SHORT).show();
            return;
        }

        int liczba;
        try{
            liczba = Integer.parseInt(lKostekStr);
        }catch(NumberFormatException e){
            makeText(this, "Wpisz poprawną liczbę całkowitą", Toast.LENGTH_SHORT).show();
            return;
        }

        if(liczba < 1 || liczba > 100){
            makeText(this, "Liczba kostek musi być z zakresu od 1 do 100", Toast.LENGTH_LONG).show();
            return;
        }

        wynikiKostkek.clear();
        for(int i = 0; i<liczba; i++){
            int wylosowana = random.nextInt(6) + 1; 
            wynikiKostkek.add(wylosowana);
        }
        wyswietlWyniki();
    }

    private void wyswietlWyniki(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<wynikiKostkek.size(); i++){
            sb.append(wynikiKostkek.get(i));
            if(i < wynikiKostkek.size() - 1){
                sb.append(", ");
            }
        }
        wyniki.setText(sb.toString());

        grid.removeAllViews();
        for(int wynik : wynikiKostkek){
            TextView tvKostka = new TextView(this);
            tvKostka.setText(String.valueOf(wynik));
            tvKostka.setTextSize(22f);
            tvKostka.setTypeface(null, android.graphics.Typeface.BOLD);
            tvKostka.setGravity(Gravity.CENTER);
            tvKostka.setBackgroundColor(getResources().getColor(android.R.color.darker_gray, null));
            tvKostka.setTextColor(getResources().getColor(android.R.color.white, null));

            int sizeInPx = (int)(50 * getResources().getDisplayMetrics().density);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = sizeInPx;
            params.height = sizeInPx;
            params.setMargins(8, 8, 8, 8);
            tvKostka.setLayoutParams(params);

            grid.addView(tvKostka);
        }
    }

    private void sortujRosnaco(){
        int n = wynikiKostkek.size();
        for(int i = 0; i < n - 1; i++){
            for(int j = 0; j < n - i - 1; j++){
                if(wynikiKostkek.get(j) > wynikiKostkek.get(j + 1)){
                    int temp = wynikiKostkek.get(j);
                    wynikiKostkek.set(j, wynikiKostkek.get(j + 1));
                    wynikiKostkek.set(j + 1, temp);
                }
            }
        }
    }

    private void sortujMalejaco(){
        int n = wynikiKostkek.size();
        for(int i = 0; i < n - 1; i++){
            for(int j = 0; j < n - i - 1; j++){
                if(wynikiKostkek.get(j) < wynikiKostkek.get(j + 1)){
                    int temp = wynikiKostkek.get(j);
                    wynikiKostkek.set(j, wynikiKostkek.get(j + 1));
                    wynikiKostkek.set(j + 1, temp);
                }
            }
        }
    }
}