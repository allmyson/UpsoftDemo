package com.upsoft.demo.business.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;


/**
 * Copyright (c) 2015,重庆扬讯软件技术有限公司<br>
 * All rights reserved.<br>
 * <p/>
 * 文件名称：EpDbHelper<br>
 * 摘要：<br>
 * -------------------------------------------------------<br>
 * 当前版本：1.1.1<br>
 * 作者：李杰br>
 * 完成日期：2017/2/12<br>
 * -------------------------------------------------------<br>
 * 取代版本：1.1.0<br>
 * 原作者：李杰br<br>
 * 完成日期：2017/2/12<br>
 */


public class EpDbHelper extends SQLiteOpenHelper implements Serializable{
	private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;
	private static volatile EpDbHelper instance;

	public EpDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }


	public static EpDbHelper getDBOpenHelperInstance(Context context) {
		if (instance == null) {
			synchronized (EpDbHelper.class) {
				if (instance == null) {
					instance = new EpDbHelper(context);
				}
			}
		}
		return instance;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
//		//新建任务表
//		db.execSQL(SqlUtil.createTaskTable());
//		//取证
//		db.execSQL(SqlUtil.createEvidenceTable());
//		//检查清单
//		db.execSQL(SqlUtil.createCheckItemTable());
//		//常用软件表
//		db.execSQL(SqlUtil.createAppTable());
//		//本地软件表
//		db.execSQL(SqlUtil.createoFienAppTable());
//		//新建消息表
//		db.execSQL(MessageDB.createMessageTable());
//		//新建发现-问题
//		db.execSQL(SqlUtil.createFindProblem());
//		//搜索历史记录表
//		db.execSQL(SqlUtil.createSearchTable());
//		//前端取证表
//		db.execSQL(SqlUtil.createEvidenceTable2());
//		//数据字典表
//		db.execSQL(SqlUtil.creatDictionaryTable());
//		//审批数据字典表
//		db.execSQL(SqlUtil.creatSpDictionaryTable());
//		//检查项选项表
//		db.execSQL(SqlUtil.creatExamineOptionTable());
//		//查选项和检查项的关联关系
//		db.execSQL(SqlUtil.creatRelationTable());
//		//任务处置情况
//		db.execSQL(SqlUtil.creatTaskConditionTable());
//		//ZIP版本管理数据库操作类
//		db.execSQL(ZipVervisionDB.createTable());
//		//检查选项
//		db.execSQL(SqlUtil.createItemOptTable());
//		//检查模板项目关联
//		db.execSQL(SqlUtil.createRELATIONTable());
//		//电子标签表
//		db.execSQL(SqlUtil.creatNfcTable());
//		//离线城市表
//		db.execSQL(SqlUtil.createCityTable());
//		//离线任务详情表
//		db.execSQL(SqlUtil.createTaskDetailsTable());
//		//投诉信息表
//		db.execSQL(SqlUtil.createComplaintTable());
//		//问题信息表
//		db.execSQL(SqlUtil.createCurrentTable());
//		//新建页面统计表
//		db.execSQL(StatisticsDB.createStatisticsTable());

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}
}

