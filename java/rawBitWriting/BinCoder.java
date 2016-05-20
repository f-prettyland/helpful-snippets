package data.bits;

import exceptions.SourceSymbolNotFoundException;
import structure.Node;
import structure.TreeManipulation;

public abstract class BinCoder {
	public BinCoder(){
		this.setRoot(TreeManipulation.createNYT());
	}

	protected int[] symbols = {0,1};
	private Node root;
	
	public void createEncodingFor(byte[] represent) throws SourceSymbolNotFoundException{
		setRoot(TreeManipulation.addNode(represent, getRoot(), symbols.length));
		TreeManipulation.increaseWeighting(represent,getRoot());
			
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
}
