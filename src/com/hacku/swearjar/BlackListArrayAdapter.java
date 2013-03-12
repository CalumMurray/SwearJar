package com.hacku.swearjar;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//Custom ArrayAdapter to hold multiple text views (split up the blacklistitem's 3 fields)
public class BlackListArrayAdapter extends ArrayAdapter<BlackListItem> {

	private Context context;
	private List<BlackListItem> values;
	
	public BlackListArrayAdapter(Context context, List<BlackListItem> values) {
		super(context, R.layout.list_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.text_left);
		TextView textView = (TextView) rowView.findViewById(R.id.text_centre);
		TextView textView = (TextView) rowView.findViewById(R.id.text_right);
		// //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		// textView.setText(values[position]);
		// // Change the icon for Windows and iPhone
		// String s = values[position];
		// if (s.startsWith("Windows7") || s.startsWith("iPhone")
		// || s.startsWith("Solaris")) {
		// imageView.setImageResource(R.drawable.no);
		// } else {
		// imageView.setImageResource(R.drawable.ok);
		// }

		return rowView;
	}
}
