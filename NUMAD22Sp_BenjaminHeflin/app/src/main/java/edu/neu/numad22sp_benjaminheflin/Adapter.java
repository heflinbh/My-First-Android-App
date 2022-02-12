package edu.neu.numad22sp_benjaminheflin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder>{

    private final ArrayList<LinkItem> linkList;
    private LinkClickListener listener;

    public Adapter (ArrayList<LinkItem> linkList) {
        this.linkList = linkList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_items, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinkItem currentLink = linkList.get(position);

        holder.linkName.setText(currentLink.getLinkName());
        holder.linkAddress.setText(currentLink.getLinkAddress());
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    public void setOnLinkClickListener(LinkClickListener listener) {
        this.listener = listener;
    }
}
