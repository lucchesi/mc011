package br.unicamp.mc011.projeto01;

import java.io.IOException;

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
        ParseTree tree = parser.root();
        ParseTreeWalker.DEFAULT.walk(new MainListener(), tree);
       // System.out.println(tree.toStringTree(parser));
	}

}
