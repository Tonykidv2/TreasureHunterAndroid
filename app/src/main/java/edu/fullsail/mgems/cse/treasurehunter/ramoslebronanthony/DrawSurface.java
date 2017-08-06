package edu.fullsail.mgems.cse.treasurehunter.ramoslebronanthony;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.constraint.solver.widgets.WidgetContainer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.util.AttributeSet;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by TheNinjaFS1 on 8/2/17.
 */

public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener
{

    public ArrayList<Item> m_itemsinGround;
    public ArrayList<Item> m_foundItems;
    public ArrayList<Point> m_digPoints;

    public int m_height, m_width, m_radius;
    Inventory m_inv;
    Bitmap mBMPField;
    Bitmap mBMPHole;
    Paint p = new Paint();

    public DrawSurface(Context context) {
        super(context);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init(context);
    }
    public DrawSurface(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init(context);
    }
    public DrawSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        init(context);
    }

    private void init(Context _contex)
    {
        mBMPField = BitmapFactory.decodeResource(getResources(), R.drawable.field);
        mBMPHole = BitmapFactory.decodeResource(getResources(), R.drawable.hole);

        m_itemsinGround = LoadItems();
        m_digPoints = new ArrayList<Point>();
        m_foundItems = new ArrayList<Item>();
        m_inv = new Inventory(_contex);
        m_radius = 30;
        //Make sure onDraw is called when I touch screen
        setWillNotDraw(false);

    }

    @Override
    public void onDraw(Canvas canvas)
    {
        System.out.println("OnDraw");
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        canvas.drawBitmap(mBMPField,0,0, p);
        for (int i = 0; i < m_digPoints.size() ; i++)
        {
            canvas.drawBitmap(mBMPHole, m_digPoints.get(i).x - (mBMPHole.getWidth()/2),
                    m_digPoints.get(i).y - (mBMPHole.getHeight()/2), p);
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        //System.out.println("surfaceCreating");
        Canvas canvas = null;
        try
        {
            canvas = surfaceHolder.lockCanvas(null);
            float scaleH = (float)mBMPField.getHeight()/(float)getHeight();
            float scaleW = (float)mBMPField.getWidth()/(float)getWidth();
            int newWidth = Math.round(mBMPField.getWidth()/scaleW);
            int newHeight = Math.round(mBMPField.getHeight()/scaleH);
            Bitmap Scaled = Bitmap.createScaledBitmap(mBMPField, newWidth, newHeight, true);
            mBMPField = Scaled;
            for (int i =0;i < m_itemsinGround.size(); i++)
            {
                m_itemsinGround.get(i).m_x = (int)(Math.random() * (float)mBMPField.getWidth());
                m_itemsinGround.get(i).m_y = (int)(Math.random() * (float)mBMPField.getHeight());
            }
            synchronized (surfaceHolder)
            {
                System.out.println("drawing");
                //onDraw(canvas);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("errorCreating");
        }
        finally
        {
            if (canvas != null)
            {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
    {
        System.out.println("drawing");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {

        //System.out.println("Touching Surface View");
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            Item item = null;
            int dx, dy;
            for (int i = m_itemsinGround.size() - 1; i >= 0; i--) {
                item = m_itemsinGround.get(i);
                dx = item.m_x - (int) motionEvent.getX();
                dy = item.m_y - (int) motionEvent.getY();

                if ((dx) * (dx) + (dy) * (dy) < m_radius * m_radius)
                {
                    //Found Item
                    Toast.makeText(getContext(), "Found " +
                            item.m_name, Toast.LENGTH_LONG).show();
                    m_foundItems.add(item);
                    if(item.m_name.equals("Gold"))
                        m_inv.addGold();
                    else if(item.m_name.equals("Potion"))
                        m_inv.addPotion();
                    else if(item.m_name.equals("Hi-Potion"))
                        m_inv.addHiPotion();
                    else if(item.m_name.equals("X-Potion"))
                        m_inv.addXPotion();
                    else if(item.m_name.equals("Ether"))
                        m_inv.addEther();
                    else if(item.m_name.equals("Hi-Ether"))
                        m_inv.addHiEther();
                    else
                        m_inv.addOtherItem(item.m_name);

                    m_itemsinGround.remove(i);
                }
            }
            Point _point = new Point((int) motionEvent.getX(), (int) motionEvent.getY());
            m_digPoints.add(_point);
            invalidate();
        }
        return false;
    }

    public void PrintInv()
    {
        m_inv.PrintInventory();
    }

    public void update()
    {

    }


    private ArrayList<Item> LoadItems()
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
