package com.goyoung.crypto.util.GenKeys;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Gen_Key_Via_RNG {

	public static void main(String[] args) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {

		SecureRandom rng = new SecureRandom(); // instantiate RNG

		byte[] tripleDesKey = new byte[24];// create 24 byte array to hold
											// Random #

		rng.nextBytes(tripleDesKey);// get next rand

		for (int i = 0; i < tripleDesKey.length; ++i)// enforce odd parity on
														// key
		{
			int keyByte = tripleDesKey[i] & 0xFE;
			int parity = 0;
			for (int b = keyByte; b != 0; b >>= 1)
				parity ^= b & 1;
			tripleDesKey[i] = (byte) (keyByte | (parity == 0 ? 1 : 0));
		}

		String s_key = DatatypeConverter.printHexBinary(tripleDesKey);// convert to hexbinary string
		StringBuilder str = new StringBuilder(s_key);
		int idx = str.length() - 4;

		while (idx > 0) {
			str.insert(idx, "-");
			idx = idx - 4;
		}

		System.out.println("3Key 3DES");
		System.out.println(str.toString());

		// output the KCV
		byte[] null_bytes_16 = new byte[16]; // vector of 16 0 bytes such as:  = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,  0x00};

		// Cipher c = Cipher.getInstance("DESede/ECB/NoPadding");
		Cipher c = Cipher.getInstance("DESede");
		SecretKeySpec sk_s = new SecretKeySpec(tripleDesKey, "DESede");

		c.init(Cipher.ENCRYPT_MODE, sk_s);
		byte[] cv = c.doFinal(null_bytes_16);
		byte[] b_kcv = Arrays.copyOfRange(cv, 0, 3);
		System.out.println("KCV: " + DatatypeConverter.printHexBinary(b_kcv));

	}

}
