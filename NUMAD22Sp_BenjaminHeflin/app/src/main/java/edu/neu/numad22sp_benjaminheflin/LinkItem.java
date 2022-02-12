package edu.neu.numad22sp_benjaminheflin;

import android.widget.Toast;

public class LinkItem {

    private String linkName;
    private String linkAddress;

    public LinkItem(String linkName, String linkAddress) {
        this.linkName = linkName;
        this.linkAddress = linkAddress;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    protected void onItemClick(int position) {
    }
}
