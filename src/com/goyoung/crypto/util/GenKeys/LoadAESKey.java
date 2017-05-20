package com.goyoung.crypto.util.GenKeys;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
//import org.bouncycastle.util.encoders.Hex;

public class LoadAESKey {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		Security.addProvider(new BouncyCastleProvider());//load the bouncy castle security provider:
		
		
		String s_Key = "DF67725AB0935B409DFD89C2BB5B40D8AFC2322618FC8DBA07481DA375D8B0C9"; //A Hex-string encoded AES Key
		byte[] bKey = Hex.decode(s_Key);
		
		//nOnce as Random BigInteger as "nonce" for IV
		byte[] bIv = new BigInteger(256, new Random()).toByteArray();
		
		SecretKey key = new SecretKeySpec(bKey, "AES");
		
		Cipher in = Cipher.getInstance("AES/EAX/NoPadding", "BC");// AES using EAX mode
        Cipher out = Cipher.getInstance("AES/EAX/NoPadding", "BC");//http://csrc.nist.gov/groups/ST/toolkit/BCM/documents/proposedmodes/eax/eax-spec.pdf

        in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(bIv));

        out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(bIv));
        
        //Message to encrypt
        String s_Input_Message = 
        		"Lorem ipsum dolor sit amet, consectetur adipiscing elit,\n"
        		+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n"
        		+ "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris\n"
        		+ "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in\n"
        		+ "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.\n"
        		+ "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia\n"
        		+ "deserunt mollit anim id est laborum.";
        		
        byte[] enc = in.doFinal(s_Input_Message.getBytes());
        
        byte[] dec = out.doFinal(enc);
        String s_b64_enc = Base64.getEncoder().encodeToString(enc);
   
		System.out.println("Encrypted message, Hex encoding: " + DatatypeConverter.printHexBinary(enc) + "\n");
		System.out.println("Encrypted message, Base64 encoding: " + s_b64_enc.toUpperCase() +"\n");
		System.out.println("Decrypted message:\n" + new String(dec));
		
	}

}
