package com.kmn.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements ContactsAdapter.OnItemClickListener{

    /** Что мы будем делать в приложении ( каждый пункт 1 коммит)
     *
     * добавить фрагмент с recyclerView      --- done
     * дабавить адаптер, холдер и генератор заглушечных данных      --- done
     * дабавить обнавления данных и состояние ошибки                --- done
     * добавить загрузку данных с телефонной книги                  --- done
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

    @Override
    public void onItemClick(String id) {
        /*
Мы создали контент провайдер, обращаемся к нему, получаем курсор, который будет хранить один столбец,
который называется number, то есть номер телефона, в котором мы сравниваем ID, которые находятся в этом столбце с ID,
которое мы ему передали и сравниваем тип номер телефона с тайп мобайл, то есть с мобильным номером. Звучит сложно,
выглядит еще сложнее, но я думаю вы разберетесь. Да, кстати, куери возвращает нам курсор, давайте загоним его в переменную.
         */
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
                        + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                new String[]{id, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                null);

        /*
        Мы передали данные из холдера в activity путём реализации интерфейса, который определен в адапторе и
        передачи Listenerа из activity в фрагмент, из фрагмента в адаптер, из адаптера в холдер. Прямая связь и
        получается вызов этого интерфейса приводит к тому, что срабатывает код в майн activity, где этот интерфейс реализован,
         это получается обратная связь. Дальше, в методе onItemClick реализован интерфейса, мы обращаемся к контент провайдеру,
          проводим запрос в базу данных, ищем номер телефона для контакта, айди которого мы передали этот метод, следим,
          чтобы номер телефона был мобильный, дальше проверяем, что полученный нами курсор, он не пустой и в нём есть данные,
          выдергиваю оттуда номер телефона, создаем не явный интент для совершения звонков, указываем в нём данные номер телефона,
           по которому мы хотим позвонить и запускаем activity. Это очень много.
         */
        if(cursor != null && cursor.moveToFirst()){
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();

            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+ number)));

        }

        Toast.makeText(this, "clicked " + id, Toast.LENGTH_SHORT).show();
        /*
        Дальше, в чём проблема этого кода? Ну, несмотря наесли не акцентироваться на том, что я не проверяю пермищн в рантайме,
        проблема в том, что я обращаюсь к контент провайдеру, делаю запрос в базу, проверяю, выдергиваю данные, закрываю курсор,
        вот все, все, все это, я делаю это в мейн трейде. Это на самом деле неправильно. По хорошему я должен был обратиться
        к контент провайдеру в фоновом потоке, провести все эти манипуляции, и когда всё будет готово, просто возвращать
         номер телефона в главный поток, с которого я уже могу позвонить, и это всё из-за того, что я отказался использовать лоудеры.
          В принципе это можно сейчас исправить, создать сервис екзикютер, например, выполнить в нём эту таску, получить фьючер,
          из фьючер выдернуть строку с номером и передать её в старт activity. Но я не буду этого делать, потому что это
          будет одним из ваших тестовых заданий. Так, обработку нажатий мы тоже реализовали, в следующем уроке
          уроке мы поговорим о декораторах. Что это такое, с чем его едят и вообще, зачем это нужно?
         */
    }
}