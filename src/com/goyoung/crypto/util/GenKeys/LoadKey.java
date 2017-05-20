package com.goyoung.crypto.util.GenKeys;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
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
//import org.bouncycastle.util.encoders.Hex;

public class LoadKey {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		Security.addProvider(new BouncyCastleProvider());//load the bouncy castle security provider:
		
		//A Hex-string encoded AES Key
		String s_Key = "A2A520912EEE0EEF467F4E1FE52C017A44D0E1FA0EE44CD8AD6B4EB4CD81C537";
		
		BigInteger nOnce = new BigInteger(256, new Random());//random BigInteger as "nonce" for IV
		//String s_Iv =  "62EC67F9C3A4A407FCB2A8C49031A8B3"; // Hex-string encoded Initialization Vector
		
		//byte[] bKey = Hex.decode(s_Input);//bouncy castle way to convert HexString to byte[]
		byte[] bKey = DatatypeConverter.parseHexBinary(s_Key);//Java standard way to do Hex string to byte
		
		//A Hex-string encoded IV'
		//byte[] bIv = Hex.decode(s_Iv);//bouncy castle way to convert HexString to byte[]
		byte[] bIv = nOnce.toByteArray();//DatatypeConverter.parseHexBinary(s_Iv);//Java standard way to do Hex string to byte
		
		SecretKey key    = new SecretKeySpec(bKey, "AES");
		
		Cipher in = Cipher.getInstance("AES/EAX/NoPadding", "BC");// AES using EAX mode
        Cipher out = Cipher.getInstance("AES/EAX/NoPadding", "BC");//http://csrc.nist.gov/groups/ST/toolkit/BCM/documents/proposedmodes/eax/eax-spec.pdf

        in.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(bIv));//in.init(Cipher.ENCRYPT_MODE, key);

        out.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(bIv));//out.init(Cipher.DECRYPT_MODE,key);
        
        byte[] enc = in.doFinal("This is a string".getBytes());
        
        byte[] dec = out.doFinal(enc);
        
   
		System.out.println("Encrypted message: " + DatatypeConverter.printHexBinary(enc));
		System.out.println("Decrypted message: " + new String(dec));
		
	}

}
