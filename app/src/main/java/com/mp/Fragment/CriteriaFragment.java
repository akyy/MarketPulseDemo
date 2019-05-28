package com.mp.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mp.R;
import com.mp.adapter.CriteriaListAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CriteriaFragment extends Fragment {

    View rootView;
    @BindView(R.id.criteria_recycler_view)
    RecyclerView recView;
    @BindView(R.id.txt_criteria)
    AppCompatTextView txtCriteria;
    @BindView(R.id.txt_tag)
    AppCompatTextView txtTag;

    private JSONObject criteriaList;
    private JSONArray criteriaArray;

    CriteriaListAdapter recListAdapter;
    public CriteriaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_criteria, container, false);
        ButterKnife.bind(this, rootView);
        criteriaList=new JSONObject();
        criteriaArray=new JSONArray();
        initializeView();
        return rootView;
    }

    private void initializeView() {
        Bundle bundle = getArguments();
        String criteria = bundle.getString("criteria");
        try {
            criteriaList=new JSONObject(criteria);
            txtCriteria.setText(criteriaList.optString("name"));
            txtTag.setText(criteriaList.optString("tag"));
            if (criteriaList.optString("color").equalsIgnoreCase("green")) {
                txtTag.setTextColor(Color.GREEN);
            }else  if (criteriaList.optString("color").equalsIgnoreCase("red")) {
                txtTag.setTextColor(Color.RED);
            }
            recView.setHasFixedSize(true);
            recView.setNestedScrollingEnabled(false);
            recView.setLayoutManager(new LinearLayoutManager(getContext()));
            recView.setItemAnimator(new DefaultItemAnimator());
            recListAdapter = new CriteriaListAdapter(criteriaList.optJSONArray("criteria"));
            recView.setAdapter(recListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
