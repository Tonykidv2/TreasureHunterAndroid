package edu.fullsail.mgems.cse.treasurehunter.ramoslebronanthony;

import android.graphics.Canvas;
import android.provider.Settings;
import android.view.SurfaceHolder;

/**
 * Created by TheNinjaFS1 on 8/2/17.
 */

public class MainThread extends Thread
{

    public static final int MAX_FPS = 30;
    private double averagefps;
    private SurfaceHolder surfaceholder;
    private DrawSurface drawsurface;
    private boolean running;
    public static Canvas canvas;

    public void setRunning(boolean _run)
    {
        running = _run;
    }

    public MainThread(SurfaceHolder _SurfHolder, DrawSurface _DrawSurf)
    {
        super();
        surfaceholder = _SurfHolder;
        drawsurface = _DrawSurf;
    }

    @Override
    public void run()
    {
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTimer;
        int FrameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while(running)
        {
            startTime = System.nanoTime();
            canvas = null;

            try
            {
                canvas = this.surfaceholder.lockCanvas();
                synchronized (surfaceholder)
                {
                    this.drawsurface.update();
                    this.drawsurface.draw(canvas);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (canvas != null)
                {
                    try
                    {
                        surfaceholder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime)/ 1000000;
            waitTimer = targetTime - timeMillis;
            try
            {
                if(waitTimer > 0)
                {
                    this.sleep(waitTimer);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            FrameCount++;

            if(FrameCount == MAX_FPS)
            {
                averagefps = 1000/ ((totalTime/FrameCount)/1000000);
                FrameCount = 0;
                totalTime = 0;
                System.out.println(averagefps);
            }
        }
    }
}
