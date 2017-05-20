package com.goyoung.crypto.util.GenKeys;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.util.encoders.Hex;

public class Gen3DESKey {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		KeyGenerator kg = KeyGenerator.getInstance("DESede");
		kg.init(168); // key length 112 for two keys, 168 for three keys
		SecretKey sk = kg.generateKey();

		
		// The KCV is the "Key Check Value" for the key, calculated by assuming the key/components are 3DES keys, and encrypting a string of binary zeroes. The KCV is the first six hex digits of the resulting ciphertext.
		byte[] null_bytes_16 = new byte[16];// { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
		
		//Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");
		Cipher c = Cipher.getInstance("DESede");
	
		c.init(Cipher.ENCRYPT_MODE, sk);
		byte[] cv = c.doFinal(null_bytes_16);
		byte[] b_arrays = Arrays.copyOfRange(cv, 0, 2);
		
		String s_key = DatatypeConverter.printHexBinary(sk.getEncoded());
	
		//System.out.println(new BigInteger(sk.getEncoded()));
		
		System.out.println("3Key 3DES");
		System.out.println(s_key);

		System.out.println("KCV: " + DatatypeConverter.printHexBinary(b_arrays));
	}
}
