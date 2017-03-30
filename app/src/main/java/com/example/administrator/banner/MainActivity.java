package com.example.administrator.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity{
    String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";
    private SliderLayout banner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = (SliderLayout) findViewById(R.id.slider);
        OkHttpUtils.get().url(url).build().execute(new StringCallback(){
            @Override
            public void onError(Call call, Exception e, int id){
            }

            @Override
            public void onResponse(String response, int id){
                Gson gson=new Gson();
                List<Banner> info = gson.fromJson(response, new TypeToken<List<Banner>>(){
                }.getType());
                for(Banner banner1 : info){
                    TextSliderView textSliderView= new TextSliderView(MainActivity.this);
                    textSliderView.image(banner1.getImgUrl());
                    textSliderView.description(banner1.getName());
                    textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                    banner.addSlider(textSliderView);


                }

                //设置指示器类型
                banner.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                banner.setCustomAnimation(new DescriptionAnimation());
                //设置转场动画
                banner.setPresetTransformer(SliderLayout.Transformer.RotateDown);
                banner.setDuration(3000);
            }
        });

    }
}
