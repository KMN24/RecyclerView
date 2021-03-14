package com.kmn.recyclerview;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.RecyclerView;

import com.kmn.recyclerview.mock.Mock;
import com.kmn.recyclerview.mock.MockHolder;

public class ContactsAdapter extends RecyclerView.Adapter<MockHolder> {

    private Cursor mCursor;

    @NonNull
    @Override
    public MockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // код из on_create_new_holder из мок адаптера можно скопировать, так как мы создаем тот же самый тоже самый view_holder.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_mock, parent, false);
        return new MockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MockHolder holder, int position) {
        /*
        То есть перемещаем курсор. Представьте его как действительно реальный курсор, который перемещается по таблице.
        Вызываем метод move_to_position с нашим position который мы поручили в аргументах к методу on_byint_view_holder
         и move_to_position возвращает boolean. Он защищает true, если курсор успешно переместился на эту позицию.
         */
        if(mCursor.moveToPosition(position)){
            /*
            Ну, давайте вытащим строку и id из курсора. String_name равен m_cursor get_string и метод
            get_string на самом деле на вход требует индекс колонки из которой нужно выдернуть данные,
            но я то не знаю индекса колонки. Но, к счастью курсор также провайдит нам метод get_colon_index,
            который возвращает индекс колонки на основании названия колонки, которой мы ему передадим и название
            колонки я знаю. Contacts точка display_name точка с запятой.
             */
            String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            // анологично делаем для id
            int id = mCursor.getInt(mCursor.getColumnIndex(ContactsContract.Contacts._ID));

            holder.bind(new Mock(name, id));
            /*
            Дальше, что нам нужно сделать это попросить разрешения у пользователя заглянуть его в его телефонную книгу.
            Это на самом деле немножко копотливая ситуация. Пишем miss_permition_red_contax в манифесте и если мы сейчас
            запустим наш проект, то он обвалится с ошибкой. Почему? Потому что прямо сейчас у меня эмулятор 26-го уровня и
            это означает, что на уровне выше 6 0 т.е. 23-го уровня уровня мне нужно запросить runtime_permition для контактов,
            так как запрос контактов это чувствительная, опасная, компрометирующая информация для пользователя, я же лезу
            в его личную жизнь получается запрашивая доступ к его телефонной книге. И так как эмулятор у нас 26 уровня,
            то по идее он должен показать всплывающее окно с просьбой дать мне доступ к контактам, но сейчас,
            чтобы не усложнять проект, я этого делать не буду. Я воспользуюсь лайфхаком. Я передаю настройки устройства.
            Перейду к приложениям. Открою наше приложение, щелкну по permition и сам вручную включу разрешение и запущу
            приложение теперь.
             */
        }
    }

    @Override
    public int getItemCount() {
        return mCursor!= null ? mCursor.getCount() : 0;
    }

    public void swapCursor(Cursor cursor){
        /*
        давайте теперь добавим метод, который добавляет курсор в этот адаптер и традиционно методы
        которые добавляет курсор называется по принципу swap курсор. Это во первых дань уважения к курсору
        адаптера из list_view, который работает с list_view. То есть это адаптер, который просто работает с курсором
        - системный адаптер. И во вторых смотрите, что мы делаем в этом методе. Если определенный курсор не null и
        переданный курсор не равен нашему текущему курсору, то если текущий курсор не null, то мы закрываем курсор и
        текущий курсор присваиваем новому курсору. Также задаем notify_data_set_change. То есть мы как бы поменяли два
        курсор местами. Дальше что? Перейдем в ресайклер фрагмент и в on_load_finished
         */

        if(cursor != null && cursor != mCursor){
            if (mCursor != null) mCursor.close();
            mCursor = cursor;
            notifyDataSetChanged();
        }

    }

}
