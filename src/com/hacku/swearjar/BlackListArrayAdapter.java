package com.hacku.swearjar;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Custom ArrayAdapter to hold multiple text views (split up the blacklistitem's 4 fields)
 */
public class BlackListArrayAdapter extends ArrayAdapter<BlackListItem> {

	private Context context;
	private ArrayList<BlackListItem> values;
	
	public BlackListArrayAdapter(Context context, ArrayList<BlackListItem> values) {
		super(context, R.layout.list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.list_item, parent, false);
		TextView[] textView = new TextView[4];
		textView[0] = (TextView) rowView.findViewById(R.id.text_word);
		textView[1] = (TextView) rowView.findViewById(R.id.text_occurrence);
		textView[2] = (TextView) rowView.findViewById(R.id.text_charge);
		textView[3] = (TextView) rowView.findViewById(R.id.text_total_charge);
		
		textView[0].setText(values.get(index).getWord());
		textView[1].setText(values.get(index).getOccurrences());
		textView[2].setText(values.get(index).formatCharge());
		textView[3].setText(values.get(index).formatTotalCharge());

		return rowView;
	}
}
