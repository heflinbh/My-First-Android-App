package edu.neu.numad22sp_benjaminheflin;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView itemIcon;
    public TextView linkName;
    public TextView linkAddress;

    public ViewHolder(View itemView, final LinkItem linkItem) {
        super(itemView);
        this.linkName = itemView.findViewById(R.id.link_name);
        this.linkAddress = itemView.findViewById(R.id.link_address);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linkItem != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        linkItem.onLinkClick(position);
                    }
                }
            }
        });
    }
}
