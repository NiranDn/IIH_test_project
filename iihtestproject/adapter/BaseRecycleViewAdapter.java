package com.example.iihtestproject.adapter;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public abstract void setDataToAdapter(Object data);
}
