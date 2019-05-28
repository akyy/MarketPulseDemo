package com.mp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mp.Fragment.MainFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        addFragment();
    }

    private void addFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MainFragment fragment = new MainFragment();
        fragmentTransaction.replace(R.id.framelayout_main, fragment).addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
