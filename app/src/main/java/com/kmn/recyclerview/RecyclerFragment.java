package com.kmn.recyclerview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kmn.recyclerview.mock.MockAdapter;
import com.kmn.recyclerview.mock.MockGenerator;

import java.util.Random;

public class RecyclerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private final MockAdapter mMockAdapter = new MockAdapter();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mErrorView;
    private Random mRandom = new Random();

    public static RecyclerFragment newInstance() {
        return new RecyclerFragment();
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
        mRecyclerView.setAdapter(mMockAdapter);
        //Мы добавили adapter к recycler-у, но ещё не вызвали метод, который добавляет данные в adapter.  Для этого
        //mMockAdapter.addData(MockGenerator.generator(20));
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                int count = mRandom.nextInt(4);

                if(count == 0){
                    showError();
                }else {
                    showData(count);
                }

                if(mSwipeRefreshLayout.isRefreshing()){ // крутится прямо сейчас
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 2000);

    }

    private void showData(int count) {
        mMockAdapter.addData(MockGenerator.generator(count), true); // true - будем только обновлять данные

        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showError() {
        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }
}
