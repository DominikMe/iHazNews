package de.messinger.dome.iHazNews;

import de.messinger.dome.iHazNews.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DescriptionActivity extends Activity {

	private static String title;
	private static String description;
	TextView text, headline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.description);

		text = (TextView) findViewById(R.id.feedDescription);
		headline = (TextView) findViewById(R.id.feedDescriptionTitle);
	}

	@Override
	protected void onResume() {
		super.onResume();
		headline.setText(title);
		text.setText(description);
	}

	protected static void setTitle(String title) {
		DescriptionActivity.title = title;
	}
	
	protected static void setDescription(String description) {
		DescriptionActivity.description = description;
	}
}
