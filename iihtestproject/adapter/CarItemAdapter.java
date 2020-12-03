package com.example.iihtestproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.iihtestproject.R;
import com.example.iihtestproject.interfaces.AdapterOnItemClickListener;
import com.example.iihtestproject.interfaces.AdapterOnItemClickListenerEvent;
import com.example.iihtestproject.object.Car;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CarItemAdapter extends BaseRecycleViewAdapter {



    private Context mContext;
    private ArrayList<Car> mCarList;
    private AdapterOnItemClickListener clickListener;

    public CarItemAdapter(Context context) {
        super();
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.car_list_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        final Car car = mCarList.get(position);
        viewHolder.newsItem = car;

        viewHolder.tvName.setText(car.getName());
        viewHolder.tvAddress.setText(car.getAddress());
        viewHolder.tvEngineType.setText(car.getEngineType());
        viewHolder.tvFuel.setText(car.getFuel());
    }

    @Override
    public int getItemCount() {
        return mCarList != null ? mCarList.size() : 0;
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void setDataToAdapter(Object data) {
        this.mCarList = (ArrayList<Car>) data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvEngineType)
        TextView tvEngineType;
        @BindView(R.id.tvFuel)
        TextView tvFuel;

        private Car newsItem;

        public ViewHolder(View myView) {
            super(myView);
            ButterKnife.bind(this, myView);

            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AdapterOnItemClickListenerEvent adapterOnItemClickListenerEvent = new AdapterOnItemClickListenerEvent();
                    adapterOnItemClickListenerEvent.setObject(newsItem);
                    clickListener.onItemClick(adapterOnItemClickListenerEvent);
                }
            });
        }
    }

    public void setOnClickListener(AdapterOnItemClickListener listener) {
        this.clickListener = listener;
    }
}

