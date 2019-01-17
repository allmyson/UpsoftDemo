package com.upsoft.demo.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.upsoft.demo.R;


public class CommonDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String titleText;
    private String msgText;
    private String orgText;
    private String timeText;
    private String cancelText;
    private String affirmText;
    private ItaskDialogBack itaskDialogBack;
    //
    private TextView mTitle;
    private TextView mMsg;
    private TextView mOrg;
    private TextView mTime;
    private TextView mCance;
    private TextView mAffirm;

    public CommonDialog(Context context, String titleText, String msgText, String orgText, String timeText, String cancelText, String affirmText, ItaskDialogBack itaskDialogBack) {
        super(context, R.style.CustomDialog);
        this.context = context;
        this.titleText = titleText;
        this.msgText = msgText;
        this.orgText = orgText;
        this.timeText = timeText;
        this.cancelText = cancelText;
        this.affirmText = affirmText;
        this.itaskDialogBack = itaskDialogBack;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.common_dialog);
        mTitle = (TextView) findViewById(R.id.teskdialog_title);
        mMsg = (TextView) findViewById(R.id.teskdialog_msg);
        mOrg = (TextView) findViewById(R.id.teskdialog_orgname);
        mTime = (TextView) findViewById(R.id.teskdialog_time);
        mCance = (TextView) findViewById(R.id.teskdialog_cancel);
        mAffirm = (TextView) findViewById(R.id.teskdialog_affirm);
        //
        mTitle.setText(titleText);
        mMsg.setText(msgText);
        mOrg.setText(orgText);
        mTime.setText(timeText);
        mCance.setText(cancelText);
        mAffirm.setText(affirmText);

        //
        mCance.setOnClickListener(this);
        mAffirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.teskdialog_cancel) {
            itaskDialogBack.cancel();
            dismiss();

        } else if (i == R.id.teskdialog_affirm) {
            itaskDialogBack.affirm();
            dismiss();

        }
    }

    public interface ItaskDialogBack {
        void cancel();
        void affirm();
    }
}
