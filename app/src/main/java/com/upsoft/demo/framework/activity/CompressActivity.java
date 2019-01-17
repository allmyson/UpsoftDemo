package com.upsoft.demo.framework.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.upsoft.demo.R;
import com.upsoft.demo.framework.base.BaseActivity;
import com.upsoft.sdk.image.loader.ImageLoader;
import com.upsoft.sdk.util.FileUtil;
import com.vise.log.ViseLog;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.File;

import uk.co.senab.photoview.PhotoView;

public class CompressActivity extends BaseActivity {
    private PhotoView sourcePV, compressPV;
    private TextView sourceTV, compressTV;
    private String path;

    @Override
    public int getLayoutId() {
        return R.layout.activity_compress;
    }

    @Override
    public void initView() {
        sourcePV = getView(R.id.pv_source);
        compressPV = getView(R.id.pv_compress);
        sourceTV = getView(R.id.tv_source);
        compressTV = getView(R.id.tv_compress);
    }

    @Override
    public void getData() {
        path = getIntent().getStringExtra("path");
        ViseLog.d(path);
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        Log.e("Test", "Bitmap Height == " + options.outHeight);
        String sourceSize = FileUtil.FormetFileSize(FileUtil.getFileSize(new File(path)));
        sourceTV.setText("原始图\t\t" + options.outWidth + "*" + options.outHeight + "\t\t" + sourceSize);
        ImageLoader.with(mContext).file(path).into(sourcePV);


        Tiny.FileCompressOptions options1 = new Tiny.FileCompressOptions();
        Tiny.getInstance().source(path).asFile().withOptions(options1).compress(new FileCallback() {
            @Override
            public void callback(boolean isSuccess, String outfile) {
                //return the compressed file path
                if(isSuccess){
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    /**
                     * 最关键在此，把options.inJustDecodeBounds = true;
                     * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
                     */
                    options.inJustDecodeBounds = true;
                    Bitmap bitmap = BitmapFactory.decodeFile(outfile, options); // 此时返回的bitmap为null
                    /**
                     *options.outHeight为原始图片的高
                     */
                    Log.e("Test", "Bitmap Height == " + options.outHeight);
                    String sourceSize = FileUtil.FormetFileSize(FileUtil.getFileSize(new File(outfile)));
                    compressTV.setText("压缩图\t\t" + options.outWidth + "*" + options.outHeight + "\t\t" + sourceSize);
                    ImageLoader.with(mContext).file(outfile).into(compressPV);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public static void intentToCompress(Context context, String path) {
        Intent intent = new Intent(context, CompressActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }

    /**
     * 得到bitmap的大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }
}
