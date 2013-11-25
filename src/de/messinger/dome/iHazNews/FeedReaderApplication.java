package de.messinger.dome.iHazNews;

import de.messinger.dome.iHazNews.R;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class FeedReaderApplication extends Application {

	String rssSource;
	private TitleActivity titleActivity;

	@Override
	public void onCreate() {
		super.onCreate();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		// this.rssSource = prefs.getString("Quelle",
		// getString(R.string.rssDefault));
	}
	
	public String getFeedSource() {
		if (rssSource == null)
			return getString(R.string.rssDefault);
		else
			return rssSource;
	}

	public void setFeedSource(String url) {
		rssSource = url;
	}
	
	public void update() {
		titleActivity.update();
	}

	public void registerTitleActivity(TitleActivity titleActivity) {
		this.titleActivity = titleActivity;		
	}
	
	public TitleActivity getTitleActivity() {
		return this.titleActivity;
	}
}
