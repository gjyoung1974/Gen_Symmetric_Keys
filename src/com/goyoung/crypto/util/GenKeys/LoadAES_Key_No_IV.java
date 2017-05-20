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

public class LoadAES_Key_No_IV {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		String s_Key = "DF67725AB0935B409DFD89C2BB5B40D8AFC2322618FC8DBA07481DA375D8B0C9"; // A Hex-string encoded AES Key
		byte[] b_Key = DatatypeConverter.parseHexBinary(s_Key);

		SecretKey key = new SecretKeySpec(b_Key, "AES");

		Cipher in = Cipher.getInstance("AES"); // AES using EAX mode
		Cipher out = Cipher.getInstance("AES"); // http://csrc.nist.gov/groups/ST/toolkit/BCM/documents/proposedmodes/eax/eax-spec.pdf

		in.init(Cipher.ENCRYPT_MODE, key); //initialize input
		out.init(Cipher.DECRYPT_MODE, key); //initialize output

		byte[] enc = in.doFinal("This is a string".getBytes());
		byte[] dec = out.doFinal(enc);

		System.out.println("Encrypted message: "+ DatatypeConverter.printHexBinary(enc));
		System.out.println("Decrypted message: " + new String(dec));

	}
}
