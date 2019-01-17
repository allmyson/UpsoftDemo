package com.upsoft.demo.framework.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.upsoft.demo.framework.api.FunctionApi;
import com.upsoft.demo.framework.sp.TextSizeSP;
import com.upsoft.demo.framework.util.TextScaleUtil;
import com.upsoft.sdk.util.L;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    protected View mView;
    protected Context mContext;
    protected Activity mActivity;
    protected TextView titleTV;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mActivity = getActivity();
        TextScaleUtil.scaleTextSize(mActivity, TextSizeSP.getTextSizeScale(mContext));//设置字体倍数
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutResource(), container, false);
        }
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        init();
        getData();
        return mView;
    }

    protected abstract void init();

    protected abstract void getData();

    protected abstract int getLayoutResource();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (titleTV != null) {
            String titleColor = FunctionApi.getCurrentTitleColor(mContext);
            titleTV.setBackgroundColor(Color.parseColor(titleColor));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    protected final <E extends View> E getView(int id) {
        try {
            return (E) mView.findViewById(id);
        } catch (ClassCastException ex) {
            L.e(ex.toString());
            throw ex;
        }
    }

    protected void show(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }


}
