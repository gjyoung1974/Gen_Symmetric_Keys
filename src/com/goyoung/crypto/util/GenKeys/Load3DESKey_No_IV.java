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
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Load3DESKey_No_IV {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		String s_Key = "AD85866773D3A445EC7AC7CB0BBC49A8ADD602AD1C0B8CFB"; // A Hex-string encoded AES Key
		byte[] b_Key = DatatypeConverter.parseHexBinary(s_Key);
				
	 	SecretKey key = new SecretKeySpec(b_Key, "DESede");

		Cipher in = Cipher.getInstance("DESede"); // 3DES
		Cipher out = Cipher.getInstance("DESede"); 
		
		in.init(Cipher.ENCRYPT_MODE, key); //initialize input
		out.init(Cipher.DECRYPT_MODE, key); //initialize output

		byte[] enc = in.doFinal("This is a string".getBytes());
		byte[] dec = out.doFinal(enc);

		System.out.println("Encrypted message: "+ DatatypeConverter.printHexBinary(enc));
		System.out.println("Decrypted message: " + new String(dec));

	}
}
