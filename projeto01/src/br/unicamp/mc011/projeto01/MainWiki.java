package br.unicamp.mc011.projeto01;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class MainWiki {
	public static String parseWiki(String wiki){
		wiki = "\"" + wiki.replace("\"", "\\\"") + "\"";
		
        ANTLRInputStream input = new ANTLRInputStream(wiki);
        WikiLexer lexer = new WikiLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);        
        WikiParser parser = new WikiParser(tokens);
        
        String output = parser.w().s;
        
        output = output.replace("\\\"", "\"");
        
        return output;
	}

}
