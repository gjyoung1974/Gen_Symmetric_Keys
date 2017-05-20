package com.goyoung.crypto.util.GenKeys;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
//import java.util.Base64;

import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

public class Gen_2Key_3DES {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// TODO Auto-generated method stub

		KeyGenerator kg = KeyGenerator.getInstance("DESede");
		kg.init(112);
		SecretKey sk = kg.generateKey();
		
		// The KCV is the "Key Check Value" for the key, calculated by assuming the key/components are 3DES keys, and encrypting a string of binary zeroes. The KCV is the first six hex digits of the resulting ciphertext.
		byte[] null_bytes_16 = new byte[16];// { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
		
		//Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");
		Cipher c = Cipher.getInstance("DESede");
		
	
		c.init(Cipher.ENCRYPT_MODE, sk);
		byte[] cv = c.doFinal(null_bytes_16);
		byte[] b_arrays = Arrays.copyOfRange(cv, 0, 3);
		
		String s_key = DatatypeConverter.printHexBinary(sk.getEncoded());

	
		StringBuilder str = new StringBuilder(s_key);
		int idx = str.length() - 4;

		while (idx > 0)
		{
		    str.insert(idx, "-");
		    idx = idx - 4;
		}

		System.out.println("2Key 3DES");
		System.out.println(str.toString());

		System.out.println("KCV: " + DatatypeConverter.printHexBinary(b_arrays));

	
	}

}
