package com.upsoft.demo.framework.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;
import com.upsoft.demo.R;
import com.upsoft.demo.framework.adapter.GalleryPagerAdapter;
import com.upsoft.demo.framework.adapter.RotationPageTransformer;
import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.demo.framework.bean.SkinBean;
import com.upsoft.demo.framework.config.FrameworkSDK;
import com.upsoft.demo.framework.sp.ColorSP;
import com.upsoft.demo.framework.ui.AllTitleView;
import com.upsoft.demo.framework.util.ColorUtil;
import com.upsoft.sdk.util.L;

import java.util.ArrayList;
import java.util.List;

public class IndividualizationActivity extends BaseActivity implements AllTitleView.ITitleViewOnClick {
    private TextView barColorTV, titleColorTV, buttonColorTV, textIconColorTV;
    private ImageView barView, titleView, buttonIV, textIconIV;
    public static final String ACTION_TITLE_COLOR_CHANGE = "com.upsoft.title.color";
    public static final String KEY_TEXT_ICON_COLOR = "textIconColor";
    private Button hfBtn, changeBtn;
    private ViewPager vp;
    private GalleryPagerAdapter pagerAdapter;
    private List<SkinBean> skinBeanList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_individualization;
    }

    @Override
    public void initView() {
        allTitleView = getView(R.id.title);
        allTitleView.setLeftImg(R.mipmap.ic_back);
        allTitleView.setCenterText("一键换肤");
        allTitleView.setTitleOnClickListener(this);

        barColorTV = getView(R.id.tv_barColor);
        titleColorTV = getView(R.id.tv_titleColor);
        buttonColorTV = getView(R.id.tv_buttonColor);
        textIconColorTV = getView(R.id.tv_textIconColor);
        barView = getView(R.id.view_bar);
        titleView = getView(R.id.view_title);
        buttonIV = getView(R.id.iv_button);
        textIconIV = getView(R.id.iv_textIcon);
        hfBtn = getView(R.id.btn_);
        changeBtn = getView(R.id.btn_change);
        changeBtn.setOnClickListener(this);
        barView.setOnClickListener(this);
        titleView.setOnClickListener(this);
        buttonIV.setOnClickListener(this);
        textIconIV.setOnClickListener(this);
        hfBtn.setOnClickListener(this);

        barColorTV.setText(barColor);
        titleColorTV.setText(titleColor);
        buttonColorTV.setText(btnColor);
        textIconColorTV.setText(textIconColor);
        barView.setColorFilter(Color.parseColor(barColor));
        titleView.setColorFilter(Color.parseColor(titleColor));
        buttonIV.setColorFilter(Color.parseColor(btnColor));
        textIconIV.setColorFilter(Color.parseColor(textIconColor));
        shapeSolid(hfBtn, btnColor);
        shapeSolid(changeBtn, btnColor);

        vp = getView(R.id.vp_);
        skinBeanList = new ArrayList<>();
        skinBeanList.addAll(FrameworkSDK.getDefaultConfig().getSkinBeanList());
        pagerAdapter = new GalleryPagerAdapter(skinBeanList, mContext);
        vp.setAdapter(pagerAdapter);
        vp.setPageTransformer(true, new RotationPageTransformer());
        vp.setOffscreenPageLimit(2);//设置预加载的数量，这里设置了2,会预加载中心item左边两个Item和右边两个Item
        vp.setPageMargin(10);//设置两个Page之间的距离
        int position = -1;
        for (int i = 0; i < skinBeanList.size(); i++) {
            if (barColor.equals(skinBeanList.get(i).color)) {
                position = i;
                break;
            }
        }
        if (position >= 0) {
            vp.setCurrentItem(position);
        } else {
            int size = skinBeanList.size();
            int currentItem = 0;
            if (size % 2 == 0) {
                currentItem = size / 2 - 1;
            } else {
                currentItem = size / 2;
            }
            L.e("currentItem=" + currentItem);
            vp.setCurrentItem(currentItem);
        }

    }

    @Override
    public void getData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_bar:
                ColorPickerDialog colorPickerDialog = ColorPickerDialog.newBuilder().setColor(Color.parseColor
                        (barColor))
                        .setDialogTitle(R.string.color_picker)//设置dialog标题
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)//设置为默认模式
                        .setShowAlphaSlider(false)//设置有透明度模式，默认没有透明度
                        .setDialogId(0)//设置Id,回调时传回用于判断
                        .setAllowPresets(true)//不显示预知模式
                        .create();//Buider创建
                colorPickerDialog.setColorPickerDialogListener(listener);
                //设置回调，用于获取选择的颜色
                colorPickerDialog.show(getFragmentManager(), "color-picker-dialog");
                break;
            case R.id.view_title:

                ColorPickerDialog colorPickerDialog1 = ColorPickerDialog.newBuilder().setColor(Color.parseColor
                        (titleColor))
                        .setDialogTitle(R.string.color_picker)//设置dialog标题
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)//设置为自定义模式
                        .setShowAlphaSlider(false)//设置有透明度模式，默认没有透明度
                        .setDialogId(1)//设置Id,回调时传回用于判断
                        .setAllowPresets(true)//不显示预知模式
                        .create();//Buider创建
                colorPickerDialog1.setColorPickerDialogListener(listener);
                //设置回调，用于获取选择的颜色
                colorPickerDialog1.show(getFragmentManager(), "color-picker-dialog");
                break;
            case R.id.iv_button:
                ColorPickerDialog colorPickerDialog2 = ColorPickerDialog.newBuilder().setColor(Color.parseColor
                        (btnColor))
                        .setDialogTitle(R.string.color_picker)//设置dialog标题
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)//设置为自定义模式
                        .setShowAlphaSlider(false)//设置有透明度模式，默认没有透明度
                        .setDialogId(2)//设置Id,回调时传回用于判断
                        .setAllowPresets(true)//不显示预知模式
                        .create();//Buider创建
                colorPickerDialog2.setColorPickerDialogListener(listener);
                //设置回调，用于获取选择的颜色
                colorPickerDialog2.show(getFragmentManager(), "color-picker-dialog");
                break;
            case R.id.iv_textIcon:
                ColorPickerDialog colorPickerDialog3 = ColorPickerDialog.newBuilder().setColor(Color.parseColor
                        (textIconColor))
                        .setDialogTitle(R.string.color_picker)//设置dialog标题
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)//设置为自定义模式
                        .setShowAlphaSlider(false)//设置有透明度模式，默认没有透明度
                        .setDialogId(3)//设置Id,回调时传回用于判断
                        .setAllowPresets(true)//不显示预知模式
                        .create();//Buider创建
                colorPickerDialog3.setColorPickerDialogListener(listener);
                //设置回调，用于获取选择的颜色
                colorPickerDialog3.show(getFragmentManager(), "color-picker-dialog");
                break;
            case R.id.btn_:
                ColorPickerDialog colorPickerDialog4 = ColorPickerDialog.newBuilder().setColor(Color.parseColor
                        (btnColor))
                        .setDialogTitle(R.string.color_picker)//设置dialog标题
                        .setDialogType(ColorPickerDialog.TYPE_PRESETS)//设置为自定义模式
                        .setShowAlphaSlider(false)//设置有透明度模式，默认没有透明度
                        .setDialogId(4)//设置Id,回调时传回用于判断
                        .setAllowPresets(true)//不显示预知模式
                        .create();//Buider创建
                colorPickerDialog4.setColorPickerDialogListener(listener);
                //设置回调，用于获取选择的颜色
                colorPickerDialog4.show(getFragmentManager(), "color-picker-dialog");
                break;
            case R.id.btn_change:
                int position = vp.getCurrentItem();
                L.e("vp current position = " + position);
                String colorStr = skinBeanList.get(position).color;
                int color = Color.parseColor(colorStr);
                barColorTV.setText(colorStr);
                titleColorTV.setText(colorStr);
                buttonColorTV.setText(colorStr);
                textIconColorTV.setText(colorStr);

                setBarColor(colorStr);
                allTitleView.setBackgroundColor(color);
                titleView.setColorFilter(color);
                barView.setColorFilter(color);
                buttonIV.setColorFilter(color);
                textIconIV.setColorFilter(color);
                shapeSolid(hfBtn, colorStr);
                shapeSolid(changeBtn,colorStr);

                FunctionApi.COLOR_BAR = colorStr;
                FunctionApi.COLOR_TITLE = colorStr;
                FunctionApi.COLOR_BTN = colorStr;
                FunctionApi.COLOR_TEXT_ICON = colorStr;
                ColorSP.saveBarColor(mContext, colorStr);
                ColorSP.saveTitleColor(mContext, colorStr);
                ColorSP.saveBtnColor(mContext, colorStr);
                ColorSP.saveTextIconColor(mContext, colorStr);
                Intent intent2 = new Intent(ACTION_TITLE_COLOR_CHANGE);
                intent2.putExtra(KEY_TEXT_ICON_COLOR, colorStr);
                sendBroadcast(intent2);
                break;
        }
    }

    @Override
    public void backLeft() {
        finish();
    }

    @Override
    public void backRight() {

    }

    private ColorPickerDialogListener listener = new ColorPickerDialogListener() {
        @Override
        public void onColorSelected(int dialogId, int color) {
//            String colorStr = ColorUtil.int2Hex(color);
            L.e("color=" + color);
            int[] argb = ColorUtil.int2Argb(color);
            L.e("a="+argb[0]);
            L.e("r="+argb[1]);
            L.e("g="+argb[2]);
            L.e("b="+argb[3]);
            String colorStr = ColorUtil.rgb2Hex(argb);
            FunctionApi.alpha = argb[0];
            ColorSP.saveAlpha(mContext,argb[0]);
            L.e(colorStr);
            switch (dialogId) {
                case 0:
                    barColorTV.setText(colorStr);
                    barView.setColorFilter(color);
                    setBarColor(colorStr);
                    FunctionApi.COLOR_BAR = colorStr;
                    ColorSP.saveBarColor(mContext, colorStr);
                    break;
                case 1:
                    //标题栏
                    titleColorTV.setText(colorStr);
                    titleView.setColorFilter(color);
                    allTitleView.setBackgroundColor(color);
                    FunctionApi.COLOR_TITLE = colorStr;
                    ColorSP.saveTitleColor(mContext, colorStr);
                    break;
                case 2:
                    //button
                    buttonColorTV.setText(colorStr);
                    buttonIV.setColorFilter(color);
                    shapeSolid(hfBtn, colorStr);
                    shapeSolid(changeBtn,colorStr);
                    FunctionApi.COLOR_BTN = colorStr;
                    ColorSP.saveBtnColor(mContext, colorStr);
                    break;
                case 3:
                    textIconColorTV.setText(colorStr);
                    textIconIV.setColorFilter(color);
                    FunctionApi.COLOR_TEXT_ICON = colorStr;
                    ColorSP.saveTextIconColor(mContext, colorStr);
                    Intent intent = new Intent(ACTION_TITLE_COLOR_CHANGE);
                    intent.putExtra(KEY_TEXT_ICON_COLOR, colorStr);
                    sendBroadcast(intent);
                    break;
                case 4:
                    barColorTV.setText(colorStr);
                    titleColorTV.setText(colorStr);
                    buttonColorTV.setText(colorStr);
                    textIconColorTV.setText(colorStr);

                    setBarColor(colorStr);
                    allTitleView.setBackgroundColor(color);
                    titleView.setColorFilter(color);
                    barView.setColorFilter(color);
                    buttonIV.setColorFilter(color);
                    textIconIV.setColorFilter(color);
                    shapeSolid(hfBtn, colorStr);
                    shapeSolid(changeBtn,colorStr);

                    FunctionApi.COLOR_BAR = colorStr;
                    FunctionApi.COLOR_TITLE = colorStr;
                    FunctionApi.COLOR_BTN = colorStr;
                    FunctionApi.COLOR_TEXT_ICON = colorStr;
                    ColorSP.saveBarColor(mContext, colorStr);
                    ColorSP.saveTitleColor(mContext, colorStr);
                    ColorSP.saveBtnColor(mContext, colorStr);
                    ColorSP.saveTextIconColor(mContext, colorStr);
                    Intent intent2 = new Intent(ACTION_TITLE_COLOR_CHANGE);
                    intent2.putExtra(KEY_TEXT_ICON_COLOR, colorStr);
                    sendBroadcast(intent2);
                    break;
            }
        }

        @Override
        public void onDialogDismissed(int dialogId) {

        }
    };


}
