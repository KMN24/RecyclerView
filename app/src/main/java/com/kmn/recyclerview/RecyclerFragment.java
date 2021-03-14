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

import com.kmn.recyclerview.mock.MockAdapter;
import com.kmn.recyclerview.mock.MockGenerator;

public class RecyclerFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private final MockAdapter mMockAdapter = new MockAdapter();

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // у нас будет список get Activity
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mMockAdapter);
        //Мы добавили adapter к recycler-у, но ещё не вызвали метод, который добавляет данные в adapter.  Для этого
        mMockAdapter.addData(MockGenerator.generator(20));
    }
}
