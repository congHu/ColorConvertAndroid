package com.hcdstudio.colorconvert;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText hexEdit;
    EditText redEdit;
    EditText greenEdit;
    EditText blueEdit;
    ImageView showColor;

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public int hex2int(CharSequence cs){
        String h = cs.toString();
        try{
            return Integer.parseInt(h);
        }catch (NumberFormatException e){
            switch (h){
                case "A":
                    return 10;
                case "B":
                    return 11;
                case "C":
                    return 12;
                case "D":
                    return 13;
                case "E":
                    return 14;
                case "F":
                    return 15;
                case "a":
                    return 10;
                case "b":
                    return 11;
                case "c":
                    return 12;
                case "d":
                    return 13;
                case "e":
                    return 14;
                case "f":
                    return 15;
                default:
                    hexEdit.setTextColor(Color.RED);
                    return -1;

            }
        }
    }
    public String int2hex(int i){
        if(i>=0&&i<10){
            return String.valueOf(i);
        }else{
            switch (i){
                case 10:
                    return "A";
                case 11:
                    return "B";
                case 12:
                    return "C";
                case 13:
                    return "D";
                case 14:
                    return "E";
                case 15:
                    return "F";
                default:
                    return "";
            }
        }
    }
    public void rgb2color(){
        try{
            int r = Integer.parseInt(redEdit.getText().toString());
            int g = Integer.parseInt(greenEdit.getText().toString());
            int b = Integer.parseInt(blueEdit.getText().toString());
            showColor.setBackgroundColor(Color.rgb(r,g,b));
            String hex = "";
            hex += int2hex(r/16);
            hex += int2hex(r%16);
            hex += int2hex(g/16);
            hex += int2hex(g%16);
            hex += int2hex(b/16);
            hex += int2hex(b%16);
            hexEdit.setText(hex);
        }catch (NumberFormatException e){

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hexEdit = (EditText)findViewById(R.id.hexEditText);
        redEdit = (EditText)findViewById(R.id.redEditText);
        greenEdit = (EditText)findViewById(R.id.greenEditText);
        blueEdit = (EditText)findViewById(R.id.blueEditText);
        showColor = (ImageView)findViewById(R.id.imageView);
        hexEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()<6){
                    hexEdit.setTextColor(Color.GRAY);
                }else if(s.length()==6){


                    if(hexEdit.hasFocus()){
                        hexEdit.setTextColor(Color.BLACK);
                        int rValue = 0;
                        int gValue = 0;
                        int bValue = 0;

                        for(int i=0;i<s.length();i++){

                            switch (i/2){
                                case 0:
                                    if(i%2==0){
                                        rValue+=(hex2int(s.subSequence(i,i+1)))*16;
                                    }else{
                                        rValue+=(hex2int(s.subSequence(i,i+1)));
                                        redEdit.setText(String.valueOf(rValue));
                                    }
                                    break;
                                case 1:
                                    if(i%2==0){
                                        gValue+=(hex2int(s.subSequence(i,i+1)))*16;
                                    }else{
                                        gValue+=(hex2int(s.subSequence(i,i+1)));
                                        greenEdit.setText(String.valueOf(gValue));
                                    }
                                    break;
                                case 2:
                                    if(i%2==0){
                                        bValue+=(hex2int(s.subSequence(i,i+1)))*16;
                                    }else{
                                        bValue+=(hex2int(s.subSequence(i,i+1)));
                                        blueEdit.setText(String.valueOf(bValue));
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        showColor.setBackgroundColor(Color.rgb(rValue,gValue,bValue));
                    }
                }else{
                    hexEdit.setTextColor(Color.RED);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        redEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    int r = Integer.parseInt(s.toString());
                    if(r<0||r>255){
                        throw new NumberFormatException();
                    }else {
                        redEdit.setTextColor(Color.BLACK);

                        if(!hexEdit.hasFocus()){
                            rgb2color();
                        }

                    }
                }catch (NumberFormatException e){
                    redEdit.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        greenEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    int r = Integer.parseInt(s.toString());
                    if(r<0||r>255){
                        throw new NumberFormatException();
                    }else {
                        greenEdit.setTextColor(Color.BLACK);

                        if(!hexEdit.hasFocus()){
                            rgb2color();
                        }
                    }
                }catch (NumberFormatException e){
                    greenEdit.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        blueEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    int r = Integer.parseInt(s.toString());
                    if(r<0||r>255){
                        throw new NumberFormatException();
                    }else {
                        blueEdit.setTextColor(Color.BLACK);

                        if(!hexEdit.hasFocus()){
                            rgb2color();
                        }
                    }
                }catch (NumberFormatException e){
                    blueEdit.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
