package com.yuyang.db.base;

import com.yuyang.MyApplication;
import com.yuyang.db.bean.DaoMaster;
import com.yuyang.db.bean.DaoSession;
import com.yuyang.db.bean.UserDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by yuyang on 2017/4/13.
 */

public class DBManager {
    private static final String DB_NAME = "yuyang_db";
    private DaoSession mDaoSession;

    public static DBManager getInstance(){
        return SingletonHolder.mInstance;
    }

    private DBManager(){
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), DB_NAME, null);
        //!--yuyang 当有数据库升级需求的时候才会使用下面这句
        //MyUpdateOpenHelper openHelper = new MyUpdateOpenHelper(MyApplication.getInstance(), DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
        mDaoSession = daoMaster.newSession();
    }

    private static class SingletonHolder{
        private static final DBManager mInstance = new DBManager();
    }

    public UserDao getUserDao(){
        return mDaoSession.getUserDao();
    }
}
