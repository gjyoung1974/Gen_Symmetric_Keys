package com.goyoung.crypto.util.GenKeys;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Load3DESKey {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		String s_Key = "7F3E2F01893DE50B7A3BBA34EFF80D317F3E2F01893DE50B"; // A Hex-string encoded AES Key
		byte[] b_Key = DatatypeConverter.parseHexBinary(s_Key);
		
		
	 	SecretKey key = new SecretKeySpec(b_Key, "DESede");
    	IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());

		Cipher in = Cipher.getInstance("DESede/CBC/PKCS5Padding"); // 3des key
		Cipher out = Cipher.getInstance("DESede/CBC/PKCS5Padding"); // 
		
		in.init(Cipher.ENCRYPT_MODE, key, iv); //initialize input
		out.init(Cipher.DECRYPT_MODE, key, iv); //initialize output

		byte[] enc = in.doFinal("This is a string".getBytes());
		byte[] dec = out.doFinal(enc);

		System.out.println("Encrypted message: "+ DatatypeConverter.printHexBinary(enc));
		System.out.println("Decrypted message: " + new String(dec));

	}
}
