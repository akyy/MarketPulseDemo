package com.mp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.gson.JsonArray;
import com.mp.Controller.ApiController;
import com.mp.R;
import com.mp.adapter.MainListAdapter;
import com.mp.interfaces.ApiInterface;
import com.mp.interfaces.ClickInterface;
import com.mp.utils.NetworkUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment  implements ClickInterface {
    View rootView;

    @BindView(R.id.fragment_recycler_view)
    RecyclerView recView;

    private Call<JsonArray> mainList;
    private ApiInterface apiService;
    MainListAdapter recListAdapter;
    private JSONArray listMainArray=new JSONArray();

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        initializeView();
        return rootView;
    }

    private void initializeView() {
        recView.setHasFixedSize(true);
        recView.setNestedScrollingEnabled(false);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        recView.setItemAnimator(new DefaultItemAnimator());
        apiService = ApiController.getClient().create(ApiInterface.class);
        recListAdapter = new MainListAdapter(listMainArray,this);
        recView.setAdapter(recListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(NetworkUtils.isConnected(getContext())) {
            callService();
        }else {
            Toast.makeText(getContext(),"Please connect to internet",Toast.LENGTH_LONG).show();
        }
    }

    private void callService() {
        mainList = apiService.loadChanges();

        mainList.enqueue(new Callback<JsonArray>() {

            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response != null && response.code() == 200) {
                    JsonArray jsonObject = response.body();
                    try {
                        listMainArray=new JSONArray(""+jsonObject);
                        recListAdapter.setData(listMainArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Log.e("status", "onFailure: ");
            }

        });
    }

    @Override
    public void setDetails(JSONObject mainModel) {
        Bundle bundle = new Bundle();
        bundle.putString("criteria", mainModel.toString());
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        CriteriaFragment fragment = new CriteriaFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framelayout_main, fragment).addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

}
