package de.ccc.search30c3;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchResultList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5003009482472385159L;
	private ArrayList<SearchResultItem> data;
	private String nextPage;
	private String lastPage;
	
	public SearchResultList() {
		data = new ArrayList<SearchResultItem>();
	}
	
	public SearchResultItem get(int position) {
		return data.get(position);
	}
	
	public void add(SearchResultItem item) {
		data.add(item);
	}
	
	public int size() {
		return data.size();
	}

}
