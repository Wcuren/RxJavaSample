package com.lj.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.lj.sample.fragment.FragmentFactory;

public class OperatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_operator, null);
        setContentView(view);
        initViewWithOperator(view);
    }

    private void initViewWithOperator(View view) {
        Fragment fragment = FragmentFactory.getFragmentWithOperator(
                getIntent().getIntExtra("operator_position", -1));
        if(fragment == null) return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.operator_content,fragment);
        transaction.commit();
    }
}
