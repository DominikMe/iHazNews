package de.messinger.dome.iHazNews;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

public class FeedRetriever {

	static final String TAG = "FeedRetriever";

	public static Document retrieveFeed(String url) {
		try {
			return new AsyncTask<String, String, Document>() {

				@Override
				protected Document doInBackground(String... params) {
					String url = params[0];
					Log.d(TAG, "retrieve " + url);
					URL address = null;
					try {
						address = new URL(url);
					} catch (MalformedURLException e) {
						Log.e("MalformedURL", "", e);
					}
					DocumentBuilder builder = null;
					try {
						builder = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder();
					} catch (ParserConfigurationException e) {
						Log.e("XMLParserConfiguration", "", e);
					}
					Document doc = null;
					try {
						doc = builder.parse(address.openStream());
					} catch (SAXException e) {
						Log.e("SAX_Ecception", "", e);
					} catch (IOException e) {
						Log.e("IO_Exception", "", e);
					}
					return doc;
				}

			}.execute(url).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}