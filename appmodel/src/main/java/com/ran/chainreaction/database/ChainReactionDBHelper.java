package com.ran.chainreaction.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ranjith on 17/12/15.
 * <p/>
 * Class Responsible for the ChainReaction SQL Table Creation.
 */
public class ChainReactionDBHelper extends SQLiteOpenHelper {

    public static final String TABLE_GAME = "game_table";
    public static final String GAME_ID = "game_id";
    public static final String GAME_NAME = "game_name";
    public static final String GAME_TIME = "game_time";
    public static final String GAME_CURRENT_PLAYER = "game_current_player";
    public static final String GAME_PLAYERS = "game_players";
    public static final String GAME_GRID_TYPE = "game_grid";
    public static final String GAME_ORB_TYPE = "game_Orb";
    public static final String GAME_BOX_INFO = "game_box_info";
    public static final String GAME_CELL_INFOS = "game_cell_infos";
    private static final String DATABASE_NAME = "chainReaction";
    private static final int DATABASE_VERSION = 1;
    /**
     * Game Table Creation ..
     */
    private String CREATE_GAME_TABLE = "CREATE TABLE " + TABLE_GAME + " (" +
        GAME_ID + " INTEGER PRIMARY KEY," +
        GAME_NAME + " TEXT," +
        GAME_TIME + " INTEGER" +
        GAME_CURRENT_PLAYER + " TEXT," +
        GAME_PLAYERS + " TEXT," +
        GAME_GRID_TYPE + " INTEGER," +
        GAME_ORB_TYPE + " INTEGER," +
        GAME_BOX_INFO + " TEXT" +
        GAME_CELL_INFOS + " TEXT" + " )";

    /**
     * Game Table Creation ..
     */
    private String DROP_GAME_TABLE = "DROP TABLE IF EXISTS " + TABLE_GAME;

    public ChainReactionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_GAME_TABLE);
        onCreate(db);
    }
}
