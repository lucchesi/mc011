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
    	if(field.equals("image")) return getImageFloatHTML();
    	if(field.equals("source")) return getSourceHTML();
    	if(field.equals("date")) return getDateHTML();
    	if(field.equals("author")) return getAuthorHTML();
    	if(field.equals("text")) return getTextHTML();
    	if(field.equals("readmore")) return getReadMoreHTML();
    	
    	return fields.get(field);  	
    }
    
    public String getTitleHTML() {
    	if(!fields.containsKey("title") || fields.get("title") == null)
    		return "";
    	
    	StringBuilder b = new StringBuilder();
    	b.append("<h2 class=\"title\">");
    	b.append(fields.get("title"));
    	b.append("</h2>");
    	
    	return b.toString();
    }
  
    public String getAbstractHTML() { 	
    	if(!fields.containsKey("abstract") || fields.get("abstract") == null)
    		return "";
    	
    	StringBuilder b = new StringBuilder();
    	b.append("<p class=\"abstract\">");
    	b.append(fields.get("abstract"));
    	b.append("</p>");
    	
    	return b.toString();
    }

    public String getImageCenterHTML() {
    	if(!fields.containsKey("image") || fields.get("image") == null)
    		return "";
    	
    	StringBuilder b = new StringBuilder();
    	b.append("<img class=\"image-center img-polaroid\" src=\"");
		b.append(fields.get("image"));
    	b.append("\" >");
    	
    	return b.toString();
    }
    
    public String getImageFloatHTML() {
    	if(!fields.containsKey("image") || fields.get("image") == null)
    		return "";
    	
    	StringBuilder b = new StringBuilder();
    	b.append("<img class=\"image-float img-polaroid\" src=\"");
		b.append(fields.get("image"));
    	b.append("\" >");
    	
    	return b.toString();
    }
    
    public String getSourceHTML() {
    	if(!fields.containsKey("source") || fields.get("source") == null)
    		return "";
    	
    	StringBuilder b = new StringBuilder();
    	b.append("<p class=\"source\"><strong>Fonte:</strong> ");
    	b.append(fields.get("source"));
    	b.append("</p>");
    	
    	return b.toString();
    }
    
    public String getDateHTML() {
    	if(!fields.containsKey("date") || fields.get("date") == null)
    		return "";
    	
    	StringBuilder b = new StringBuilder();
    	b.append("<p class=\"date\"><strong>Data:</strong> ");
    	b.append(fields.get("date"));
    	b.append("</p>");
    	
    	return b.toString();
    }
    
    public String getAuthorHTML() {
    	if(!fields.containsKey("author") || fields.get("author") == null)
    		return "";
    	
    	StringBuilder b = new StringBuilder();
    	b.append("<p class=\"author\"><strong>Autor:</strong> ");
    	b.append(fields.get("author"));
    	b.append("</p>");
    	
    	return b.toString();
    }
    
    public String getTextHTML() {
    	if(!fields.containsKey("text") || fields.get("text") == null)
    		return "";
    	
    	StringBuilder b = new StringBuilder();
    	//TODO: Tratar formatacao wiki
    	b.append("<div class=\"text\">");
    	b.append(fields.get("text"));
    	b.append("</div>");
    	
    	return b.toString();
    }
    
    public String getReadMoreHTML(){
    	StringBuilder b = new StringBuilder();
		b.append("<p class=\"text-right\"><a href=\"#");
		b.append(fields.get("objname"));
		b.append("\" class=\"readmore\">Leia mais...</a></p>");
		return b.toString();
    }
}
