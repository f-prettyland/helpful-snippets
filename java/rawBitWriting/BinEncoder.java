package data.bits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import exceptions.CodingSymbolNotFoundException;
import exceptions.NYTNotFoundException;
import exceptions.SourceSymbolNotFoundException;
import structure.Node;
import structure.TreeManipulation;

public class BinEncoder extends BinCoder {
	public BinEncoder(){
		super();
	}
	public String getEncodingFor(byte[] represent)throws NYTNotFoundException, CodingSymbolNotFoundException, SourceSymbolNotFoundException{
		TreeMap<String, Node> rootNode = new TreeMap<String, Node> ();
		rootNode.put("", getRoot());
		return getEncodingFor(represent, rootNode);
	}

	private String getEncodingFor(byte[] represent, TreeMap<String, Node> nodes) throws NYTNotFoundException, CodingSymbolNotFoundException, SourceSymbolNotFoundException{
		TreeMap<String, Node> nDepthNodes = new TreeMap<String, Node>();
		final Set<Entry<String, Node>> entrySet = nodes.entrySet();
		for(Entry<String, Node> nodeEntry : entrySet){
			Node node = nodeEntry.getValue();
			String key = nodeEntry.getKey();
			if(Arrays.equals(node.getRepresents(),represent)){
				if(!Arrays.equals(TreeManipulation.NYT_REPRESENT,represent)){
					//if not NYT increase weighting
					TreeManipulation.increaseWeighting(node,getRoot());
				}
				return key;
			}else{
				final ArrayList<Node> children = node.getChildren();
				if(!children.isEmpty()){

					if(children.size()>symbols.length){
						throw new CodingSymbolNotFoundException();
					}
					int i = 0;
					for (Node child : children) {
						nDepthNodes.put(key.concat(""+symbols[i]), child);
						i++;
					}
				}
			}
		}
		if(!nDepthNodes.isEmpty()){
			return getEncodingFor(represent, nDepthNodes);
		}else{
			//NYT
			if(represent.length ==0){
				throw new NYTNotFoundException();
			}else{
				throw new SourceSymbolNotFoundException();
			}
		}
	}
}
