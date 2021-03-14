package com.kmn.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /** Что мы будем делать в приложении ( каждый пункт 1 коммит)
     *
     * добавить фрагмент с recyclerView      --- done
     * дабавить адаптер, холдер и генератор заглушечных данных
     * дабавить обнавления данных и состояние ошибки
     * добавить загрузку данных с телефонной книги
     * дабавить обработку нажатий
     * добавить декораторы
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecyclerFragment.newInstance())
                    .commit();
        }


    }
}