package com.binartech.CompatApi.newapi;

import java.util.ArrayList;

import com.binartech.CompatApi.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.os.Bundle;

public class PaneList extends FragmentActivity
{

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		String url = getIntent().getStringExtra("url");
		if(url != null)
		{
			WebView wv = new WebView(this);
			wv.setWebViewClient(new WebViewClient() {
		        @Override
		        public boolean shouldOverrideUrlLoading(WebView view, String url) {
		           return super.shouldOverrideUrlLoading(view, url);
		        }
		    });
			LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			wv.setLayoutParams(lp);
			wv.loadUrl(url);
			setContentView(wv);
		}
		else
		{
			setContentView(R.layout.activity_pane_list);
		}
	}

	public static class ItemList extends ListFragment
	{

		private boolean mDualPane;
		private boolean mAddedPane;
		

		@Override
		public void onActivityCreated(Bundle savedInstanceState)
		{
			mDualPane = getActivity().findViewById(R.id.fragment_content_pane) != null;
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), !mDualPane ? android.R.layout.simple_list_item_1 : android.R.layout.simple_list_item_single_choice, new String[] {"Onet", "Dziennik.pl", "SpeedAlarm.pl"});
			setListAdapter(adapter);			
			if(mDualPane)
			{
				getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			}
			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id)
		{
			switch(position)
			{
				case 0:
				{
					updateContent("http://www.onet.pl");
				}break;
				case 1:
				{
					updateContent("http://www.dziennik.pl");
				}break;
				case 2:
				{
					updateContent("http://www.speedalarm.pl");
				}break;
			}
		}

		private void updateContent(String url)
		{
			if(mDualPane)
			{
				PageView view = new PageView();
				Bundle args = new Bundle();
				args.putString("url", url);
				view.setArguments(args);
				if (mAddedPane)
				{
					getFragmentManager().beginTransaction().replace(R.id.fragment_content_pane, view).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
				}
				else
				{
					getFragmentManager().beginTransaction().add(R.id.fragment_content_pane, view).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
				}
			}
			else
			{
				Intent intent = new Intent(getActivity(), PaneList.class);
				intent.putExtra("url", url);
				startActivity(intent);
			}
		}
	}

	public static class PageView extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			WebView wv = new WebView(getActivity());
			wv.setWebViewClient(new WebViewClient() {
		        @Override
		        public boolean shouldOverrideUrlLoading(WebView view, String url) {
		           return super.shouldOverrideUrlLoading(view, url);
		        }
		    });

			String mURL = getArguments().getString("url");
			if (mURL != null)
			{
				wv.loadUrl(mURL);
			}
			return wv;
		}

		
	}
}
