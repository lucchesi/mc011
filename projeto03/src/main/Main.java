package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PushbackReader;

import optimization.DeadCodeElimination;

import minijava.lexer.Lexer;
import minijava.lexer.LexerException;
import minijava.node.Start;
import minijava.parser.Parser;
import minijava.parser.ParserException;
import reg_alloc.RegAlloc;
import semant.Env;
import semant.TypeChecker;
import syntaxtree.Program;
import translate.Frag;
import translate.ProcFrag;
import translate.Translate;
import translate.VtableFrag;
import util.List;
import util.conversor.SyntaxTreeGenerator;
import assem.Instr;
import canon.BasicBlocks;
import canon.Canon;
import canon.TraceSchedule;
import errors.ErrorEchoer;
import frame.Frame;
import frame.Proc;

// Uma coisa n�o especificada em minijava eh se
// subclasses podem redeclarar atributos.
// Solucao adotada: podem; perdem o acesso a variavel
// da super classe se o fizerem.
public final class Main{
	private static InputStreamReader input;
	private static PrintStream output;
	private static boolean optimization;
	
	private static void processArguments(String[] args) throws FileNotFoundException{
		if(args == null)
			return;
		
		for(String arg : args){
			if("-O".equals(arg)){
				optimization = true;
			}else if(input == null){
				input = new InputStreamReader(new FileInputStream(args[0]));				
			}else if(output == null){
				output = new PrintStream(arg);				
			}
		}
		
		if(input == null)
			input = new InputStreamReader(System.in);
		if(output == null)
			output = new PrintStream(System.out);
	}
	
	public static void main(String[] args) {
		try{
			processArguments(args);
            String name = args.length == 0 ? "stdin" : args[0];
            
			Program program = parse(input);
			
            ErrorEchoer err = new SimpleError(name);
            Env env = TypeChecker.TypeCheck(err, program);

            if ( err.ErrorCount() != 0 ){
                err.Print(new Object[]{err.ErrorCount() + " erros", err.WarningCount() + " avisos"});
                System.exit(-1);
            }

            generateCode(args, program, env);
		}
		catch(Throwable e)
		{
			System.err.println(e.getMessage());
            e.printStackTrace();
		}
		
		System.exit(0);
	}

	private static void generateCode(String[] args, Program program, Env env) throws FileNotFoundException {
		// here the AST is transformed into the Intemediate Representation
		Frame frame = new x86.Frame();
		Frag f = Translate.translate(frame, env, program);
		
		output.println("    BITS 32");
		output.println("");
		output.println("    EXTERN _minijavaExit");
		output.println("    EXTERN _printInt");
		output.println("    EXTERN _newObject");
		output.println("    EXTERN _newArray");
		output.println("    EXTERN _assertPtr");
		output.println("    EXTERN _boundCheck");
		output.println("");
		output.println("    GLOBAL _minijava_main_1");
		output.println("");
		output.println("    SECTION .data");
		// outputting vtables
		for ( Frag a = f; a != null; a = a.next )
		    if ( a instanceof VtableFrag )
		    {
		        VtableFrag v = (VtableFrag) a;
		        
		        output.println(v.name+":");
		        for ( String ss : v.vtable )
		            output.println("    dd " + ss);
		    }
		
		output.println("");
		output.println("SECTION .text");
		output.println("");
		for ( Frag a = f; a != null; a = a.next )
		    if ( a instanceof ProcFrag ){
		        ProcFrag p = (ProcFrag) a;
		
		        // the IR is canonicalized.
		        TraceSchedule ts = new TraceSchedule(new BasicBlocks(Canon.linearize(p.body)));
		        
		        
		        // Instruction Selection is done                   
		        List<Instr> instrs = p.frame.codegen(ts.stms);
		        
		        instrs = p.frame.procEntryExit2(instrs);
		        if(optimization){
		        	DeadCodeElimination opt = new DeadCodeElimination();
		        	opt.optimize(instrs);
		        }
		                                                
		        // allocating the registers
		        RegAlloc r = new RegAlloc(p.frame, instrs);
		        
		        // outputting the generated code.
		        Proc finalProc = p.frame.procEntryExit3(r.instrs);
		        finalProc.print(output, r);
		    }
	}

	private static Program parse(InputStreamReader input) throws ParserException, LexerException, IOException {
		PushbackReader pushback = new PushbackReader(input);
		Lexer lexer = new Lexer(pushback);
		Parser parser = new Parser(lexer);
		
		Start s = parser.parse();
		//System.out.println(s);
		
		// ... up until here, classes and package organization
		// are decided by SableCC
		
		// Translating from SableCC's to Appel's internal representation
		Program program = SyntaxTreeGenerator.convert(s);
		return program;
	}

}
