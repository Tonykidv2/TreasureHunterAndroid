package edu.fullsail.mgems.cse.treasurehunter.ramoslebronanthony;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by TheNinjaFS1 on 8/4/17.
 */

public class Inventory extends Activity
{
    private int m_goldCount;
    private int m_potionCount;
    private int m_hipotionCount;
    private int m_XpotionCount;
    private int m_etherCount;
    private int m_hietherCount;

    private AlertDialog.Builder m_dialog;
    private ArrayList<CharSequence> m_items;
    private Button mDoneButton;

    public Inventory(Context _context)
    {
        m_goldCount = 0;
        m_potionCount = 0;
        m_hipotionCount = 0;
        m_XpotionCount = 0;
        m_etherCount = 0;
        m_hietherCount = 0;

        m_dialog = new AlertDialog.Builder(_context);
        m_items = new ArrayList<CharSequence>();
    }

    public void addGold()
    {
        m_goldCount++;
    }
    public void addPotion()
    {
        m_potionCount++;
    }
    public void addHiPotion()
    {
        m_hipotionCount++;
    }
    public void addXPotion()
    {
        m_XpotionCount++;
    }
    public void addEther()
    {
        m_etherCount++;
    }
    public void addHiEther()
    {
        m_hietherCount++;
    }
    public void addOtherItem(String _name)
    {
        m_items.add(_name + " ");
    }

    public void PrintInventory()
    {

        m_dialog.setTitle("Inventory");

        ArrayList<CharSequence> _temp = new ArrayList<CharSequence>();
        if(m_goldCount > 0)
            _temp.add("Gold x" + m_goldCount);
        if(m_potionCount > 0)
            _temp.add("Potion x" + m_potionCount);
        if(m_hipotionCount > 0)
            _temp.add("Hi-Potion x" + m_hipotionCount);
        if(m_XpotionCount > 0)
            _temp.add("X-Potion x" + m_XpotionCount);
        if(m_etherCount > 0)
            _temp.add("Ether x" + m_etherCount);
        if(m_hietherCount > 0)
            _temp.add("Hi-Ether x" + m_hietherCount);

        for (int i = 0; i < m_items.size(); i++)
        {
            _temp.add(m_items.get(i));
        }

        m_dialog.setMessage(_temp.toString());
        m_dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface _dialog, int id)
            {
                _dialog.dismiss();
            }
        });

        m_dialog.show();
    }
}
