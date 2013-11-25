package de.messinger.dome.iHazNews;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.messinger.dome.iHazNews.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TitleActivity extends Activity implements OnItemClickListener {

	private static final String TAG = "TitleActivity";
//	private TextView txt;
	private ListView listTitles;
	private ArrayAdapter<String> adapter;
	private Document doc;
	private FeedReaderApplication app;
//	private int count = 0;

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);

//		txt = (TextView) findViewById(R.id.txt);
		listTitles = (ListView) findViewById(R.id.listTitles);

		app = (FeedReaderApplication) getApplication();
		app.registerTitleActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
//		startService(new Intent(this, UpdateService.class));
		doc = FeedRetriever.retrieveFeed(app.getFeedSource());
		if (doc == null)
			return;

		adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.feedTitle);
		NodeList items = doc.getElementsByTagName("item");
		for (int i = 0; i < items.getLength(); i++) {
			Node node = items.item(i);
			Node title = DOMHelper.getChild(node, "title");
			adapter.add(title.getTextContent());
		}

		listTitles.setAdapter(adapter);
		listTitles.setOnItemClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG, "onPause");
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "onDestroy");
		stopService(new Intent(this, UpdateService.class));
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long row) {
		Log.d("CLICK", row + " wurde geklickt");
		// view is LinearLayout
		TextView item = (TextView) ((LinearLayout) view).getChildAt(0);
		String title = (String) item.getText();
		DescriptionActivity.setTitle(title);

		NodeList titles = doc.getElementsByTagName("item");
		Node node = DOMHelper.getItemByChildTextValue(titles, "title", title);
		DescriptionActivity.setDescription(DOMHelper.getChild(node,
				"description").getTextContent());

		startActivity(new Intent(this, DescriptionActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemPrefs:
			startActivity(new Intent(this, PrefsActivity.class));
			break;
		}
		return true;
	}

	public void update() {
		/*runOnUiThread(new Runnable() {

			@Override
			public void run() {
				txt.setText("updated " + count);
				count++;
			}
		});*/
	}
}