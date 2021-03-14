package com.kmn.recyclerview.mock;

// Adapter - нужен, чтобы снабжать recyclerView данными. Он своего рода является посредником
// между объектами, о которых recyclerView ничего не знают, и непосредственно самим recyclerView.

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kmn.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class MockAdapter extends RecyclerView.Adapter<MockHolder> {

    private final List<Mock> mMockList = new ArrayList<>();

    @NonNull
    @Override
    public MockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflater, как вы уже знаете, нужен для того, чтобы из XML разметки сделать view.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_mock, parent, false);
        return new MockHolder(view);
    }

    @Override
    public void onBindViewHolder(MockHolder holder, int position) {
        /*
        OnBindViewHolder на вход получает holder, либо созданный, либо переиспользованный и position,
        то есть позицию этого элемента из адаптера, позицию объекта из адаптера.
         */
        holder.bind(mMockList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMockList.size(); // returning size of MockList
    }

    public void addData(List<Mock> mocks){
        mMockList.addAll(mocks);
        // Дальше, для того, чтобы система знала, что вообще-то у меня адаптер обновился, неплохо было бы вызвать notify Data set Changed.
        notifyDataSetChanged();
    }

}
