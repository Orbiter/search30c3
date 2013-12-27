package de.ccc.search30c3;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchResultItem {

	/** Json result 
	 * 
	 * 
    "title": "Schedule 30C3",
    "link": "https://events.ccc.de/congress/2013/Fahrplan/events/5622.html",
    "code": "",
    "description": "Schedule <b>30C3</b>",
    "pubDate": "Wed, 25 Dec 2013 22:38:58 +0000",
    "size": "3219",
    "sizename": "3 kbyte",
    "guid": "0H5rbhpQt9hg",
    "faviconCode": "a97FUVpQt9hg",
    "host": "events.ccc.de",
    "path": "/congress/2013/Fahrplan/events/5622.html",
    "file": "5622.html",
    "urlhash": "0H5rbhpQt9hg",
    "ranking": "1534439"
    */
	
	public String title;
	public String link;
	public String code;
	public String description;
	public Date pubDate;
	public int size;
	public String sizeReadable;
	public String guid;
	public String faviconCode;
	public String host;
	public String path;
	public String file;
	public String urlhash;
	public long ranking;
	
	public SearchResultItem(JSONObject json) {
		try {
			this.title = json.getString("title");
			this.link = json.getString("link");
			this.code = json.getString("code");
			this.description = json.getString("description");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.title;
	}

}
