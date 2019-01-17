package com.upsoft.demo.framework.api;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.telephony.PhoneNumberUtils;

import com.google.gson.Gson;
import com.upsoft.demo.framework.activity.LoginActivity;
import com.upsoft.demo.framework.activity.NFCScanActivity;
import com.upsoft.demo.framework.activity.ShowPicDetailActivity;
import com.upsoft.demo.framework.activity.WelcomeActivity;
import com.upsoft.demo.framework.bean.InfoBean;
import com.upsoft.demo.framework.bean.NetworkBean;
import com.upsoft.demo.framework.dialog.DialogUtil;
import com.upsoft.demo.framework.dialog.SureFragment;
import com.upsoft.demo.framework.sp.ColorSP;
import com.upsoft.demo.framework.sp.IpSP;
import com.upsoft.demo.framework.sp.UserSP;
import com.upsoft.demo.framework.util.ActivityUtil;
import com.upsoft.demo.framework.util.AppUtil;
import com.upsoft.qrlibrary.CaptureActivity;
import com.upsoft.sdk.Constant;
import com.upsoft.sdk.http.BaseBean;
import com.upsoft.sdk.http.BaseHttp;
import com.upsoft.sdk.http.nohttp.HttpListener;
import com.upsoft.sdk.sp.SPUtil;
import com.upsoft.sdk.util.Base64Util;
import com.upsoft.sdk.util.L;
import com.upsoft.sdk.util.Md5Util;
import com.upsoft.sdk.util.StringUtil;
import com.upsoft.sdk.util.SystemUtil;
import com.upsoft.sdk.util.ToastUtil;
import com.yanzhenjie.nohttp.rest.Response;
import com.yongchun.library.view.ImageSelectorActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lh
 * @version 1.0.0
 * @filename FunctionApi
 * @description -------------------------------------------------------
 * @date 2018/8/31 14:26
 */
public class FunctionApi {
    public static interface LoginCallback {
        void onSuccess(InfoBean infoBean);

        void onFail(Constant.ErrorCode errorCode);
    }


    public static interface LogoutCallback {
        void onSuccess();

        void onFail(Constant.ErrorCode errorCode);
    }


    public static final String AUTHORITY = ".fileprovider";
    public static String TEMP_PHOTO_PATH = "";

    public static boolean isLogin(Context context) {
        return UserSP.isLogin(context);
    }

    /**
     * 查看大图
     *
     * @param mContext
     * @param path
     * @param position
     */
    public static void LargerImage(Context mContext, String[] path, int position) {
        if (path != null && path.length > 0) {
            Intent intent = new Intent();
            intent.putExtra("imageUrl", path);
            if (position < path.length) {
                intent.putExtra("position", position);
            } else {
                intent.putExtra("position", 0);
            }
            intent.setClass(mContext, ShowPicDetailActivity.class);
            mContext.startActivity(intent);
        } else {
            ToastUtil.show(mContext, "暂无图片");
        }
    }

    /**
     * @param mContext
     * @param max           最大选择张数
     * @param mode          选择模式 1多选，2单
     * @param showCamera    显示拍照
     * @param enablePreview 预览
     * @param enableCrop    剪切
     */
    public static void takePicture(Context mContext, int max, int mode, boolean showCamera, boolean enablePreview,
                                   boolean enableCrop) {
        if ((ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager
                        .PERMISSION_GRANTED)) {
            ToastUtil.show(mContext, "请开启相关权限");
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            return;
        }
        ImageSelectorActivity.AUTHORITY = AppUtil.getPackageName(mContext) + FunctionApi.AUTHORITY;
        ImageSelectorActivity.start((Activity) mContext, max, mode, showCamera, enablePreview, enableCrop);
    }

    /**
     * @param mContext
     * @param max           最大选择张数
     * @param mode          选择模式 1多选，2单
     * @param showCamera    显示拍照
     * @param enablePreview 预览
     * @param enableCrop    剪切
     */
    public static void takePicture(Fragment mContext, int max, int mode, boolean showCamera, boolean enablePreview,
                                   boolean enableCrop) {
        if ((ActivityCompat.checkSelfPermission(mContext.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(mContext.getActivity(), Manifest.permission.CAMERA) !=
                        PackageManager
                                .PERMISSION_GRANTED)) {
            ToastUtil.show(mContext.getActivity(), "请开启相关权限");
            ActivityCompat.requestPermissions(mContext.getActivity(), new String[]{Manifest.permission
                    .WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            return;
        }
        ImageSelectorActivity.AUTHORITY = AppUtil.getPackageName(mContext.getActivity()) + FunctionApi.AUTHORITY;
        ImageSelectorActivity.start(mContext, max, mode, showCamera, enablePreview, enableCrop);
    }

    /**
     * 扫描二维码
     */
    public static void intentToCapture(Activity context, int requestCode) {
        Intent intent = new Intent(context, CaptureActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 扫描二维码
     */
    public static void intentToCapture(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), CaptureActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * NFC
     *
     * @param activity
     * @param requestCode
     */
    public static void intentToNFC(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, NFCScanActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * NFC
     *
     * @param fragment
     * @param requestCode
     */
    public static void intentToNFC(Fragment fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), NFCScanActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 拍照获取相片
     *
     * @param mContext
     */
    public static void takeCamera(Activity mContext, int requesetCode) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        TEMP_PHOTO_PATH = Constant.TEMPIM_PATH + "/" + StringUtil.getNowTimeStr(1);
        File f = new File(TEMP_PHOTO_PATH);//
        if (!new File(Constant.TEMPIM_PATH).exists()) {
            new File(Constant.TEMPIM_PATH).mkdirs();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//android-7.0
            String authority = AppUtil.getPackageName(mContext) + AUTHORITY;
            Uri contentUri = FileProvider.getUriForFile(mContext, authority, f);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);//
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            L.e("path>>", contentUri.toString());
        } else {
            Uri u = Uri.fromFile(f);//
            L.e("path>>", u.toString());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);//
        }
        mContext.startActivityForResult(intent, requesetCode);
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void diallPhone(Context context, String phoneNum) {
        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED)) {
            ToastUtil.show(context, "请开启相关权限");
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission
                    .CALL_PHONE}, 1);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }


    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context, String phoneNum) {
        if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED)) {
            ToastUtil.show(context, "请开启相关权限");
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission
                    .CALL_PHONE}, 1);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 发短信
     *
     * @param context
     * @param phoneNumber
     * @param message
     */
    public static void sendSMS(Context context, String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
            intent.putExtra("sms_body", message);
            context.startActivity(intent);
        }
    }

    /**
     * 获取设备信息
     *
     * @param context
     * @return
     */
    public static Map<String, String> getDeviceInfo(Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("appSignature", SystemUtil.AppSignature(context));
        map.put("appName", SystemUtil.AppName(context));
        map.put("appVersionCode", SystemUtil.VersionCode(context) + "");
        map.put("appVersionName", SystemUtil.VersionName(context));
        map.put("appPackageName", SystemUtil.PackgeName(context));
        map.put("imei", SystemUtil.IMEI(context));
        map.put("imsi", SystemUtil.IMSI(context));
        map.put("phoneNumber", SystemUtil.Num(context));
        map.put("sn", SystemUtil.SN());//产品序列号
        map.put("sim", SystemUtil.SIM(context));
        map.put("id", SystemUtil.ID(context));
        map.put("mac", SystemUtil.MAC(context));
        map.put("country", SystemUtil.Country(context));
        map.put("Language", SystemUtil.Language(context));
        map.put("screenHeight", SystemUtil.Height(context) + "");
        map.put("screenWidth", SystemUtil.Width(context) + "");
        map.put("systemVersionName", Build.VERSION.RELEASE);//系统版本名
        map.put("systemVersionCode", Build.VERSION.SDK_INT + "");//系统版本号
        map.put("systemMode", Build.MODEL);//系统型号
        map.put("systemBrand", Build.BRAND);//系统定制商
        map.put("systemBoard", Build.BOARD);//系统主板
        map.put("product", Build.PRODUCT);//手机制造商
        map.put("system2", Build.HOST);//系统2
        map.put("system3", Build.TIME + "    " + System.currentTimeMillis());//系统3
        map.put("system4", Build.USER);//系统4
        map.put("manufacturer", Build.MANUFACTURER);//系统硬件执照商
        return map;
    }

    public static String getDeviceInfoByJson(Context context) {
        return new Gson().toJson(getDeviceInfo(context));
    }


    public static boolean isFirst(Context context) {
        return (boolean) SPUtil.get(context, "isFirst", true);
    }

    public static void setNoFirst(Context context) {
        SPUtil.put(context, "isFirst", false);
    }


    /**
     * 保存IP列表
     *
     * @param context
     * @param list
     */
    public static void saveIplist(Context context, List<NetworkBean> list) {
        IpSP.saveIPList(context, list);
    }

    /**
     * 获取Ip列表
     *
     * @param context
     * @return
     */
    public static List<NetworkBean> getIPList(Context context) {
        return IpSP.getIPList(context);
    }

    public static String getIp(Context mContext) {
        return IpSP.getIp(mContext);
    }


    public static String getHttpIp(Context mContext) {
        return IpSP.getHttpIp(mContext);
    }


    /**
     * 获取用户数据
     *
     * @param mContext
     * @param listener
     */
    public static void getUserInfo(Activity mContext, HttpListener<String> listener) {
        String url = FunctionApi.getHttpIp(mContext) + Constant.Url.USERINFO;
        BaseHttp.getInstance().postJson(mContext, url, "", listener);
    }


    /**
     * 判断登录Activity是否处于栈顶
     *
     * @return
     */
    public static boolean isNewTop(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context
                .ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(LoginActivity.class.getName()) || name.equals(WelcomeActivity.class.getName());
    }

    /**
     * session失效弹出登录提示
     *
     * @param context
     */
    public static void showLoginDialog(final Context context) {
        L.e("showLoginDialog执行" + context.getClass().getName());
        if (!FunctionApi.isNewTop(context) && context instanceof FragmentActivity) {
            DialogUtil.showSure(context, "登录状态已失效,请重新登录！", new SureFragment.ClickListener() {
                @Override
                public void sure() {
                    FunctionApi.intentToLogin(context, UserSP.getUsername(context), UserSP.getPassword
                            (context), true);
                    DialogUtil.removeDialog(context);
                    ActivityUtil.finishExceptLogin();
                }
            });
        }
    }

    /**
     * 跳转登录界面
     *
     * @param context
     * @param username
     * @param password
     * @param isFinishAll 登录成功后是否关闭所有界面并跳转程序首页
     */
    public static void intentToLogin(Context context, String username, String password, boolean isFinishAll) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(LoginActivity.KEY_USER, StringUtil.valueOf(username));
        intent.putExtra(LoginActivity.KEY_PASSWORD, StringUtil.valueOf(password));
        intent.putExtra(LoginActivity.KEY_ISFINISH_ALL, isFinishAll);
        context.startActivity(intent);
    }

    public static String getDownloadUrlByFileId(Context context, String fileId) {
        return FunctionApi.getHttpIp(context) + "/cgi/bp/filemgt/special/download?fileId=" + StringUtil.valueOf(fileId);
    }


    public static void logout(final Context context, final LogoutCallback callback) {
        String url = FunctionApi.getHttpIp(context) + Constant.Url.LOGOUT;
        BaseHttp.getInstance().postSimpleJson(context, url, "", new HttpListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                BaseBean baseBean = new Gson().fromJson(StringUtil.valueOf(response.get()), BaseBean.class);
                if (baseBean != null && "1".equals(baseBean.status)) {
                    if (callback != null) {
                        callback.onSuccess();
                    }
                } else {
                    if (callback != null) {
                        callback.onFail(new Constant.ErrorCode(Constant.ErrorCode.WRONG_NULL));
                    }
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                if (callback != null) {
                    callback.onFail(Constant.ErrorCode.getServerErrorCode(response));
                }
            }
        });
    }

    /**
     * 退出清除
     *
     * @param context
     * @return
     */
    public static void loginOut(Context context) {
//        UserSP.clear(context);
        //取出IP信息
        String ip = IpSP.getIp(context);
        String addName = IpSP.getAddIpName(context);
        String addAddress = IpSP.getAddIpAres(context);
        List<NetworkBean> list = IpSP.getIPList(context);

        //取出用户信息
        boolean isRemember = UserSP.isRemenber(context);
        String username = UserSP.getUsername(context);
        String password = UserSP.getPassword(context);

        //取出ColorSP数据
        String titleColor = getCurrentTitleColor(context);
        String barColor = getCurrentBarColor(context);
        String btnColor = getCurrentBtnColor(context);
        String textIconColor = getCurrentTextIconColor(context);
        clearAllSP(context);
        //还原IP信息
        IpSP.saveIp(context, ip);
        if (!"".equals(addName) && !"".equals(addAddress)) {
            IpSP.saveAddIp(context, addName, addAddress);
        }
        if (list != null && list.size() > 0) {
            IpSP.saveIPList(context, list);
        }
        //还原用户信息
        UserSP.saveUsername(context, username);
        UserSP.savePassword(context, password);
        UserSP.setRemenber(context, isRemember);
        //还原ColorSP数据
        ColorSP.saveTitleColor(context, titleColor);
        ColorSP.saveBarColor(context, barColor);
        ColorSP.saveBtnColor(context, btnColor);
        ColorSP.saveTextIconColor(context, textIconColor);
        FunctionApi.setNoFirst(context);
    }

    /**
     * 清除所有SP
     *
     * @param context
     */
    public static void clearAllSP(Context context) {
        SPUtil.clear(context);
    }


    /**
     * 上传用户头像
     *
     * @param context
     * @param bitmap
     * @param listener
     */
    @Deprecated
    public static void updateHead(Activity context, Bitmap bitmap, HttpListener<String> listener) {
        String url = FunctionApi.getHttpIp(context) + Constant.Url.UPDATE_HEAD;
        String catalogId = StringUtil.getUUID();
        String pic = Base64Util.bitmapToBase64(bitmap);
        String moduleId = "app_sdk_photos";
        Map<String, String> map = new HashMap<>();
        map.put("catalogId", catalogId);
        map.put("moduleId", moduleId);
        map.put("fileData", pic);
        String json = new Gson().toJson(map);
        BaseHttp.getInstance().postSimpleJson(context, url, json, listener);
    }

    /**
     * 上传文件
     *
     * @param context
     * @param filePath
     * @param httpListener
     */
    public static void updateFile(Context context, String filePath, HttpListener<String> httpListener) {
        String url = FunctionApi.getHttpIp(context) + Constant.Url.UPLOAD_FILE + "?catalogId=" + StringUtil.getUUID() +
                "&moduleId=" + "upsoftsdk/app" + "&fileExtend=";
        Map<String, File> fileMap = new HashMap<>();
        fileMap.put("file", new File(filePath));
        BaseHttp.getInstance().postFile(context, url, fileMap, httpListener);
    }

    /**
     * 更新用户头像
     *
     * @param context
     * @param fileId
     * @param httpListener
     */
    public static void updatePhoto(Activity context, String fileId, HttpListener<String> httpListener) {
        String url = FunctionApi.getHttpIp(context) + Constant.Url.UPDATE_USERINFO;
        InfoBean infoBean = UserSP.getInfo(context);
        if (infoBean != null && infoBean.data != null) {
            InfoBean.DataBean dataBean = infoBean.data;
            if (dataBean.extInfo != null) {
                dataBean.extInfo.photo = fileId;
                String json = new Gson().toJson(dataBean);
                BaseHttp.getInstance().postJson(context, url, json, httpListener);
            }
        }
    }

    /**
     * 修改密码
     *
     * @param context
     * @param oldPsd
     * @param newPsd
     * @param httpListener
     */
    public static void updatePassword(Activity context, String oldPsd, String newPsd, HttpListener<String>
            httpListener) {
        String url = FunctionApi.getHttpIp(context) + Constant.Url.URL_CHANGEPSD;
        Map<String, String> map = new HashMap<>();
        map.put("oldPwd", Md5Util.getMD5String(oldPsd));
        map.put("newPwd", Md5Util.getMD5String(newPsd));
        map.put("confirmNewPwd", Md5Util.getMD5String(newPsd));
        String json = new Gson().toJson(map);
        BaseHttp.getInstance().postJson(context, url, json, httpListener);
    }


    /**
     * 获取插件列表数据
     *
     * @param mContext
     * @param listener
     */
    public static void getPlugInfo(Activity mContext, HttpListener<String> listener) {
        String url = FunctionApi.getHttpIp(mContext) + Constant.Url.PLUG_PAGE;
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", "1");
        map.put("pageSize", "20");
        map.put("param", new HashMap<>());
        String params = new Gson().toJson(map);
        BaseHttp.getInstance().postJson(mContext, url, params, listener);
    }

    /**
     * 获取下载列表数据
     *
     * @param mContext
     * @param listener
     */
    public static void getDownloadInfo(Activity mContext, HttpListener<String> listener) {
        String url = FunctionApi.getHttpIp(mContext) + Constant.Url.DOWNLOAD_PAGE;
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", "1");
        map.put("pageSize", "20");
        map.put("param", new HashMap<>());
        String params = new Gson().toJson(map);
        BaseHttp.getInstance().postJson(mContext, url, params, listener);
    }

    public static String COLOR_TITLE;
    public static String COLOR_BAR;
    public static String COLOR_BTN;
    public static String COLOR_TEXT_ICON;//部分字体及图标颜色
    public static int alpha = -1;

    /**
     * 将标题栏、状态栏、按钮、文字等颜色缓存到内存
     *
     * @param context
     */
    public static void cacheColorToMemory(Context context) {
        COLOR_TITLE = ColorSP.getTitleColor(context);
        COLOR_BAR = ColorSP.getBarColor(context);
        COLOR_BTN = ColorSP.getBtnColor(context);
        COLOR_TEXT_ICON = ColorSP.getTextIconColor(context);
        alpha = ColorSP.getAlpha(context);
    }

    public static String getCurrentTitleColor(Context context) {
        if (StringUtil.isBlank(COLOR_TITLE)) {
            return ColorSP.getTitleColor(context);
        }
        return COLOR_TITLE;
    }


    public static String getCurrentBarColor(Context context) {
        if (StringUtil.isBlank(COLOR_BAR)) {
            return ColorSP.getBarColor(context);
        }
        return COLOR_BAR;
    }

    public static String getCurrentBtnColor(Context context) {
        if (StringUtil.isBlank(COLOR_BTN)) {
            return ColorSP.getBtnColor(context);
        }
        return COLOR_BTN;
    }

    public static String getCurrentTextIconColor(Context context) {
        if (StringUtil.isBlank(COLOR_TEXT_ICON)) {
            return ColorSP.getBtnColor(context);
        }
        return COLOR_TEXT_ICON;
    }

    public static int getCurrentAlpha(Context context) {
        if (alpha == -1) {
            return ColorSP.getAlpha(context);
        }
        return alpha;
    }

    /**
     * 批量上传日志文件
     *
     * @param context
     * @param listener
     */
    public static void uploadLogFile(Context context, String uuid, HttpListener<String> listener) {
        String logPath = Constant.CACHE_LOGE_PATH;
        File path = new File(logPath);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File[] files = path.listFiles();// 读取文件夹下文件
            if (files != null && files.length > 0) {
                String url = FunctionApi.getHttpIp(context) + Constant.Url.URL_UPLOAD_FILE + "?catalogId=" + uuid +
                        "&moduleId=" + "bp/mobile" + "&fileExtend=";
                L.e("文件上传地址：" + url);
                Map<String, File> fileMap = new HashMap<>();
                for (File f : files) {
                    fileMap.put(f.getName(), f);
                }
                BaseHttp.getInstance().postFile(context, url, fileMap, listener);
            } else {
                L.e(logPath + "目录下没有日志文件");
            }
        } else {
            L.e("SD卡不存在或不可读");
        }
    }

    /**
     * 上传日志关系数据
     *
     * @param context
     * @param uuid
     * @param listener
     */
    public static void uploadLog(Context context, String uuid, HttpListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("packageName", AppUtil.getPackageName(context));
        map.put("systemVersion", "Android" + Build.VERSION.RELEASE);
        map.put("mobileType", Build.MODEL);
        map.put("batchId", uuid);
        String params = new Gson().toJson(map);
        L.e("崩溃日志上传参数：" + params);
        String url = FunctionApi.getHttpIp(context) + Constant.Url.URL_UPLOAD_LOG;
        BaseHttp.getInstance().postSimpleJson(context, url, params, listener);
    }

    public static String getAuthority(Context context) {
        return AppUtil.getPackageName(context) + FunctionApi.AUTHORITY;
    }

    /**
     * 根据文件ID查询文件信息
     * @param context
     * @param fileId
     * @param listener
     */
    public static void getFileInfoByFileId(Context context, String fileId,HttpListener<String> listener) {
        Map<String, String> map = new HashMap<>();
        map.put("fileId", fileId);
        String params = new Gson().toJson(map);
        L.e("根据ID查询文件信息参数：" + params);
        String url = FunctionApi.getHttpIp(context) + Constant.Url.URL_GET_FILEINFO;
        BaseHttp.getInstance().postSimpleJson(context, url, params, listener);
    }
}

