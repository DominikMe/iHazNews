package de.messinger.dome.iHazNews;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;

public class UpdateService extends Service {

	static final String TAG = "UpdateService";
	static final int DELAY = 3000;
	private boolean runFlag = false;
	private Thread updater;
	// private FeedData feedData;
	private Document doc;
	private ContentValues values;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "service created");
		updater = new Thread(new Updater());
		// feedData = new FeedData(getApplicationContext());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "service started");
		if (!updater.isAlive())
			updater.start();
		runFlag = true;
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "service stopped");
		runFlag = false;
		updater.interrupt();
		// feedData.close();
	}

	class Updater implements Runnable {

		FeedReaderApplication app = (FeedReaderApplication) getApplication();

		@Override
		public void run() {
			while (runFlag) {
				try {
					app.update();

					/*
					 * doc = FeedRetriever.retrieveFeed(app.getFeedSource());
					 * values = new ContentValues(); Node node =
					 * doc.getElementsByTagName("channel").item(0); String
					 * channel = node.getAttributes().item(0) .getTextContent();
					 * NodeList items = doc.getElementsByTagName("item"); for
					 * (int i = 0; i < items.getLength(); i++) { node =
					 * items.item(i); Node value = DOMHelper.getChild(node,
					 * "title"); values.put(FeedData.TITLE,
					 * value.getTextContent()); value = DOMHelper.getChild(node,
					 * "description"); values.put(FeedData.DESCRIPTION,
					 * value.getTextContent()); value = DOMHelper.getChild(node,
					 * "link"); values.put(FeedData.LINK,
					 * value.getTextContent()); values.put(FeedData.CHANNEL,
					 * channel); feedData.insert(values); }
					 */
					Log.d(TAG, "update done");
					Thread.sleep(DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
