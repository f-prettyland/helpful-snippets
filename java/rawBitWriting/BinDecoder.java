package data.bits;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.CodingSymbolNotFoundException;
import exceptions.SourceSymbolNotFoundException;
import structure.Node;
import structure.TreeManipulation;
import ui.Interface;

public class BinDecoder extends BinCoder{
	public BinDecoder(){
		super();
	}
	public byte getDecodingFor(BinInBuffer code) throws IOException, CodingSymbolNotFoundException, SourceSymbolNotFoundException {
		byte[] represents =  getDecodingFor(code, getRoot());
		byte toReturn = (byte)0;
		for (byte b : represents) {
			toReturn=b;
		}
		return toReturn;
	}
	private byte[] getDecodingFor(BinInBuffer code, Node node) throws IOException, CodingSymbolNotFoundException, SourceSymbolNotFoundException {

		final ArrayList<Node> children = node.getChildren();
		if(children.isEmpty()){
			final byte[] represent = node.getRepresents();
			if(represent==null){
				throw new SourceSymbolNotFoundException();
			}else{
				//NYT
				if(represent.length==0){
					final byte[] nextByte = code.nextByte();
					try{
						TreeManipulation.getNode(nextByte, getRoot());
					}catch(SourceSymbolNotFoundException e){
						this.createEncodingFor(nextByte);
						return nextByte;
					}
					//KNOWN SYMBOL AFTER NYT representing eof
					throw new EOFException();
				}
				//if not NYT increase weighting
				TreeManipulation.increaseWeighting(node,getRoot());
				return represent;
			}
		}
		int readIn = code.nextInt();
		Interface.verbosePrint("following "+ readIn);
		int symIndex= indexOf(readIn);
		if(symIndex == -1){
			throw new CodingSymbolNotFoundException();
		}

		return getDecodingFor(code, children.get(symIndex));
	}

	private int indexOf(int bin){
		int index=0;
		for (int symbol : symbols) {
			if(symbol==bin){
				return index;
			}
			index++;
		}
		return -1;
	}
}
