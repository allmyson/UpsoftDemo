package com.upsoft.sdk.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2016,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：Sqlutil<br>
 * 摘要：数据库sql操作工具<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：彭粟<br>
 * 完成日期：2016/11/3<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：彭粟<br>
 * 完成日期：2016/11/3<br>
 */

public class SdkSqlutil {
    /**
     * @author 彭粟
     * @version 1.0
     * @Description: 新增  修改  删除 操作  true为执行正常  false 为执行失败
     * @return void
     * @time： 2016/11/3
     */
    public static boolean insertOrUpdateOrDeleteSql(String sql, SQLiteOpenHelper dbOpenHelper){
        boolean result=true;
        try{
            dbOpenHelper.getWritableDatabase().execSQL(sql);
        }catch (Exception e){
            result=false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @author 彭粟
     * @version 1.0
     * @Description: 新增  修改  删除 操作  true为执行正常  false 为执行失败  args是参数值数组
     * @return void
     * @time： 2016/11/3
     */
    public  static boolean insertOrUpdateOrDeleteSql(String sql,String[] args, SQLiteOpenHelper dbOpenHelper){
        boolean result=true;
        try{
            dbOpenHelper.getWritableDatabase().execSQL(sql,args);
        }catch (Exception e){
            result=false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @author 彭粟
     * @version 1.0
     * @Description: 创建表的方法 rue为执行正常  false 为执行失败
     * @return void
     * @time： 2016/11/3
     */

    public  static boolean createTable(String sql,SQLiteOpenHelper dbOpenHelper) {
        boolean result=true;
        try{
            dbOpenHelper.getWritableDatabase().execSQL(sql);
        }catch (Exception e){
            result=false;
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @author 彭粟
     * @version 1.0
     * @Description: 判断表是否存在
     * @return void
     * @time： 2016/10/26
     */
    public  static boolean tableIsExist(String tableName,SQLiteOpenHelper dbOpenHelper){
        boolean result = false;
        if(TextUtils.isEmpty(tableName)){
            return false;
        }
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tableName.trim()+"' ";
            Cursor cursor = dbOpenHelper.getWritableDatabase().rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }
            cursor.close();
        } catch (Exception e) {

        }
        return result;
    }


    /**
     * @author 彭粟
     * @version 1.0
     * @Description: 数据库通用查询方法
     * @return void
     * @time： 2016/10/26
     */
    public  static List<Map<String,Object>> getList(String sql, String[] params, SQLiteOpenHelper dbOpenHelper, SdkCallbacks sdkCallbacks){
        List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
        try{
            if(TextUtils.isEmpty(sql)){
                sdkCallbacks.onFail("sql语句不能为空,数据获取失败");
            } else{
                Cursor cursor= dbOpenHelper.getWritableDatabase().rawQuery(sql,params);
                resultList.addAll(getSearchResult(cursor));
            }
            sdkCallbacks.onSuccess("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            sdkCallbacks.onFail("sql语句出错");
        }
        return resultList;
    }
    /**
     * @author 彭粟
     * @version 1.0
     * @Description: 数据库通用查询方法
     * @return void
     * @time： 2016/10/26
     */
    public  static List<Map<String,Object>> getList(String sql,SQLiteOpenHelper dbOpenHelper, SdkCallbacks sdkCallbacks){
        List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
        try{
            if(TextUtils.isEmpty(sql)){
                sdkCallbacks.onFail("0");
            } else{
                Cursor cursor= dbOpenHelper.getWritableDatabase().rawQuery(sql,new String[]{});
                resultList.addAll(getSearchResult(cursor));
                sdkCallbacks.onSuccess("查询成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            sdkCallbacks.onFail("0");
        }
        return resultList;
    }
    /**
     * @author 彭粟
     * @version 1.0
     * @Description: 根据表的列名获取每一列的数据
     * @param cursor
     * @return void
     * @time： 2016/10/26
     */

    public  static List<Map<String,Object>> getSearchResult(Cursor cursor){
        //获取表的所有列名
        List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
        Map<String,Object> map=null;
        try{
            if(cursor!=null){
                String[] columnNames=cursor.getColumnNames();
                while(cursor.moveToNext()){
                    map=new HashMap<String,Object>();
                    for (String str:columnNames){
                        map.put(str,cursor.getString(cursor.getColumnIndex(str)));
                    }
                    resultList.add(map);
                }
                cursor.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * 得到数据库下所有的表名
     *
     * @return
     */
    public static List<String> getTableNames(SQLiteDatabase db) {
        List<String> list = new ArrayList<String>();
        String name = null;
        if (db != null) {
            Cursor cursor = db.rawQuery("select name from sqlite_master where type='table' order by name", null);
            while (cursor.moveToNext()) {
                //遍历出表名
                name = cursor.getString(0);
                list.add(name);
            }
            if (list.contains("android_metadata")) {
                list.remove("android_metadata");
            }
            //删除数据的时候不删除用户行为分析表
            /**2016-06-02 by lh**/
            if(list.contains("statistics")){
                list.remove("statistics");
            }
        }
        return list;
    }

    /**
     * 删除所有表的数据
     *
     * @return
     */
    public static boolean deleteAllTable(Context context, SQLiteDatabase db) {
        List<String> list = getTableNames(db);
        if (list != null && list.size() > 0) {
            for (String s : list) {
                db.execSQL("delete from " + s);
            }
        }
        return true;
    }
}
