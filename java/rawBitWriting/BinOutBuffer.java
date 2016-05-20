package data.bits;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import ui.Interface;

public class BinOutBuffer {
	public BinOutBuffer( FileOutputStream bin, FileOutputStream bin2){
		this.bin = bin;
		this.readableOutput = bin2;
		buffer="";
	}

	private FileOutputStream bin;
	private FileOutputStream readableOutput;
	private String buffer;
	private String readableBuffer;
	private int offset;

	public void writeSymbol(byte[] b) throws IOException{
		String i = padLeft(Integer.toBinaryString(b[0]& 0xFF).replace(' ', '0'),8);
		write(i);
	}

	public void write(String i) throws IOException{
		readableBuffer+=i+ " ";
		if(offset+i.length() < 8){
			buffer += i;
			offset+=i.length() ;
		}else{
			final int charsToWrite = 8-offset;
			buffer += i.substring(0,charsToWrite);
			String leftToWrite = i.substring(charsToWrite);
			offset=0;
			writeBuffer();
			buffer="";
			write(leftToWrite);
		}
	}

	public void close() throws IOException{
		if(buffer.length()>0){
			buffer = padRight(buffer, 8);
			writeBuffer();

			if(Interface.utf8Out){
				readableOutput.close();
			}
		}
	}

	public void writeBuffer() throws IOException{

		Interface.verbosePrint("want to write "+buffer);
		final byte[] byteArray = new BigInteger(buffer, 2).toByteArray();
		byte toWrite = (byte)0;
		for (byte b : byteArray) {
			toWrite=b;
		}
		Interface.verbosePrint("wrote "+Integer.toBinaryString(toWrite& 0xFF));

		if(buffer.length()>0){
			bin.write(toWrite);
			if(Interface.utf8Out){
				readableOutput.write(readableBuffer.getBytes());
			}
			readableBuffer="";
		}
	}

	private static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s).replace(' ', '0');  
	}

	private static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s).replace(' ', '0');  
	}


}
