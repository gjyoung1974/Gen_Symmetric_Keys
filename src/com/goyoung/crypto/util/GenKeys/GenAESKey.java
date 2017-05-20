package com.goyoung.crypto.util.GenKeys;

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
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

public class GenAESKey {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		// TODO Auto-generated method stub

		KeyGenerator kg = KeyGenerator.getInstance("AES");
		kg.init(256);
		SecretKey key = kg.generateKey();

		
	byte[] null_bytes_16 = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	byte[] one_bytes_16 = new byte[] { 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01};
		
		IvParameterSpec iv_null_aes = new IvParameterSpec(null_bytes_16);
		
		Cipher c = Cipher.getInstance("AES/CBC/NoPadding");
		c.init(Cipher.ENCRYPT_MODE, key, iv_null_aes);
		byte[] cv = c.doFinal(one_bytes_16);
		byte[] b_arrays = Arrays.copyOfRange(cv, 0, 3);
		
		String s_key = DatatypeConverter.printHexBinary(key.getEncoded());
		String s_cv = DatatypeConverter.printHexBinary(cv);
		String s_arrays = DatatypeConverter.printHexBinary(b_arrays);
		System.out.println("Key as string: ");
		
		StringBuilder str = new StringBuilder(s_key);
		int idx = str.length() - 4;

		while (idx > 0)
		{
		    str.insert(idx, "-");
		    idx = idx - 4;
		}
		System.out.println(str.toString());
		
		
		//System.out.println("Check Value: " + s_cv);
		System.out.println("KCV: " + s_arrays);
		
	}

}
