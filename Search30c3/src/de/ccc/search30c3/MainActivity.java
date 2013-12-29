package de.ccc.search30c3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends Activity {
	
	private static int START_INDEX = 0;

	protected MainActivity mainActivity;
	private SearchResultList results = null;
	private String search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainActivity = this;

		results = new SearchResultList();
		setContentView(R.layout.activity_main);
		EditText searchText = (EditText) findViewById(R.id.searchText);

		// On search listener
		searchText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				results = new SearchResultList();
				search = v.getText().toString();
				new SearchAsyncTask(mainActivity).execute(search);
				disableInput();
				return false;
			}
		});

		// On click listener
		searchText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// EditText searchText = (EditText) v;
				// searchText.setText("");
			}
		});

		// Configure buttons
		Button buttonNext = (Button) findViewById(R.id.buttonNext);
		buttonNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new SearchAsyncTask(mainActivity).execute(search, ""
						+ (results.firstItemNumber + results.pageSize));
				disableInput();
			}
		});

		Button buttonLast = (Button) findViewById(R.id.buttonLast);
		buttonLast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new SearchAsyncTask(mainActivity).execute(search, ""
						+ (results.firstItemNumber - results.pageSize));
				disableInput();
			}
		});

		// Recover state
		if ((null != savedInstanceState)
				&& (savedInstanceState.containsKey("data"))) {
			results = (SearchResultList) savedInstanceState.get("data");
			if (null != results)
				displayResults(results);
			search = savedInstanceState.getString("search");
			if (null != search) {
				((EditText) findViewById(R.id.searchText)).setText(search);
			}
		}

		// adjust buttons
		buttonNext.setEnabled(results.firstItemNumber > START_INDEX);
		buttonLast.setEnabled((results.firstItemNumber > START_INDEX)
				&& ((results.firstItemNumber - START_INDEX ) > results.pageSize));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPause() {
		Log.w("Test", "onPause");
		super.onPause();
	}

	public void onResume() {
		super.onResume();
		// if (null != results) {
		// displayResults(results);
		// }
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		bundle.putSerializable("data", results);
		bundle.putString("search", search);
	}

	public void displayResults(SearchResultList results2) {

		if (null != results2) {
			ListView resultListView = (ListView) findViewById(R.id.resultListView);

			this.results = results2;

			ResultListAdapter adapter = new ResultListAdapter(
					getApplicationContext(), results);

			resultListView.setAdapter(adapter);
			resultListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long id) {
					String link = results.get(position).link;
					Intent webViewIntent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(link));
					startActivity(webViewIntent);
				}
			});
		}

		EditText editText = (EditText) findViewById(R.id.searchText);
		editText.setEnabled(true);

		updateButtons();
	}
	
	public void disableInput() {
		((EditText) findViewById(R.id.searchText)).setEnabled(false);
		((Button) findViewById(R.id.buttonLast)).setEnabled(false);
		((Button) findViewById(R.id.buttonNext)).setEnabled(false);
	}
	
	public void updateButtons() {
		((EditText) findViewById(R.id.searchText)).setEnabled(true);
		((Button) findViewById(R.id.buttonNext))
				.setEnabled((results.firstItemNumber + results.pageSize) < results.maxItems);
		if ((results.firstItemNumber >= results.pageSize + START_INDEX)) {
			((Button) findViewById(R.id.buttonLast)).setEnabled(true);
		} else {
			((Button) findViewById(R.id.buttonLast)).setEnabled(false);
		}
	}

}
