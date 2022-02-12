package edu.neu.numad22sp_benjaminheflin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class LinkCollectorActivity extends AppCompatActivity {

    private ArrayList<LinkItem> linkList;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private FloatingActionButton floatingActionButton;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        recyclerView = findViewById(R.id.recycler_view);
        linkList = new ArrayList<>();

        setAdapter();
        initializeData(savedInstanceState);

        floatingActionButton = findViewById(R.id.FloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLink();
            }
        });
    }

    private void setAdapter() {
        adapter = new Adapter(linkList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void addLink() {
        Intent newLinkActivityIntent = new Intent(getApplicationContext(), NewLinkActivity.class);
        startActivity(newLinkActivityIntent);

        linkList.add(new LinkItem("Link 4", "Address 4"));
        adapter.notifyItemChanged(0);
    }

    // Handling Orientation Changes on Android
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {


        int size = linkList == null ? 0 : linkList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        // Need to generate unique key for each item
        // This is only a possible way to do, please find your own way to generate the key
        for (int i = 0; i < size; i++) {

            outState.putString(KEY_OF_INSTANCE + i + "1", linkList.get(i).getLinkName());
            outState.putString(KEY_OF_INSTANCE + i + "2", linkList.get(i).getLinkAddress());
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeData(Bundle savedInstanceState) {
        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (linkList == null || linkList.size() == 0) {

                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {

                    String linkName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    String linkAddress = savedInstanceState.getString(KEY_OF_INSTANCE + i + "2");

                    LinkItem link = new LinkItem(linkName, linkAddress);

                    linkList.add(link);
                }
            }
        }
    }
}