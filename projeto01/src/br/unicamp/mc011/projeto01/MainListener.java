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

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class MainListener implements NPLListener {
	int i=0;
	
	List<String> a = new ArrayList<String>();
	
	class Noticia {
	    public String bla;
	}
	
	List<Noticia> news = new ArrayList<Noticia>();

	@Override
	public void enterEveryRule(ParserRuleContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitEveryRule(ParserRuleContext arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitErrorNode(ErrorNode arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitTerminal(TerminalNode arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterRange(RangeContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitRange(RangeContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterTitle_(Title_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitTitle_(Title_Context ctx) {
        i++; 
        System.out.println("i: " + i + " " + ctx.getText());
        a.add(ctx.getText());
	}

	@Override
	public void enterRoot(RootContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitRoot(RootContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterCol_(Col_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitCol_(Col_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterBorder_(Border_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitBorder_(Border_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterPrint(PrintContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitPrint(PrintContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterNews_(News_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitNews_(News_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterDate_(Date_Context ctx) {
        Noticia tmp = new Noticia();
        tmp.bla = ctx.getText();
        news.add(tmp);
	}

	@Override
	public void exitDate_(Date_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterNews_obj(News_objContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitNews_obj(News_objContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterStruct(StructContext ctx) {
        System.out.println("<html><head></head><body>");
        
        for(Iterator<String> it = a.iterator(); it.hasNext(); ) {
            String item = it.next();
            System.out.println(item);
        }
        
        System.out.println("Noticias:");
        for(Iterator<Noticia> it = news.iterator(); it.hasNext(); ) {
            String item = it.next().bla;
            System.out.println(item);
        }

	}

	@Override
	public void exitStruct(StructContext ctx) {
		System.out.println("</body></html>");
	}

	@Override
	public void enterNews_obj_token(News_obj_tokenContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitNews_obj_token(News_obj_tokenContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterFormat_(Format_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitFormat_(Format_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterCont(ContContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitCont(ContContext ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterNewsp_(Newsp_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitNewsp_(Newsp_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enterItem_(Item_Context ctx) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exitItem_(Item_Context ctx) {
		// TODO Auto-generated method stub

	}

}
