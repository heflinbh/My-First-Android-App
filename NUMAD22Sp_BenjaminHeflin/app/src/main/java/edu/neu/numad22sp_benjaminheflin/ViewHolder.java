package edu.neu.numad22sp_benjaminheflin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView linkName;
    public TextView linkAddress;

    public ViewHolder(final View view) {
        super(view);
        this.linkName = view.findViewById(R.id.link_name);
        this.linkAddress = view.findViewById(R.id.link_address);
    }
}
