package com.example.iihtestproject.interfaces;


import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class AdapterOnItemClickListenerEvent implements Serializable {

    private RecyclerView.Adapter adapter;
    private Object object;
    private int view;

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }


    @Override
    public String toString() {
        return "AdapterOnItemClickListenerEvent{" +
                "adapter=" + adapter +
                ", object=" + object +
                ", view=" + view +
                '}';
    }
}
