package br.unicamp.mc011.projeto01;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import br.unicamp.mc011.projeto01.NPLParser.Border_Context;
import br.unicamp.mc011.projeto01.NPLParser.Col_Context;
import br.unicamp.mc011.projeto01.NPLParser.ContContext;
import br.unicamp.mc011.projeto01.NPLParser.Date_Context;
import br.unicamp.mc011.projeto01.NPLParser.Format_Context;
import br.unicamp.mc011.projeto01.NPLParser.Item_Context;
import br.unicamp.mc011.projeto01.NPLParser.News_Context;
import br.unicamp.mc011.projeto01.NPLParser.News_objContext;
import br.unicamp.mc011.projeto01.NPLParser.News_obj_tokenContext;
import br.unicamp.mc011.projeto01.NPLParser.Newsp_Context;
import br.unicamp.mc011.projeto01.NPLParser.PrintContext;
import br.unicamp.mc011.projeto01.NPLParser.RangeContext;
import br.unicamp.mc011.projeto01.NPLParser.RootContext;
import br.unicamp.mc011.projeto01.NPLParser.StructContext;
import br.unicamp.mc011.projeto01.NPLParser.Title_Context;

import java.util.List;
import java.util.ArrayList;

public class MainListener implements NPLListener {		
	Newspaper newspaper = new Newspaper();
	List<NewsArticle> articles = new ArrayList<NewsArticle>();
	
	public String unescape(String input){
		return input.substring(1, input.length() - 1).replace("\\\"", "\"");
	}
	
	@Override
	public void enterEveryRule(ParserRuleContext arg0) { }

	@Override
	public void exitEveryRule(ParserRuleContext arg0) { }

	@Override
	public void visitErrorNode(ErrorNode arg0) { }

	@Override
	public void visitTerminal(TerminalNode arg0) { }

	@Override
	public void enterRange(RangeContext ctx) { }

	@Override
	public void exitRange(RangeContext ctx) { }

	@Override
	public void enterTitle_(Title_Context ctx) { }

	@Override
	public void exitTitle_(Title_Context ctx) {	}

	@Override
	public void enterRoot(RootContext ctx) { }

	@Override
	public void exitRoot(RootContext ctx) {	}

	@Override
	public void enterCol_(Col_Context ctx) { }

	@Override
	public void exitCol_(Col_Context ctx) { }

	@Override
	public void enterBorder_(Border_Context ctx) { }

	@Override
	public void exitBorder_(Border_Context ctx) { }

	@Override
	public void enterPrint(PrintContext ctx) { }

	@Override
	public void exitPrint(PrintContext ctx) { }

	@Override
	public void enterNews_(News_Context ctx) { }

	@Override
	public void exitNews_(News_Context ctx) {
        NewsArticle n = new NewsArticle();
        n.fields.put("objname", ctx.NEWS().getText());
        
        List<News_objContext> objs = ctx.news_obj();

        for(News_objContext o : objs){
        	if(n.fields.containsKey(o.news_obj_token().getText().toLowerCase()))
        		System.out.println("Warning: Duplicate field detected: " + o.news_obj_token().getText().toLowerCase());
        	
        	n.fields.put(o.news_obj_token().getText().toLowerCase(), unescape(o.STRING().getText()));
        }
        
        n.fields.put("readmore", "");
        
        articles.add(n);
        newspaper.articles.put(n.fields.get("objname"), n);
	}

	@Override
	public void enterDate_(Date_Context ctx) { }

	@Override
	public void exitDate_(Date_Context ctx) { }

	@Override
	public void enterNews_obj(News_objContext ctx) { }

	@Override
	public void exitNews_obj(News_objContext ctx) { }

	@Override
	public void enterStruct(StructContext ctx) { }

	@Override
	public void exitStruct(StructContext ctx) {	}

	@Override
	public void enterNews_obj_token(News_obj_tokenContext ctx) { }

	@Override
	public void exitNews_obj_token(News_obj_tokenContext ctx) { }

	@Override
	public void enterFormat_(Format_Context ctx) { }

	@Override
	public void exitFormat_(Format_Context ctx) {
		if(ctx.col_() != null)
			newspaper.numCols = Integer.parseInt(ctx.col_().NUM().getText());
		if(ctx.border_() != null)
			newspaper.border = Integer.parseInt(ctx.border_().NUM().getText());
	}

	@Override
	public void enterCont(ContContext ctx) { }

	@Override
	public void exitCont(ContContext ctx) { 
		Newsp_Context c = ctx.newsp_();
		
		newspaper.title = unescape(c.title_().STRING().getText());
		
		if(c.date_() != null)
			newspaper.date = unescape(c.date_().STRING().getText());
	}

	@Override
	public void enterNewsp_(Newsp_Context ctx) { }

	@Override
	public void exitNewsp_(Newsp_Context ctx) { }

	@Override
	public void enterItem_(Item_Context ctx) { }

	@Override
	public void exitItem_(Item_Context ctx) { 
		NewspaperItem n = new NewspaperItem();
		
		if(ctx.range() != null){
			n.firstCol = Integer.parseInt(ctx.range().NUM(0).getText());
			n.lastCol = Integer.parseInt(ctx.range().NUM(1).getText());
		} else if(ctx.NUM() != null) {
			n.firstCol = Integer.parseInt(ctx.NUM().getText());
			n.lastCol = Integer.parseInt(ctx.NUM().getText());
		} else {
			return;
		}
		
		for(PrintContext o : ctx.print())
			n.printItems.add(new NewspaperItemPrint(o.NEWS().getText(), o.news_obj_token().getText()));
		
		newspaper.items.add(n);
	}

}
