package edu.neu.numad22sp_benjaminheflin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private RecyclerView.LayoutManager layoutManager;

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

        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(LinkCollectorActivity.this, "Delete an item", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition();
                linkList.remove(position);
                adapter.notifyItemRemoved(position);

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
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

                Snackbar snackbar = Snackbar.make(recyclerView, "Link successfully added.", Snackbar.LENGTH_SHORT);
                snackbar.show();

            }

            else if (resultCode == RESULT_CANCELED) {
                Snackbar snackbar = Snackbar.make(recyclerView, "Link request canceled.", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }
    }

    private void setAdapter() {
        adapter = new Adapter(linkList);
        layoutManager = new LinearLayoutManager(this);

        LinkClickListener linkClickListener = new LinkClickListener() {
            @Override
            public void onLinkClick(int position) {
                linkList.get(position).onLinkClick(position);
                adapter.notifyItemChanged(position);
            }
        };

        adapter.setOnLinkClickListener(linkClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void addLink() {
        Intent newLinkActivityIntent = new Intent(getApplicationContext(), NewLinkActivity.class);
        startActivityForResult(newLinkActivityIntent, 1);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        int i = 0;
        for (LinkItem link : linkList) {
            outState.putString("Name" + i, link.getLinkName());
            outState.putString("Address" + i, link.getLinkAddress());
            i += 1;
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeData(Bundle savedInstanceState) {
        if (savedInstanceState != null && (linkList == null || linkList.size() == 0)) {

            int i = 0;
            while (savedInstanceState.getString("Name" + i) != null &&
                    savedInstanceState.getString("Address" + i) != null) {

                String linkName = savedInstanceState.getString("Name" + i);
                String linkAddress = savedInstanceState.getString("Address" + i);
                linkList.add(new LinkItem(linkName, linkAddress));

                i += 1;
            }
        }
    }
}