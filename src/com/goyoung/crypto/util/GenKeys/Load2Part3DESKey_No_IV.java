package com.goyoung.crypto.util.GenKeys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class Load2Part3DESKey_No_IV {

	public static void main(String[] args) throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			DataLengthException, ShortBufferException, IllegalStateException,
			InvalidCipherTextException {

		String s_Key = "CA8934666FCC030FDB2C694E2B9B57E5"; // A Hex-string
															// encoded AES Key
		byte[] b_Key2 = DatatypeConverter.parseHexBinary(s_Key);

		DESedeEngine engine = new DESedeEngine();
		PaddedBufferedBlockCipher encryptCipher = new PaddedBufferedBlockCipher(
				engine);
		encryptCipher.init(true, new KeyParameter(b_Key2));

		CipherParameters params = new KeyParameter(b_Key2);

		engine.init(true, params);

		byte[] null_bytes_16 = new byte[16]; // same as: byte[] _iv = new byte[]
												// { 0, 0, 0, 0, 0, 0, 0, 0, 0,
												// 0, 0, 0, 0, 0, 0, 0 };
		byte[] keyhex = Hex.encode(b_Key2);

		System.out.println("Key: " + new String(keyhex).toUpperCase());

		DesDEBC desdebc = new DesDEBC(b_Key2);
		ByteArrayInputStream in = new ByteArrayInputStream(null_bytes_16);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		desdebc.encrypt(in, null_bytes_16.length, out);
		byte[] b_arrays = Arrays.copyOfRange(out.toByteArray(), 0, 2);

		System.out.println("KCV: "
				+ new String(Hex.encode(b_arrays)).toUpperCase());

	}
}
