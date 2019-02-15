package com.rafalsky.dragandswipe.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rafalsky.dragandswipe.R;
import com.rafalsky.dragandswipe.model.Person;

import java.util.Collections;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonHolder>
        implements ItemTouchHelperAdapter{

    @Override
    public boolean onItemMove(int from, int to) {
        if (from < to){
            for (int i = from; i < to; i++){
                Collections.swap(data, i, i+1);
            }
        }else {
            for (int i = from; i > to; i--){
                Collections.swap(data, i, i-1);
            }
        }
        notifyItemMoved(from, to);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        Person removedItem = data.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar(removedItem, position);
    }

    public class PersonHolder extends RecyclerView.ViewHolder{

        private TextView nameInfo;
        private TextView ageInfo;

        public PersonHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.recycle_view_item, viewGroup, false));
            init();
        }

        private void init() {
            nameInfo = itemView.findViewById(R.id.name_info);
            ageInfo = itemView.findViewById(R.id.age_info);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Person person){
            nameInfo.setText(person.getName());
            ageInfo.setText(Integer.toString(person.getAge()));
        }
    }

    private List<Person> data;
    private Context context;
    private View layout;

    public PersonAdapter(Context context, View layout, List<Person> data){
        this.data = data;
        this.context = context;
        this.layout = layout;
    }

    private void showUndoSnackbar(Person removedItem, int position){
        Snackbar snackbar = Snackbar.make(layout, "Remove this item?", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Undo deleting at position " + position, v -> undoRemoving(removedItem, position));
        snackbar.show();
    }

    private void undoRemoving(Person removedItem, int position) {
        data.add(position, removedItem);
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("recycle", "onCreateViewHolder: i = " + i);
        return new PersonHolder(LayoutInflater.from(context), viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder personHolder, int i) {
        Log.d("recycle", "onBindViewHolder: i = " + i);
        personHolder.bind(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
