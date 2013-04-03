package br.unicamp.mc011.projeto01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Newspaper {
	public String title;
	public String date;
	
	public int numCols;
	public int border;
	
	public Map<String, NewsArticle> articles = new HashMap<String, NewsArticle>();
	public List<NewspaperItem> items = new ArrayList<NewspaperItem>();
}
