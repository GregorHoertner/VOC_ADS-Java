package kapitel_3.vl.ds;
public class PrintTree {
	private static final String pgfHeader = "\\tikzset{\n" +
			"	every node/.style={\n" +
			"		inner sep=0,\n" +
			"		minimum width=15pt,\n" +
			"		draw,\n" +
			"		circle},\n" +
			"	blank/.style={\n" +
			"		draw=none},\n" +
			"	level distance=1cm,\n" +
			"	edge from parent/.style={\n" +
			"		draw,\n" +
			"		edge from parent path={\n" +
			"			(\\tikzparentnode) -- (\\tikzchildnode)\n" +
			"		}\n" +
			"	},\n" +
			"	level 1/.style={\n" +
			"		sibling distance=3cm\n" +
			"	},\n" +
			"	level 2/.style={\n" +
			"		sibling distance=1.5cm\n" +
			"	},\n" +
			"	level 3/.style={\n" +
			"		sibling distance=0.75cm\n" +
			"	},\n" +
			"	level 4/.style={\n" +
			"		sibling distance=0.375cm\n" +
			"	}\n" +
			"};\n";
	
	protected static String subTreesToPGF(BTree.Node currentRoot, String tabs) {
		String pgfSubTrees = "";
		String pgfTreeLeft = treeToPGF(currentRoot.left, tabs);
		String pgfTreeRight = treeToPGF(currentRoot.right, tabs);
		if (!pgfTreeLeft.equals("") || !pgfTreeRight.equals("")) {
			if (pgfTreeLeft.equals("")) {
				pgfTreeLeft = tabs + "child[missing]\n";
			} else if (pgfTreeRight.equals("")) {
				pgfTreeRight = tabs + "child[missing]\n";
			}
			pgfSubTrees = pgfTreeLeft + pgfTreeRight;
		}
		return pgfSubTrees;
	}
	
	protected static String treeToPGF(BTree.Node currentRoot, String tabs) {
		String pgfTree = "";
		if (currentRoot != null) {
			if (currentRoot.parent == null) {
				pgfTree = "\\node {" + currentRoot.data + "}\n";
				pgfTree = pgfTree + subTreesToPGF(currentRoot, tabs + "\t") + ";";
			} else {
				pgfTree = tabs + "child {\n" + tabs + "\tnode {" + currentRoot.data + "}\n";
				pgfTree = pgfTree + subTreesToPGF(currentRoot, tabs + "\t") + tabs + "}\n";
			}
		}
		
		return pgfTree;
	}

	protected static String treeToQTree(BTree.Node currentRoot) {
		String qTree = null;
		
		if (currentRoot != null) {
			qTree = "[.\\node[circle,inner sep=0,minimum width=15,draw]{" + currentRoot.data + "}; ";
			String qTreeLeft = treeToQTree(currentRoot.left);
			String qTreeRight = treeToQTree(currentRoot.right);
			if (qTreeLeft != null || qTreeRight != null) {
				if (qTreeLeft == null && qTreeRight != null) {
					qTreeLeft = "\\edge[draw=white];{} ";
				} else if (qTreeRight == null && qTreeLeft != null) {
					qTreeRight = "\\edge[draw=white];{} ";
				}
				qTree = qTree + qTreeLeft + qTreeRight;
			}
			qTree = qTree + "] ";
		}
		
		return qTree;
	}
	
	
	public static String treeToPGF(BTree tree) {
		String pgfTree = treeToPGF(tree.root, "");
		pgfTree = "\\begin{tikzpicture}\n" + pgfHeader + pgfTree + "\n\\end{tikzpicture}\n";
		return pgfTree;
	}
	
	public static String treeToQTree(BTree tree) {
		return "\\tikzset{edge from parent/.style={draw, edge from parent path={(\\tikzparentnode) -- (\\tikzchildnode)}}}\n\\Tree " + treeToQTree(tree.root);
	}
}
