package com.oguzozkaraca.sqliteexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private DatabaseHelper databaseHelper;
    EditText isimText;
    EditText yasText;
    Button ekleButton;
    Button kayitGosterButton;
    TextView kayitlar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        isimText = (EditText)findViewById(R.id.isimText);
        yasText = (EditText)findViewById(R.id.yasText);
        ekleButton = (Button)findViewById(R.id.ekleButton);
        kayitGosterButton = (Button)findViewById(R.id.kayitGosterButton);
        kayitlar = (TextView)findViewById(R.id.kayitlar);

        ekleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                try {
                    ContentValues values = new ContentValues();

                    values.put("isim", isimText.getText().toString());
                    values.put("yas",yasText.getText().toString());
                    db.insert(DatabaseHelper.TABLE_NAME, null, values);
                    Toast.makeText(MainActivity.this, "Kayıt başarı ile eklendi", Toast.LENGTH_SHORT).show();
                    //Log.v("DBTEST", "KAYIT EKLENDİ");
                } catch (Exception e) {
                    Log.e("DBTEST", e.toString());
                }finally{
                    db.close();
                }
            }
        });


        kayitGosterButton.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, new String[]{"_id", "isim", "yas"}, null, null, null, null, null);
                startManagingCursor(cursor);
                StringBuilder builder = new StringBuilder("Kayitlar:\n");
                while(cursor.moveToNext()){
                    long id = cursor.getLong(cursor.getColumnIndex("_id"));
                    String ad = cursor.getString((cursor.getColumnIndex("isim")));
                    int yas = cursor.getInt((cursor.getColumnIndex("yas")));
                    builder.append(id).append(". ");
                    builder.append(ad).append(": ");
                    builder.append(yas).append("\n");
                }

                kayitlar.setText(builder);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
