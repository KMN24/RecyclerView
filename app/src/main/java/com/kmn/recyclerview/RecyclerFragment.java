package com.kmn.recyclerview;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kmn.recyclerview.mock.MockAdapter;
import com.kmn.recyclerview.mock.MockGenerator;

import java.util.Random;

public class RecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecyclerView;
   // private final MockAdapter mMockAdapter = new MockAdapter();
    private final  ContactsAdapter mContactsAdapter = new ContactsAdapter();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mErrorView;
    private Random mRandom = new Random();

    private ContactsAdapter.OnItemClickListener mListener;


    public static RecyclerFragment newInstance() {
        return new RecyclerFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ContactsAdapter.OnItemClickListener){
            mListener = (ContactsAdapter.OnItemClickListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.recycler);
        mSwipeRefreshLayout = view.findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mErrorView = view.findViewById(R.id.error_view);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // у нас будет список get Activity
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mContactsAdapter);
        //Мы добавили adapter к recycler-у, но ещё не вызвали метод, который добавляет данные в adapter.  Для этого
        //mMockAdapter.addData(MockGenerator.generator(20));

        mContactsAdapter.setListener(mListener);
    }

    @Override
    public void onRefresh() {
//        mSwipeRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                int count = mRandom.nextInt(4);
//
//                if(count == 0){
//                    showError();
//                }else {
//                    showData(count);
//                }
//
//                if(mSwipeRefreshLayout.isRefreshing()){ // крутится прямо сейчас
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            }
//        }, 2000);

        getLoaderManager().restartLoader(0,null, this);

    }

//    private void showData(int count) {
//        mMockAdapter.addData(MockGenerator.generator(count), true); // true - будем только обновлять данные
//
//        mErrorView.setVisibility(View.GONE);
//        mRecyclerView.setVisibility(View.VISIBLE);
//    }
//
//    private void showError() {
//        mErrorView.setVisibility(View.VISIBLE);
//        mRecyclerView.setVisibility(View.GONE);
//    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        /*
        И так как это джэнерик, то передаем еще и курсор, в качестве джэнерик параметра.
        К курсору стоит относиться как к таблице, то есть у нее есть строки, столбцы и она обрабатывает запросы.
        Комплимент methods, create. onLoader finished и onloader reset. Хорошо. В on_create_loader мы создаем loader
        и передаем его в систему. Cистема уже сама его запустит, обработает запрос и передаст нам его в on_load_finished.
         Пишем! Return. И существует так называемый курсор loader, то есть loader который в себе хранит курсор.
         Курсор loader тоже из версии поддержки. Наверное надо дописать new в начале new. new_cursor_loader и
         передаем в конструктор контекст контекст это наши активити, get_activity запятая. Content_ura.
         То есть строка, по которой можно обратиться к контент провайдеру. Ну, своего рода идентификатор.
         Это константа в нашем случае. contex contract context content_ura запятая. Дальше нужно передать selection.
         То есть, если курсор это таблица, то projection это столбцы этой таблицы, которая нас интересует, которую нам
         можно получитью. New string и нам нужно два столбца. Это id и имя контакта - тоже константы.
         contex id запятая, contex_display_name запятая. Дальше передаем selection. Selection это условие,
         по которому мы определяем, какие записи в таблице нас интересуют, нас удовлетворяют. Если передать null,
         то означает, что мы просто берем все таблицы не передавая никакое условие. no_null на самом деле selection
         и selection_аrx связанны, так как если selection более менее серьёзный,
         */
        return new CursorLoader (getActivity(),
                ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                null,
                null,
                ContactsContract.Contacts._ID
        );

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mContactsAdapter.swapCursor(data);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }
}
