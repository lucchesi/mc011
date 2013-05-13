package x86;

import util.List;

import temp.Label;
import temp.Temp;
import tree.Exp;
import tree.Stm;
import assem.Instr;

public class Codegen{
	public Frame frame;
    public List<Instr> program;
	
    public Codegen(Frame f){  	
    	this.frame = f;
    }
    
    public List<Instr> codegen(List<Stm> body){
    	this.program = new List<Instr>();
    	
    	for(Stm s: body)
    		munchStm(s);
    	
    	return this.program;
    }
    
    /* STATEMENTS */
    public void munchStm(Stm stm){
    	if(stm instanceof tree.CJUMP)  { munchStm((tree.CJUMP)  stm); return; }
    	if(stm instanceof tree.EXPSTM) { munchStm((tree.EXPSTM) stm); return; }
    	if(stm instanceof tree.JUMP)   { munchStm((tree.JUMP)   stm); return; }
    	if(stm instanceof tree.LABEL)  { munchStm((tree.LABEL)  stm); return; }
    	if(stm instanceof tree.MOVE)   { munchStm((tree.MOVE)   stm); return; }
    	if(stm instanceof tree.SEQ)    { munchStm((tree.SEQ)    stm); return; }
    	throw new Error("Unknown statement.");
    }
    
    /* EXPRESSIONS */
    public Temp munchExp(Exp exp){
    	if(exp instanceof tree.BINOP) return munchExp((tree.BINOP) exp);
    	if(exp instanceof tree.CALL)  return munchExp((tree.CALL)  exp);
    	if(exp instanceof tree.CONST) return munchExp((tree.CONST) exp);
    	if(exp instanceof tree.ESEQ)  return munchExp((tree.ESEQ)  exp);
    	if(exp instanceof tree.MEM)   return munchExp((tree.MEM)   exp);
    	if(exp instanceof tree.NAME)  return munchExp((tree.NAME)  exp);
    	if(exp instanceof tree.TEMP)  return munchExp((tree.TEMP)  exp);
    	throw new Error("Unknown expression.");
    }
    
    /* Statements implementation */
    
    public void munchStm(tree.CJUMP stm){
    	Temp left = munchExp(stm.getLeft());
    	Temp right = munchExp(stm.getRight());
    	
    	program.append(new assem.OPER(
    			"cmp `u0, `u1",
    			null,
    			new List<Temp>(left, right)
    			));
    	
    	String cmpInstruction;
    	switch(stm.getOperation()){
    	case tree.CJUMP.EQ:
    		cmpInstruction = "je"; break;
    	case tree.CJUMP.GE:
    		cmpInstruction = "jge"; break;
    	case tree.CJUMP.GT:
    		cmpInstruction = "jg"; break;
    	case tree.CJUMP.LE:
    		cmpInstruction = "jle"; break;
    	case tree.CJUMP.LT:
    		cmpInstruction = "jl"; break;
    	case tree.CJUMP.NE:
    		cmpInstruction = "jne"; break;
    	case tree.CJUMP.UGE:
    		cmpInstruction = "jae"; break;
    	case tree.CJUMP.UGT:
    		cmpInstruction = "ja"; break;
    	case tree.CJUMP.ULE:
    		cmpInstruction = "jbe"; break;
    	case tree.CJUMP.ULT:
    		cmpInstruction = "jb"; break;
    	default:
    		throw new Error("Unknown CJUMP operation");
    	}

    	Label falseLabel = new Label();
    	
    	program.append(new assem.OPER(
    			cmpInstruction + " `j0",
    			new List<Label>(stm.getLabelTrue(), falseLabel)
    			));
    	
    	program.append(new assem.LABEL(
    			falseLabel.toString() + ":",
    			falseLabel
    			));
    	
    	program.append(new assem.OPER(
    			"jmp `j0",
    			new List<Label>(stm.getLabelFalse())
    			));
    }
    
    public void munchStm(tree.EXPSTM stm) {
        munchExp(stm.getExpression());
    }
    
    public void munchStm(tree.JUMP stm){
    	program.append(new assem.OPER(
    			"jmp `u0",
    			null,
    			new List<Temp>(munchExp(stm.getExpression())),
    			stm.getTargets()
    			));
    }
    
    public void munchStm(tree.LABEL stm){
    	program.append(new assem.LABEL(
    			stm.getLabel().toString() + ":",
    			stm.getLabel()
    			));
    }
    
    public void munchStm(tree.MOVE stm){
    	Exp src = stm.getSource();
    	Exp dst = stm.getDestination();
     	
    	if(dst instanceof tree.MEM){
        	Temp srcRegs = munchExp(src);
        	Temp dstRegs = munchExp(((tree.MEM)dst).getExpression());
        	
    		program.append(new assem.OPER(
    				"mov [`u0], `u1",
    				null,
    				new List<Temp>(dstRegs, srcRegs)
    				));
    	} else {
        	Temp srcRegs = munchExp(src);
        	Temp dstRegs = ((tree.TEMP)dst).getTemp();
        	
    		program.append(new assem.MOVE(
    				dstRegs,
    				srcRegs
    				));
    	}
    }
    
    public void munchStm(tree.SEQ stm){
    	munchStm(stm.getLeft());
    	munchStm(stm.getRight());
    }    
    
    /* Expressions implementation */
    
    public Temp munchExp(tree.BINOP exp){
    	// TODO: Refactor, add more efficient instructions
    	// with consts / direct memory references
    	
    	Temp result = new Temp();
    	Temp right;
        Temp left = munchExp(exp.getLeft());
        program.append(new assem.MOVE(
        		result,
        		left
        		));

       	Exp rightExp = exp.getRight();
    	
    	switch(exp.getOperation()){
    	
    	case tree.BINOP.AND:
    		if(rightExp instanceof tree.CONST){
    			program.append(new assem.OPER(
    					"and `d0, " + ((tree.CONST)rightExp).getValue(),
    					new List<Temp>(result),
    					new List<Temp>(result)
    					));
    		} else {
    	        right = munchExp(exp.getRight());
        		program.append(new assem.OPER(
        				"and `d0, `u0",
        				new List<Temp>(result),
        				new List<Temp>(right, result)
        				));
    		}

    		break;
    		
    	case tree.BINOP.ARSHIFT:
    		if(! (rightExp instanceof tree.CONST) )
    			// TODO: Maybe handle this somehow?
    			throw new Error("Invalid ARSHIFT");
    		
    		program.append(new assem.OPER(
    				"sar `d0, " + ((tree.CONST)rightExp).getValue(),
    				new List<Temp>(result),
    				new List<Temp>(result)
    				));
    		break;
    		
    	case tree.BINOP.DIV:
	        right = munchExp(exp.getRight());

    		program.append(new assem.MOVE(
    				Frame.eax,
    				result
    				));
    		
    		program.append(new assem.OPER("cdq"));
    		
    		program.append(new assem.OPER(
    				"div `u0",
    				new List<Temp>(Frame.eax, Frame.edx),
    				new List<Temp>(right, Frame.eax, Frame.edx)
    				));
    		
    		program.append(new assem.MOVE(
    				result,
    				Frame.eax
    				));
    		
    		break;
     		
    	case tree.BINOP.LSHIFT:
    		if(! (rightExp instanceof tree.CONST) )
    			// TODO: Maybe handle this somehow?
    			throw new Error("Invalid LSHIFT");
    		
    		program.append(new assem.OPER(
    				"shl `d0, " + ((tree.CONST)rightExp).getValue(),
    				new List<Temp>(result),
    				new List<Temp>(result)
    				));
    		break;
    		
    	case tree.BINOP.MINUS:
    		if(rightExp instanceof tree.CONST){
    			program.append(new assem.OPER(
    					"sub `d0, " + ((tree.CONST)rightExp).getValue(),
    					new List<Temp>(result),
    					new List<Temp>(result)
    					));
    		} else {
    	        right = munchExp(exp.getRight());
        		program.append(new assem.OPER(
        				"sub `d0, `u0",
        				new List<Temp>(result),
        				new List<Temp>(right, result)
        				));
    		}
    		break;
    		
    	case tree.BINOP.OR:
    		if(rightExp instanceof tree.CONST){
    			program.append(new assem.OPER(
    					"or `d0, " + ((tree.CONST)rightExp).getValue(),
    					new List<Temp>(result),
    					new List<Temp>(result)
    					));
    		} else {
    	        right = munchExp(exp.getRight());
        		program.append(new assem.OPER(
        				"or `d0, `u0",
        				new List<Temp>(result),
        				new List<Temp>(right, result)
        				));
    		}
    		break;
    		
    	case tree.BINOP.PLUS:
    		if(rightExp instanceof tree.CONST){
    			program.append(new assem.OPER(
    					"add `d0, " + ((tree.CONST)rightExp).getValue(),
    					new List<Temp>(result),
    					new List<Temp>(result)
    					));
    		} else {
    	        right = munchExp(exp.getRight());
        		program.append(new assem.OPER(
        				"add `d0, `u0",
        				new List<Temp>(result),
        				new List<Temp>(right, result)
        				));
    		}
    		break;
    		
    	case tree.BINOP.RSHIFT:
    		if(! (rightExp instanceof tree.CONST) )
    			// TODO: Maybe handle this somehow?
    			throw new Error("Invalid RSHIFT");
    		
    		program.append(new assem.OPER(
    				"shr `d0, " + ((tree.CONST)rightExp).getValue(),
    				new List<Temp>(result),
    				new List<Temp>(result)
    				));
    		break;
    		
    	case tree.BINOP.TIMES:
	        right = munchExp(exp.getRight());

    		program.append(new assem.MOVE(
    				Frame.eax, 
    				result
    				));
    		
    		program.append(new assem.OPER(
    				"mul `u0",
    				new List<Temp>(Frame.eax, Frame.edx),
    				new List<Temp>(right, Frame.eax)
    				));
    		
    		program.append(new assem.MOVE(
    				result,
    				Frame.eax    				
    				));   		
    		break;
    		
    	case tree.BINOP.XOR:
    		if(rightExp instanceof tree.CONST){
    			program.append(new assem.OPER(
    					"xor `d0, " + ((tree.CONST)rightExp).getValue(),
    					new List<Temp>(result),
    					new List<Temp>(result)
    					));
    		} else {
    	        right = munchExp(exp.getRight());
        		program.append(new assem.OPER(
        				"xor `d0, `u0",
        				new List<Temp>(result),
        				new List<Temp>(right, result)
        				));
    		}
    		break;
    		
    	default:
    		throw new Error("Unknown BINOP");
    	}
    	
    	return result;
    }
    
    public Temp munchExp(tree.CALL exp){
    	List<Exp> args = exp.getArguments();
    	List<Exp> callArgs = null;
    	List<Temp> argRegisters = new List<Temp>();
    	
    	// Invert arg list
    	for(Exp arg: args){
    		if(callArgs != null) callArgs.add(arg, 0);
    		else callArgs = new List<Exp>(arg);
    	}
    	
    	// Munch args and push into stack
    	for(Exp arg: callArgs){
    		Temp mArg = munchExp(arg);
    		argRegisters.append(mArg);
    		
    		program.append(new assem.OPER(
    				"push `u0",
    				new List<Temp>(frame.SP()),
    				new List<Temp>(mArg, frame.SP())
    				));
    	}

    	
    	Temp callable = munchExp(exp.getCallable());

    	// TODO: call by name if callable is tree.NAME
    	program.append(new assem.OPER(
				"call `u0",
				frame.calleeDefs(),
				new List<Temp>(callable, argRegisters)
				));
    	
    	// Restore stack pointer
    	program.append(new assem.OPER(
    			"add `d0, " + (frame.wordsize() * callArgs.size()),
    			new List<Temp>(frame.SP()),
    			new List<Temp>(frame.SP())
    			));
    	
    	return frame.RV();
    }
    
    public Temp munchExp(tree.CONST exp){
    	Temp result = new Temp();
    	
    	program.append(new assem.OPER(
    			"mov `d0, " + exp.getValue(),
    			new List<Temp>(result),
    			null
    			));
    	
    	return result;
    }

    public Temp munchExp(tree.MEM exp){
    	// TODO: Express›es mais eficientes com MEM
    	// (consts, binops, etc)
    	Temp expResult = munchExp(exp.getExpression());
    	Temp result = new Temp();
    	
    	program.append(new assem.OPER(
    			"mov `d0, [`u0]",
    			new List<Temp>(result),
    			new List<Temp>(expResult)
    			));
    	
    	return result;
    }
    
    public Temp munchExp(tree.NAME exp){
    	Temp result = new Temp();
    	
    	program.append(new assem.OPER(
    			"mov `d0, " + exp.getLabel(),
    			new List<Temp>(result),
    			null
    			));
    	
    	return result;
    }
    
    public Temp munchExp(tree.TEMP exp){
    	return exp.getTemp();
    }

}
