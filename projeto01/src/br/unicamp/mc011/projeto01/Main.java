package br.unicamp.mc011.projeto01;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
        ANTLRInputStream input = new ANTLRInputStream(System.in);
        NPLLexer lexer = new NPLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);        
        NPLParser parser = new NPLParser(tokens);
        MainListener parserListener = new MainListener();
        
        ParseTree tree = parser.root();
        ParseTreeWalker.DEFAULT.walk(parserListener, tree);
        
        Newspaper n = parserListener.newspaper;   
        try {
			System.out.println(buildIndex(n));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	public static int getColWidth(int totalCols, int numCols, int border){
		int dwidth = (960 - (border * (totalCols - 1))) / totalCols;
		int cwidth = numCols * dwidth + ((numCols - 1) * border);
		return cwidth ;
	}
		
	public static String buildIndex(Newspaper n) throws Exception{
		String headerTemplate = readFile("../templates/index_header.html");	
		String footerTemplate = readFile("../templates/index_footer.html");	

		StringBuilder output = new StringBuilder();
		
		String header = headerTemplate.replace("|title|", n.title);
		header = header.replace("|border|", "" + n.border);
		header = header.replace("|date|", n.date);
		output.append(header);
		
		int lastColumn = 0;
		output.append("<div class=\"row\">");
		
        for(NewspaperItem i : n.items){
        	// Nova linha?
        	if(i.firstCol <= lastColumn){
        		// Preencher restante da linha antes da nova linha
        		if(lastColumn < n.numCols){
        			output.append("<div class=\"span1\" style=\"width: ");
        			output.append(getColWidth(n.numCols, n.numCols - lastColumn, n.border));
        			output.append("px;\" >&nbsp;</div>");
        		}
        		output.append("</div><div class=\"row\">");
        	} else if(i.firstCol > lastColumn + 1){
        		// Preencher buraco entre colunas
    			output.append("<div class=\"span1\" style=\"width: ");
    			output.append(getColWidth(n.numCols, i.firstCol - lastColumn - 1, n.border));
    			output.append("px;\" >&nbsp;</div>");
        	}
        	
			output.append("<div class=\"span1\" style=\"width: ");
			output.append(getColWidth(n.numCols, i.lastCol - i.firstCol + 1, n.border));
			output.append("px;\" >");
			output.append(i.getItemHTML(n));
			output.append("</div>");
			
			lastColumn = i.lastCol;
        }
        
        output.append("</div>");
        
        output.append(footerTemplate);        
        return output.toString();
	}
	
	private static String readFile(String path) throws IOException {
		  FileInputStream stream = new FileInputStream(new File(path));
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    return Charset.forName("UTF-8").decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
		}

}
