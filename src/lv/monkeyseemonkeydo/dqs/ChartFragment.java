package lv.monkeyseemonkeydo.dqs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChartFragment extends Fragment {

	public static final String ARG_DATE = "date";
	private static final int[] CELLS = new int[] { R.id.c11, R.id.c12, R.id.c13, R.id.c14,
			R.id.c15, R.id.c16, R.id.c21, R.id.c22, R.id.c23, R.id.c24, R.id.c25, R.id.c26,
			R.id.c31, R.id.c32, R.id.c33, R.id.c34, R.id.c35, R.id.c36, R.id.c41, R.id.c42,
			R.id.c43, R.id.c44, R.id.c45, R.id.c46, R.id.c51, R.id.c52, R.id.c53, R.id.c54,
			R.id.c55, R.id.c56, R.id.c61, R.id.c62, R.id.c63, R.id.c64, R.id.c65, R.id.c66,
			R.id.c71, R.id.c72, R.id.c73, R.id.c74, R.id.c75, R.id.c76, R.id.c81, R.id.c82,
			R.id.c83, R.id.c84, R.id.c85, R.id.c86, R.id.c91, R.id.c92, R.id.c93, R.id.c94,
			R.id.c95, R.id.c96, R.id.cx1, R.id.cx2, R.id.cx3, R.id.cx4, R.id.cx5, R.id.cx6 };

	private String mDate;
	private StringBuffer mSelections;
	private int mScore = 0;

	public ChartFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Bundle args = getArguments();
		mDate = args.getString(ARG_DATE);
		SharedPreferences prefs = getActivity().getSharedPreferences("DQS", Context.MODE_PRIVATE);
		mSelections = new StringBuffer(prefs.getString(mDate,
				"000000000000000000000000000000000000000000000000000000000000"));
		Log.d("DQS", mDate + ": " + mSelections.toString());
		View v = inflater.inflate(R.layout.chart, null);

		for (int i = 0; i < CELLS.length; i++) {
			TextView cell = (TextView) v.findViewById(CELLS[i]);
			int index = Integer.parseInt((String) cell.getTag());
			if (mSelections.charAt(index) == '1') {
				cell.setBackgroundColor(getResources().getColor(R.color.dark));
				cell.setTextColor(getResources().getColor(android.R.color.white));
				mScore += Integer.parseInt(cell.getText().toString());
			}

			cell.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onCellClick(v);
				}
			});
		}

		TextView scoreView = (TextView) v.findViewById(R.id.score);
		scoreView.setText("" + mScore);
		return v;
	}

	public void onCellClick(View v) {
		TextView cell = (TextView) v;
		int index = Integer.parseInt((String) cell.getTag());

		// Flip it
		if (mSelections.charAt(index) == '1') {
			mSelections.setCharAt(index, '0');
			cell.setBackgroundColor(0);
			cell.setTextColor(getResources().getColor(android.R.color.black));
			mScore -= Integer.parseInt(cell.getText().toString());

		} else {
			mSelections.setCharAt(index, '1');
			cell.setBackgroundColor(getResources().getColor(R.color.dark));
			cell.setTextColor(getResources().getColor(android.R.color.white));
			mScore += Integer.parseInt(cell.getText().toString());
		}

		TextView scoreView = (TextView) getView().findViewById(R.id.score);
		scoreView.setText("" + mScore);

		// And save back
		new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				SharedPreferences prefs = getActivity().getSharedPreferences("DQS",
						Context.MODE_PRIVATE);
				SharedPreferences.Editor e = prefs.edit();
				e.putString(mDate, mSelections.toString());
				e.commit();
			}

		}.start();

	}

}
