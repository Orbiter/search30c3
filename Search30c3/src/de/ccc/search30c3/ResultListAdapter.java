package de.ccc.search30c3;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResultListAdapter extends BaseAdapter {

	private ArrayList<SearchResultItem> listData;
	private LayoutInflater layoutInflater;
	private Context context;

	public ResultListAdapter(Context ctx, ArrayList<SearchResultItem> data) {
		this.context = ctx;
		this.listData = data;
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Initialize view
		if (null == convertView) {
			if (null == layoutInflater) {
				layoutInflater = (LayoutInflater) context
						.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			}
			convertView = layoutInflater.inflate(R.layout.search_result_view,
					null);
		}

		// Get views
		TextView titleView = (TextView) convertView
				.findViewById(R.id.textView_title);
		TextView descriptionView = (TextView) convertView
				.findViewById(R.id.textView_description);

		// Fill views
		titleView.setText(listData.get(position).title);
		Spanned formattedDescr = Html.fromHtml(listData.get(position).description);
		descriptionView.setText(formattedDescr);

		// Return view
		return convertView;
	}

}