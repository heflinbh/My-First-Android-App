package edu.neu.numad22sp_benjaminheflin;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView linkName;
    public TextView linkAddress;

    public ViewHolder(View view, final LinkClickListener listener) {
        super(view);
        this.linkName = view.findViewById(R.id.link_name);
        this.linkAddress = view.findViewById(R.id.link_address);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        listener.onLinkClick(position);
                    }
                }
            }
        });
    }
}
