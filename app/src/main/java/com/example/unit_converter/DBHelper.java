package com.example.unit_converter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "unitConverter.db";
    private static final int DATABASE_VERSION = 6; // Incremented for the length table addition

    // Table for Temperature conversions
    private static final String TABLE_TEMPERATURE = "TemperatureConversions";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_FROM_UNIT = "FromUnit";
    private static final String COLUMN_TO_UNIT = "ToUnit";
    private static final String COLUMN_INPUT_VALUE = "InputValue";
    private static final String COLUMN_OUTPUT_VALUE = "OutputValue";

    // Table for Mass conversions
    private static final String TABLE_MASS = "MassConversions";
    private static final String COLUMN_MASS_ID = "ID";
    private static final String COLUMN_MASS_FROM_UNIT = "FromUnit";
    private static final String COLUMN_MASS_TO_UNIT = "ToUnit";
    private static final String COLUMN_MASS_INPUT_VALUE = "InputValue";
    private static final String COLUMN_MASS_OUTPUT_VALUE = "OutputValue";

    // Table for Speed conversions
    private static final String TABLE_SPEED = "SpeedConversions";
    private static final String COLUMN_SPEED_ID = "ID";
    private static final String COLUMN_SPEED_FROM_UNIT = "FromUnit";
    private static final String COLUMN_SPEED_TO_UNIT = "ToUnit";
    private static final String COLUMN_SPEED_INPUT_VALUE = "InputValue";
    private static final String COLUMN_SPEED_OUTPUT_VALUE = "OutputValue";

    // Table for Area conversions
    private static final String TABLE_AREA = "AreaConversions";
    private static final String COLUMN_AREA_ID = "ID";
    private static final String COLUMN_AREA_FROM_UNIT = "FromUnit";
    private static final String COLUMN_AREA_TO_UNIT = "ToUnit";
    private static final String COLUMN_AREA_INPUT_VALUE = "InputValue";
    private static final String COLUMN_AREA_OUTPUT_VALUE = "OutputValue";

    // Table for Frequency conversions
    private static final String TABLE_FREQUENCY = "FrequencyConversions";
    private static final String COLUMN_FREQUENCY_ID = "ID";
    private static final String COLUMN_FREQUENCY_FROM_UNIT = "FromUnit";
    private static final String COLUMN_FREQUENCY_TO_UNIT = "ToUnit";
    private static final String COLUMN_FREQUENCY_INPUT_VALUE = "InputValue";
    private static final String COLUMN_FREQUENCY_OUTPUT_VALUE = "OutputValue";

    // Table for Length conversions
    private static final String TABLE_LENGTH = "LengthConversions";
    private static final String COLUMN_LENGTH_ID = "ID";
    private static final String COLUMN_LENGTH_FROM_UNIT = "FromUnit";
    private static final String COLUMN_LENGTH_TO_UNIT = "ToUnit";
    private static final String COLUMN_LENGTH_INPUT_VALUE = "InputValue";
    private static final String COLUMN_LENGTH_OUTPUT_VALUE = "OutputValue";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Temperature table
        String createTemperatureTable = "CREATE TABLE " + TABLE_TEMPERATURE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FROM_UNIT + " TEXT, "
                + COLUMN_TO_UNIT + " TEXT, "
                + COLUMN_INPUT_VALUE + " TEXT, "
                + COLUMN_OUTPUT_VALUE + " TEXT)";
        db.execSQL(createTemperatureTable);

        // Create Mass table
        String createMassTable = "CREATE TABLE " + TABLE_MASS + " ("
                + COLUMN_MASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MASS_FROM_UNIT + " TEXT, "
                + COLUMN_MASS_TO_UNIT + " TEXT, "
                + COLUMN_MASS_INPUT_VALUE + " TEXT, "
                + COLUMN_MASS_OUTPUT_VALUE + " TEXT)";
        db.execSQL(createMassTable);

        // Create Speed table
        String createSpeedTable = "CREATE TABLE " + TABLE_SPEED + " ("
                + COLUMN_SPEED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SPEED_FROM_UNIT + " TEXT, "
                + COLUMN_SPEED_TO_UNIT + " TEXT, "
                + COLUMN_SPEED_INPUT_VALUE + " TEXT, "
                + COLUMN_SPEED_OUTPUT_VALUE + " TEXT)";
        db.execSQL(createSpeedTable);

        // Create Area table
        String createAreaTable = "CREATE TABLE " + TABLE_AREA + " ("
                + COLUMN_AREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_AREA_FROM_UNIT + " TEXT, "
                + COLUMN_AREA_TO_UNIT + " TEXT, "
                + COLUMN_AREA_INPUT_VALUE + " TEXT, "
                + COLUMN_AREA_OUTPUT_VALUE + " TEXT)";
        db.execSQL(createAreaTable);

        // Create Frequency table
        String createFrequencyTable = "CREATE TABLE " + TABLE_FREQUENCY + " ("
                + COLUMN_FREQUENCY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FREQUENCY_FROM_UNIT + " TEXT, "
                + COLUMN_FREQUENCY_TO_UNIT + " TEXT, "
                + COLUMN_FREQUENCY_INPUT_VALUE + " TEXT, "
                + COLUMN_FREQUENCY_OUTPUT_VALUE + " TEXT)";
        db.execSQL(createFrequencyTable);

        // Create Length table
        String createLengthTable = "CREATE TABLE " + TABLE_LENGTH + " ("
                + COLUMN_LENGTH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LENGTH_FROM_UNIT + " TEXT, "
                + COLUMN_LENGTH_TO_UNIT + " TEXT, "
                + COLUMN_LENGTH_INPUT_VALUE + " TEXT, "
                + COLUMN_LENGTH_OUTPUT_VALUE + " TEXT)";
        db.execSQL(createLengthTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMPERATURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPEED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AREA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FREQUENCY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LENGTH);
        onCreate(db);
    }

    // Insert method for Temperature conversions
    public boolean insertTemperatureConversion(String fromUnit, String toUnit, String inputValue, String outputValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FROM_UNIT, fromUnit);
        values.put(COLUMN_TO_UNIT, toUnit);
        values.put(COLUMN_INPUT_VALUE, inputValue);
        values.put(COLUMN_OUTPUT_VALUE, outputValue);

        long result = db.insert(TABLE_TEMPERATURE, null, values);
        return result != -1; // returns true if insertion was successful
    }

    // Insert method for Mass conversions
    public boolean insertMassConversion(String fromUnit, String toUnit, String inputValue, String outputValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MASS_FROM_UNIT, fromUnit);
        values.put(COLUMN_MASS_TO_UNIT, toUnit);
        values.put(COLUMN_MASS_INPUT_VALUE, inputValue);
        values.put(COLUMN_MASS_OUTPUT_VALUE, outputValue);

        long result = db.insert(TABLE_MASS, null, values);
        return result != -1; // returns true if insertion was successful
    }

    // Insert method for Speed conversions
    public boolean insertSpeedConversion(String fromUnit, String toUnit, String inputValue, String outputValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SPEED_FROM_UNIT, fromUnit);
        values.put(COLUMN_SPEED_TO_UNIT, toUnit);
        values.put(COLUMN_SPEED_INPUT_VALUE, inputValue);
        values.put(COLUMN_SPEED_OUTPUT_VALUE, outputValue);

        long result = db.insert(TABLE_SPEED, null, values);
        return result != -1; // returns true if insertion was successful
    }

    // Insert method for Area conversions
    public boolean insertAreaConversion(String fromUnit, String toUnit, String inputValue, String outputValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AREA_FROM_UNIT, fromUnit);
        values.put(COLUMN_AREA_TO_UNIT, toUnit);
        values.put(COLUMN_AREA_INPUT_VALUE, inputValue);
        values.put(COLUMN_AREA_OUTPUT_VALUE, outputValue);

        long result = db.insert(TABLE_AREA, null, values);
        return result != -1; // returns true if insertion was successful
    }

    // Insert method for Frequency conversions
    public boolean insertFrequencyConversion(String fromUnit, String toUnit, String inputValue, String outputValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FREQUENCY_FROM_UNIT, fromUnit);
        values.put(COLUMN_FREQUENCY_TO_UNIT, toUnit);
        values.put(COLUMN_FREQUENCY_INPUT_VALUE, inputValue);
        values.put(COLUMN_FREQUENCY_OUTPUT_VALUE, outputValue);

        long result = db.insert(TABLE_FREQUENCY, null, values);
        return result != -1; // returns true if insertion was successful
    }

    // Insert method for Length conversions
    public boolean insertLengthConversion(String fromUnit, String toUnit, String inputValue, String outputValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LENGTH_FROM_UNIT, fromUnit);
        values.put(COLUMN_LENGTH_TO_UNIT, toUnit);
        values.put(COLUMN_LENGTH_INPUT_VALUE, inputValue);
        values.put(COLUMN_LENGTH_OUTPUT_VALUE, outputValue);

        long result = db.insert(TABLE_LENGTH, null, values);
        return result != -1; // returns true if insertion was successful
    }
}
