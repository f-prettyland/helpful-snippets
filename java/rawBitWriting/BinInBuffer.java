package data.bits;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigInteger;

import ui.Interface;

public class BinInBuffer {
	public BinInBuffer( BufferedInputStream bin) throws IOException{
		this.bin = bin;
		readByteIntoBuffer();
	}

	private BufferedInputStream bin;
	private String buffer;
	private int offset;
	
	public int nextInt() throws IOException{
		if(offset < 8){
			final char nextChar = buffer.charAt(offset);
			offset++;
			if(nextChar=='1'){
				return 1;
			}else{
				return 0;
			}
		}else{
			readByteIntoBuffer();
			return nextInt();
		}
	}

	private void readByteIntoBuffer() throws IOException {
		buffer="";
		byte[] b = new byte[1];
		bin.read(b);
		buffer = padLeft(Integer.toBinaryString(b[0]& 0xFF),8);
		Interface.verbosePrint("Read "+ buffer);
		offset=0;
	}
	
	public byte[] nextByte() throws IOException{
		String str ="";
		for (int i = 0; i < 8; i++) {
			str += nextInt();
		}
		return new BigInteger(str, 2).toByteArray();
	}
	
	private static String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s).replace(' ', '0');  
	}

	
}
