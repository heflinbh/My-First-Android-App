package edu.neu.numad22sp_benjaminheflin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LinkCollectorActivity extends AppCompatActivity {

    private ArrayList<LinkItem> linkList = new ArrayList<>();

    private RecyclerView recyclerView;
    private Adapter adapter;
    private RecyclerView.LayoutManager layoutManger;
    private FloatingActionButton floatButton;

    private final String STRING_SEPARATOR = ":::";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        init(savedInstanceState);

        floatButton = findViewById(R.id.floatingActionButton);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 0;
                addLink(pos);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
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

    private void init(Bundle savedInstanceState) {

        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void addLink(int position) {
        linkList.add(position, new LinkItem("New Name", "New Address"));
        Toast.makeText(LinkCollectorActivity.this, "Add an item", Toast.LENGTH_SHORT).show();

        adapter.notifyItemInserted(position);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        int i = 0;
        for (LinkItem link : linkList) {
            outState.putString(String.valueOf(i), link.getLinkName() + STRING_SEPARATOR + link.getLinkAddress());
            i += 1;
        }
        super.onSaveInstanceState(outState);
    }


    private void initialItemData(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            if (linkList == null || linkList.size() == 0) {

                for (int i = 0; i < savedInstanceState.size(); i++) {

                    String[] values = savedInstanceState.getString(String.valueOf(i)).split(STRING_SEPARATOR);

                    String linkName = values[0];
                    String linkAddress = values[1];

                    LinkItem linkItem = new LinkItem(linkName, linkAddress);
                    linkList.add(linkItem);
                }
            }
        }
    }

    private void createRecyclerView() {


        layoutManger = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        adapter = new Adapter(linkList);
        LinkItem linkItem = new LinkItem() {
            @Override
            public void onLinkClick(int position) {
                //attributions bond to the item has been changed
                linkList.get(position).onLinkClick(position);

                adapter.notifyItemChanged(position);
            }
        };
        adapter.setOnLinkClickListener(linkItem);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManger);


    }
}