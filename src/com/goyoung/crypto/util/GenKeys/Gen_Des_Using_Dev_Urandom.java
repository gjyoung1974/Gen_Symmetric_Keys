package com.goyoung.crypto.util.GenKeys;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/*
 * This class generates a 3TDEA key by grabbing random bytes from /dev/urandom + enforcing odd parity on the key
 * The prints the key and Key Check Value
 */

public class Gen_Des_Using_Dev_Urandom {

	public static void main(String[] args) throws IOException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		byte[] tripleDesKey; // byte array to hold key

		tripleDesKey = process(new File("/dev/random"));// get some randomness from character device at: /dev/urandom

		for (int i = 0; i < tripleDesKey.length; ++i)// test + enforce odd parity on key
		{
			int keyByte = tripleDesKey[i] & 0xFE;//take byte at position i of key then and it to 254
			int parity = 0;
			for (int b = keyByte; b != 0; b >>= 1)
				parity ^= b & 1;//bitwise XOR b and 1
			tripleDesKey[i] = (byte) (keyByte | (parity == 0 ? 1 : 0));
		}

		String s_key = DatatypeConverter.printHexBinary(tripleDesKey); // convert to string representation of  hex-binary
		StringBuilder str = new StringBuilder(s_key);
		int idx = str.length() - 4;

		while (idx > 0) {
			str.insert(idx, "-"); //put some separators between characters to make easy to write down
			idx = idx - 4;
		}

		System.out.println("3Key 3DES");
		System.out.println(str.toString());

		// output the KCV. KCV is encryption of a vector of 16 null bytes
		byte[] null_bytes_16 = new byte[16]; // vector of 16 0 bytes such as: 
		//= { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

		Cipher c = Cipher.getInstance("DESede");//instance of DESede cipher to generate KCV value
		SecretKeySpec sk_s = new SecretKeySpec(tripleDesKey, "DESede");//SecretKeySpec object to hold our 3DES key

		c.init(Cipher.ENCRYPT_MODE, sk_s);
		byte[] cv = c.doFinal(null_bytes_16);
		byte[] b_kcv = Arrays.copyOfRange(cv, 0, 3);
		System.out.println("KCV: " + DatatypeConverter.printHexBinary(b_kcv));

	}

	private static byte[] process(File file) throws IOException {
		byte[] rand = new byte[24];

		try (RandomAccessFile data = new RandomAccessFile(file, "r")) {

			//for (long i = 0, len = 24; i < len; i++) {
				data.readFully(rand);
			
			//}
		}
		return rand;
	}

}
