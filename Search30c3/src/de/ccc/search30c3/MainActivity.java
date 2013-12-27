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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainActivity extends Activity {
	
	protected MainActivity mainActivity;
	SearchResultList results = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mainActivity = this;
		setContentView(R.layout.activity_main);
		EditText searchText = (EditText) findViewById(R.id.searchText);
		
		// On search listener
		searchText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				new SearchAsyncTask(mainActivity).execute(v
						.getText().toString());
				v.setEnabled(false);
				return false;
			}
		});

		// On click listener
		searchText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				EditText searchText = (EditText) v;
//				searchText.setText("");
			}
		});
		
		if ((null != savedInstanceState )
				&& (savedInstanceState.containsKey("data"))) {
			displayResults((SearchResultList) savedInstanceState.get("data"));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onPause() {
		Log.w("Test","onPause");
		super.onPause();
	}
	
	public void onResume() {
		super.onResume();
//		if (null != results) {
//			displayResults(results);
//		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		bundle.putSerializable("data", results);
	}
	
	public void displayResults(SearchResultList results2) {
		ListView resultListView = (ListView) findViewById(R.id.resultListView);

		this.results = results2;
		
		ResultListAdapter adapter = new ResultListAdapter(
				getApplicationContext(), results);

		resultListView.setAdapter(adapter);
		
		resultListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				String link = results.get(position).link;
				Intent webViewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
				startActivity(webViewIntent);
			}
		});
		
		EditText editText = (EditText) findViewById(R.id.searchText);
		editText.setEnabled(true);
	}

}
