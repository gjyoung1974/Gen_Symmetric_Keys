package com.goyoung.crypto.util.GenKeys;

import java.io.IOException;

import javax.xml.bind.DatatypeConverter;

public class DecodeHex {
	
	public static void main(String[] args) throws IOException{
	
	String s  = "A2A520912EEE0EEF467F4E1FE52C017A44D0E1FA0EE44CD8AD6B4EB4CD81C537";
	byte[] b_key = DatatypeConverter.parseHexBinary(s);
	System.out.write(b_key);
	}
}
