package com.example.fulicenter.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.fulicenter.R;
import com.example.fulicenter.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 涛 on 2016/10/17.
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.new_good)
    RadioButton mnewGood;
    @BindView(R.id.boutique)
    RadioButton mboutique;
    @BindView(R.id.category)
    RadioButton mcategory;
    @BindView(R.id.cart)
    RadioButton mcart;
    @BindView(R.id.tvCartHint)
    TextView mtvCartHint;
    @BindView(R.id.center)
    RadioButton mcenter;

    int index;
    RadioButton[] b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.i("Mainactivity onCreate");
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        b=new RadioButton[5];
        b[0]=mnewGood;
        b[1]=mboutique;
        b[2]=mcategory;
        b[3]=mcart;
        b[4]=mcenter;
    }

    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.new_good:
                index = 0;
                break;
            case R.id.boutique:
                index = 1;
                break;
            case R.id.category:
                index = 2;
                break;
            case R.id.cart:
                index = 3;
                break;
            case R.id.center:
                index = 4;
                break;
        }
        setRadioButtonStatus();
    }

    private void setRadioButtonStatus() {
        L.e("index"+index);
        for (int i=0;i<b.length;i++){
            if (i==index){
                b[i].setChecked(true);
            }else {
                b[i].setChecked(false);
            }
        }
    }

}
