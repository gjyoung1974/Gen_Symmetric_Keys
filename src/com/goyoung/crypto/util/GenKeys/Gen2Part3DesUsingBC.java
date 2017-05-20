package com.goyoung.crypto.util.GenKeys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class Gen2Part3DesUsingBC {

	public static void main(String[] args) throws DataLengthException,
			IllegalStateException, InvalidCipherTextException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException,
			InvalidKeySpecException, ShortBufferException {

		SecureRandom sr = new SecureRandom();

		CipherKeyGenerator generator = new CipherKeyGenerator();
		generator.init(new KeyGenerationParameters(sr, 128));
		byte[] key = generator.generateKey();

		DESedeEngine engine = new DESedeEngine();
		PaddedBufferedBlockCipher encryptCipher = new PaddedBufferedBlockCipher(engine);
		encryptCipher.init(true, new KeyParameter(key));

		CipherParameters params = new KeyParameter(key);

		engine.init(true, params);

		
		byte[] null_bytes_16 = new byte[16]; // same as: byte[] _iv = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }; 
		byte[] keyhex = Hex.encode(key);

		System.out.println("Key: " + new String(keyhex).toUpperCase());

		DesDEBC desdebc = new DesDEBC(key);
		ByteArrayInputStream in = new ByteArrayInputStream(null_bytes_16);
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		desdebc.encrypt(in, null_bytes_16.length, out);
		byte[] b_arrays = Arrays.copyOfRange(out.toByteArray(), 0, 2);

		System.out.println("KCV: " + new String(Hex.encode(b_arrays)).toUpperCase());

	}
}