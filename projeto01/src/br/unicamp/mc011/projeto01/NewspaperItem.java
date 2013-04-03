package br.unicamp.mc011.projeto01;

import java.util.ArrayList;
import java.util.List;

public class NewspaperItem {
	public int firstCol;
	public int lastCol;
	
	public List<NewspaperItemPrint> printItems = new ArrayList<NewspaperItemPrint>();
	
	public String getItemHTML(Newspaper n) throws Exception{
		StringBuilder b = new StringBuilder();
		
		b.append("<div class=\"newsitem\">");
		for(NewspaperItemPrint p : printItems){
			String fieldHtml = n.articles.get(p.article).getFieldHTML(p.field);
			b.append(fieldHtml);
    	}
		b.append("</div>");
		
		return b.toString();
	}
}
