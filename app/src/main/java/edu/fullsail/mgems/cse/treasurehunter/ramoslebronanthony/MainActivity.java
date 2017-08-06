package edu.fullsail.mgems.cse.treasurehunter.ramoslebronanthony;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener/*extends Activity*/{

    Button minvButton;
    Button mcredButton;
    Bitmap mBMPField;
    Bitmap mBMPHole;
    DrawSurface m_Field;
    AlertDialog.Builder _dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Making sure the listener is looking at the button
        mcredButton = ((Button)findViewById(R.id.btnCredits));
        mcredButton.setOnTouchListener(this);

        //Making sure the listener is looking at the button
        minvButton = ((Button)findViewById(R.id.btnInventory));
        minvButton.setOnTouchListener(this);

        _dialog = new AlertDialog.Builder(this);
        _dialog.setTitle("Made by");
        _dialog.setMessage("Anthony Ramoslebron\nMGMS | APM\n8/1/2017");
        _dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface _dialog, int id)
            {
                _dialog.dismiss();
            }
        });

    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN
                && view.getId() == R.id.btnInventory)
        {
            ((DrawSurface)findViewById(R.id.surfaceView)).PrintInv();
        }
        else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN
                && view.getId() == R.id.btnCredits)
        {
            _dialog.show();
        }

        return false;
    }


    public ArrayList<Item> LoadItems()
    {

        InputStream input = getResources().openRawResource(R.raw.items);
        BufferedReader reader = null;
        ArrayList<Item> items = new ArrayList<Item>();
        String line;

        try
        {
            reader = new BufferedReader(new InputStreamReader(input));
            while((line = reader.readLine()) != null)
            {
                items.add(new Item(line));
            }
        }
        catch (Exception e)
        {
            Log.e("MainActivity", "Reading Failed", e);
        }
        finally
        {
            try
            {
                if(reader != null)
                    reader.close();

            }
            catch(Exception e)
            {
                Log.e("MainActivity", "Reading Failed", e);
            }
        }

        return items;
    }
}
