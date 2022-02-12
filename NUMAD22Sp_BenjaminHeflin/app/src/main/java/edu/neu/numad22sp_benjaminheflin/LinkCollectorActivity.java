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
import com.google.android.material.snackbar.Snackbar;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String linkName = data.getExtras().getString("Link Name");
                String linkAddress = data.getExtras().getString("Link Address");

                linkList.add(new LinkItem(linkName, linkAddress));
                adapter.notifyItemChanged(0);

                Snackbar snackbar = Snackbar.make(recyclerView, "Success!", Snackbar.LENGTH_SHORT);
                snackbar.show();

            }

            else if (resultCode == RESULT_CANCELED) {
                String problems = data.getExtras().getString("Problems");
                if (problems.equals("Name")) {
                    // ADD WARNING FOR NAME
                    Snackbar snackbar = Snackbar.make(recyclerView, "Bad Name!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else if (problems.equals("Address")) {
                    // ADD WARNING FOR ADDRESS
                    Snackbar snackbar = Snackbar.make(recyclerView, "Bad Address!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else if (problems.equals("None")) {
                    // CONFIRM CANCELLATION
                    Snackbar snackbar = Snackbar.make(recyclerView, "We Canceled!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        }

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
        startActivityForResult(newLinkActivityIntent, 1);
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