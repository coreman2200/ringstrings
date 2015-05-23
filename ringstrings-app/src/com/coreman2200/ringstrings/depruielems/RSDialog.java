package com.coreman2200.ringstrings.depruielems;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coreman2200.ringstrings.entities.Entity;
import com.coreman2200.ringstrings.entities.Tags;

import java.util.LinkedList;
import java.util.ListIterator;

public class RSDialog extends Dialog
  implements View.OnClickListener
{
  private static final int _ALPHA = 1996488704;
  private Button back;
  private final Context c;
  private Entity entFocus;
  private LinearLayout ll;
  private ScrollView sv;

  public RSDialog(Context paramContext, Entity paramEntity)
  {
    super(paramContext, 2131296256);
    this.c = paramContext;
    this.entFocus = paramEntity;
    this.sv = new ScrollView(this.c);
    this.ll = new LinearLayout(this.c);
    this.sv.setFillViewport(true);
    this.ll.setOrientation(1);
    this.sv.addView(this.ll);
    createView();
    setContentView(this.sv);
  }

  private LinearLayout SetDesc(String paramString, int paramInt)
  {
    LinearLayout localLinearLayout = new LinearLayout(this.c);
    localLinearLayout.setOrientation(0);
    TextView localTextView = new TextView(this.c);
    localTextView.setShadowLayer(0.0F, 0.0F, 0.0F, 0);
    localTextView.setTypeface(Typeface.DEFAULT, 2);
    localTextView.setTextColor(-1 - paramInt / 4);
    localTextView.setText(paramString);
    localLinearLayout.addView(localTextView);
    return localLinearLayout;
  }

  private LinearLayout SetHeader(String paramString, int paramInt)
  {
    LinearLayout localLinearLayout = new LinearLayout(this.c);
    localLinearLayout.setOrientation(0);
    localLinearLayout.setBackgroundColor(paramInt);
    ImageView localImageView = new ImageView(this.c);
    localLinearLayout.addView(localImageView);
    TextView localTextView = new TextView(this.c);
    localTextView.setTextColor(-1);
    localTextView.setText(paramString);
    localLinearLayout.addView(localTextView);
    return localLinearLayout;
  }

  private LinearLayout SetTags(LinkedList<Tags> paramLinkedList)
  {
    LinearLayout localLinearLayout = new LinearLayout(this.c);
    localLinearLayout.setOrientation(1);
    ListIterator localListIterator = paramLinkedList.listIterator();
    while (true)
    {
      if (!localListIterator.hasNext())
        return localLinearLayout;
      localLinearLayout.addView(TagView((Tags)localListIterator.next()));
    }
  }

  private TextView TagView(Tags paramTags)
  {
    boolean bool = false;
    TextView localTextView = new TextView(this.c);
    localTextView.setText(paramTags.getName());
    localTextView.setTextColor(16777215);
    if (bool)
    {
      localTextView.setFocusable(true);

    }
    return localTextView;
  }

  public void clearView()
  {
    this.sv.removeAllViews();
  }

  public void createView()
  {
    Entity localEntity1 = this.entFocus.getPrevEnt();
    String str1 = this.entFocus.getName();
    String str2 = this.entFocus.getDesc();
    int i = this.entFocus.getColor();
    this.sv.setBackgroundColor(_ALPHA + i);
    this.ll.addView(SetHeader(str1, i));
    this.ll.addView(SetDesc(str2, i));
    LinearLayout localLinearLayout1 = new LinearLayout(this.c);

  }

  public void onClick(View paramView)
  {
    if (paramView == this.back)
      dismiss();
  }
}

/* Location:           C:\Users\higgie\Documents\htc\DEV\RingStrings\classes_dex2jar.jar
 * Qualified Name:     com.coreman2200.ringstrings.depruielems.RSDialog
 * JD-Core Version:    0.6.0
 */