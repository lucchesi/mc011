package optimization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import temp.Temp;

import flow_graph.FlowGraph;
import graph.Node;

public class LivenessAnalysis {
	private Map<Node, Set<Temp>> in;
	private Map<Node, Set<Temp>> out;

	public Set<Temp> getOut(Node n) {
		return out.get(n);
	}

	public LivenessAnalysis(FlowGraph g) {
		// Variable initialization
		Map<Node, Set<Temp>> _in;
		Map<Node, Set<Temp>> _out;

		in = new HashMap<Node, Set<Temp>>();
		out = new HashMap<Node, Set<Temp>>();
		_in = new HashMap<Node, Set<Temp>>();
		_out = new HashMap<Node, Set<Temp>>();

		// Based on Appel - Algorithm 10.4

		// Initialize sets
		for (Node n : g.nodes()) {
			in.put(n, new HashSet<Temp>());
			out.put(n, new HashSet<Temp>());
		}

		do {
			for (Node n : g.nodes()) {
				// Line 1
				_in.put(n, in.get(n));
				_out.put(n, out.get(n));

				// Line 2
				Set<Temp> newIn = new HashSet<Temp>(out.get(n));
				if (g.def(n) != null)
					for (Temp t : g.def(n))
						newIn.remove(t);
				if (g.use(n) != null)
					for (Temp t : g.use(n))
						newIn.add(t);
				in.put(n, newIn);

				// Line 3
				Set<Temp> newOut = new HashSet<Temp>();
				if (n.succ() != null)
					for (Node s : n.succ())
						for (Temp t : in.get(s))
							newOut.add(t);
				out.put(n, newOut);
			}

		} while (!(_in.equals(in) && _out.equals(out)));
	}
}