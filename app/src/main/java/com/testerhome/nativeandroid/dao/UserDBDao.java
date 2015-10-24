package com.testerhome.nativeandroid.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.testerhome.nativeandroid.dao.UserDB;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_DB".
*/
public class UserDBDao extends AbstractDao<UserDB, Long> {

    public static final String TABLENAME = "USER_DB";

    /**
     * Properties of entity UserDB.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property User_id = new Property(0, Long.class, "user_id", true, "USER_ID");
        public final static Property Login = new Property(1, String.class, "login", false, "LOGIN");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Avatar_url = new Property(3, String.class, "avatar_url", false, "AVATAR_URL");
    };


    public UserDBDao(DaoConfig config) {
        super(config);
    }
    
    public UserDBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_DB\" (" + //
                "\"USER_ID\" INTEGER PRIMARY KEY ," + // 0: user_id
                "\"LOGIN\" TEXT," + // 1: login
                "\"NAME\" TEXT," + // 2: name
                "\"AVATAR_URL\" TEXT);"); // 3: avatar_url
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_DB\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, UserDB entity) {
        stmt.clearBindings();
 
        Long user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindLong(1, user_id);
        }
 
        String login = entity.getLogin();
        if (login != null) {
            stmt.bindString(2, login);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String avatar_url = entity.getAvatar_url();
        if (avatar_url != null) {
            stmt.bindString(4, avatar_url);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public UserDB readEntity(Cursor cursor, int offset) {
        UserDB entity = new UserDB( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // user_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // login
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // avatar_url
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, UserDB entity, int offset) {
        entity.setUser_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLogin(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAvatar_url(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(UserDB entity, long rowId) {
        entity.setUser_id(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(UserDB entity) {
        if(entity != null) {
            return entity.getUser_id();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
