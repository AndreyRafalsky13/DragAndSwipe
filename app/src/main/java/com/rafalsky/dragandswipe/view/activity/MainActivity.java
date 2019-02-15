package com.rafalsky.dragandswipe.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.rafalsky.dragandswipe.R;
import com.rafalsky.dragandswipe.adapters.PersonAdapter;
import com.rafalsky.dragandswipe.callback.SwipeToDeleteCallback;
import com.rafalsky.dragandswipe.model.PeopleStorage;
import com.rafalsky.dragandswipe.model.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateRecycleView();
    }

    private void updateRecycleView() {
        List<Person> people = PeopleStorage.get().getPeople();
        if (adapter == null){
            adapter = new PersonAdapter(this, findViewById(R.id.coordinator_layout), people);
            ItemTouchHelper touchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this, adapter));
            recyclerView.setAdapter(adapter);
            touchHelper.attachToRecyclerView(recyclerView);
        }

    }
}
