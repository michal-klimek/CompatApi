package com.binartech.CompatApi.newapi;

import com.binartech.CompatApi.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

public class ViewPagerInFrame extends Activity implements OnClickListener
{
	private ViewPager mPager;
	private ViewFlipper mFlipper;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager_in_frame);
		mFlipper = (ViewFlipper)findViewById(R.id.view_flipper);
		mPager = (ViewPager)findViewById(R.id.pager);
		mPager.setAdapter(new FlipAdapter());
		findViewById(R.id.button_show_trio).setOnClickListener(this);
		findViewById(R.id.button_show_warning).setOnClickListener(this);
		mPager.getAdapter().notifyDataSetChanged();
	}

	
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.button_show_trio:
			{
				mFlipper.setDisplayedChild(1);
			}break;
			case R.id.button_show_warning:
			{
				mFlipper.setDisplayedChild(0);
			}break;
		}
	}


	protected class FlipAdapter extends PagerAdapter
	{
		
		@Override
		public int getCount()
		{
			return 3;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View)object);
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			switch(position)
			{
				case 0:
				{
					return "Korki";
				}
				case 1:
				{
					return "LiveFeed";
				}
				case 2:
				{
					return "Statystyki";
				}
				default:
				{
					return null;
				}
			}
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			final View view;
			switch(position)
			{
				case 0:
				{
					view = getLayoutInflater().inflate(R.layout.korki, container, false);
				}
				break;
				case 1:
				{
					view = getLayoutInflater().inflate(R.layout.livefeed, container, false);
				}break;
				case 2:
				{
					view = getLayoutInflater().inflate(R.layout.statsy, container, false);
				}break;
				default:
				{
					return null;
				}
			}
			container.addView(view, 1);
			return view;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

		

		
		
	}
}
