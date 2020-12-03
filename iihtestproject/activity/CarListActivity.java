package com.example.iihtestproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.iihtestproject.R;
import com.example.iihtestproject.adapter.CarItemAdapter;
import com.example.iihtestproject.fragment.MapsFragment;
import com.example.iihtestproject.interfaces.AdapterOnItemClickListener;
import com.example.iihtestproject.interfaces.AdapterOnItemClickListenerEvent;
import com.example.iihtestproject.object.Car;
import com.example.iihtestproject.object.CarListResponseEvent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarListActivity extends BaseActivity implements AdapterOnItemClickListener, MapsFragment.MapFragmentListener {

    @BindView(R.id.recycleView)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    private CarItemAdapter mCarItemAdapter;
    private Car mSelectedCar = new Car();
    private ArrayList<Car> mCarDetailsList;
    private MapsFragment mMapsFragment;

    private boolean isShowCarList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolBar);

        mMapsFragment = new MapsFragment();
        mMapsFragment.setFragmentInterface(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCarItemAdapter = new CarItemAdapter(this);
        mCarItemAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mCarItemAdapter);

        if (isNetworkConnected()) {
            showWaiting();
            IIHApplication.getInstance().getController().getCarList();
        }else {
            showMessage("No Internet");
        }

    }

    /**
     * @param event list view item click listener
     */
    @Override
    public void onItemClick(AdapterOnItemClickListenerEvent event) {
        mSelectedCar = (Car) event.getObject();
        isShowCarList = false;
        startFragment(R.id.letterContainer,"map", mMapsFragment, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btnCarList)
    public void onShowCraListOnMap(){
        if (mCarDetailsList != null){
            isShowCarList = true;
            startFragment(R.id.letterContainer,"map", mMapsFragment, true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CarListResponseEvent event) {
        dismissWaiting();

        if (event.isSuccess()){
            mCarDetailsList = event.getCars();
            mCarItemAdapter.setDataToAdapter(mCarDetailsList);
        }else {
            showMessage(event.getMessage());
        }
    }

    @Override
    public void onMapReady() {
        if (isShowCarList) {
            mMapsFragment.setCarListLocation(mCarDetailsList);
        } else
            mMapsFragment.setCarLocation(mSelectedCar);
    }

}