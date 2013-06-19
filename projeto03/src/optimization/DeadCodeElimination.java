package optimization;

import java.util.HashSet;
import java.util.Set;

import temp.Temp;
import util.List;
import assem.Instr;
import assem.OPER;
import assem.MOVE;

import flow_graph.AssemFlowGraph;
import graph.Node;

public class DeadCodeElimination {

	/* armazena a análise de longevidade */
	private LivenessAnalysis dfa;
	private AssemFlowGraph cfg;

	/*
	 * realiza a otimizacao "dead code elimination" utilizando a analise de
	 * fluxo de dados "liveness analysis"
	 */

	private String[] binops = { "and", "sar", "div", "shl", "sub", "or", "add",
			"shr", "mul", "xor" };

	private boolean isRemovable(Instr i) {
		// s: a←b⊕c
		if (i instanceof OPER) {
			for (String s : binops)
				if (i.assem.startsWith(s))
					return true;

		// s: a←M[x] ou s: a←b
		} else if (i instanceof MOVE) {
			if (i.assem.startsWith("mov `d0"))
				return true;
		}

		return false;
	}

	public void optimize(List<Instr> l) {
		cfg = new AssemFlowGraph(l);
		/* executa a analise de longevidade */
		dfa = new LivenessAnalysis(cfg);

		int remCount = 0;
		
		for (Node n : cfg.nodes()) {
			if (!isRemovable(cfg.instr(n)))
				continue;

			Set<Temp> defs = new HashSet<Temp>();
			if (cfg.def(n) != null)
				for (Temp t : cfg.def(n))
					defs.add(t);
			defs.retainAll(dfa.getOut(n));
			if (defs.size() == 0){
				l.remove(cfg.instr(n));
				System.err.println("[-"+ (++remCount) + "] "  + cfg.instr(n).assem);
			}
		}
	}
}
