package br.unicamp.mc011.projeto01;

import java.util.HashMap;
import java.util.Map;

public class NewsArticle {
	public Map<String, String> fields = new HashMap<String, String>();
    
    public String toString(){
    	StringBuilder a = new StringBuilder();
    	a.append("Object name: " + fields.get("objname") + "\n");
    	a.append("Title: " + fields.get("title") + "\n");
    	a.append("Abstract: " + fields.get("abstract") + "\n");
    	a.append("Image: " + fields.get("image") + "\n");
    	a.append("Source: " + fields.get("source") + "\n");
    	a.append("Date: " + fields.get("date") + "\n");
    	a.append("Author: " + fields.get("author") + "\n");
    	a.append("Text: " + fields.get("text") + "\n");
    	
    	return a.toString();
    }	
    
    public String getFieldHTML(String field) throws Exception{
    	if(!fields.containsKey(field) || fields.get(field) == null)
    		throw new Exception(fields.get("objname") + " does not contain field " + field);
    	
    	if(field.equals("title")) return getTitleHTML();
    	if(field.equals("abstract")) return getAbstractHTML();
    	if(field.equals("image")) return getImageHTML();
    	if(field.equals("source")) return getSourceHTML();
    	if(field.equals("date")) return getDateHTML();
    	if(field.equals("author")) return getAuthorHTML();
    	if(field.equals("text")) return getTextHTML();
    	
    	return fields.get(field);  	
    }
    
    public String getTitleHTML() {
    	StringBuilder b = new StringBuilder();
    	b.append("<h1 class=\"title\">");
    	b.append(fields.get("title"));
    	b.append("</h1>");
    	
    	return b.toString();
    }
    
    public String getAbstractHTML() { 	
    	StringBuilder b = new StringBuilder();
    	b.append("<p class=\"abstract\">");
    	b.append(fields.get("abstract"));
    	b.append("</p>");
    	
    	return b.toString();
    }
    
    public String getImageHTML() {
    	StringBuilder b = new StringBuilder();
    	b.append("<img class=\"image\" src=\"");
		b.append(fields.get("image"));
    	b.append("\" >");
    	
    	return b.toString();
    }
    
    public String getSourceHTML() {
    	StringBuilder b = new StringBuilder();
    	b.append("<span class=\"source\"><b>Fonte:</b> ");
    	b.append(fields.get("source"));
    	b.append("</span>");
    	
    	return b.toString();
    }
    
    public String getDateHTML() {
    	StringBuilder b = new StringBuilder();
    	b.append("<span class=\"date\"><b>Data:</b> ");
    	b.append(fields.get("date"));
    	b.append("</span>");
    	
    	return b.toString();
    }
    
    public String getAuthorHTML() {
    	StringBuilder b = new StringBuilder();
    	b.append("<span class=\"author\"><b>Autor:</b> ");
    	b.append(fields.get("author"));
    	b.append("</span>");
    	
    	return b.toString();
    }
    
    public String getTextHTML() {
    	StringBuilder b = new StringBuilder();
    	//TODO: Tratar formatacao wiki
    	b.append("<div class=\"text\">");
    	b.append(fields.get("text"));
    	b.append("</div>");
    	
    	return b.toString();
    }
}
