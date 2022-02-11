package edu.neu.numad22sp_benjaminheflin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder>{

    private final ArrayList<LinkItem> linkList;
    private LinkItem linkItem;

    public Adapter (ArrayList<LinkItem> linkList) {
        this.linkList = linkList;
    }

    public void setOnLinkClickListener(LinkItem linkItem) {
        this.linkItem = linkItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_item, parent, false);
        return new ViewHolder(view, linkItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinkItem currentItem = linkList.get(position);

        holder.linkName.setText(currentItem.getLinkName());
        holder.linkAddress.setText(currentItem.getLinkAddress());

    }

    @Override
    public int getItemCount() {
        return this.linkList.size();
    }

}
