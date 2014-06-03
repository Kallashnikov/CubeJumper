package com.me.cubejumper.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class FileManager
{
	/**
	 * @param key - needs to be at least 8 characters for DES
	 * @param inputPath - input file to be encrypted
	 * @param outputPath - output for new encrypted file
	 * @param mode - 0 = encrypt, 1 = decrypt<p>
	 * 
	 * @author Jacob
	 */
	public FileManager(String key, String inputPath, String outputPath, int mode) {
		if(mode == 0){	
			try {
				FileInputStream fis = new FileInputStream(inputPath);
				FileOutputStream fos = new FileOutputStream(outputPath);
				encrypt(key, fis, fos);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}else if(mode == 1){
			try {
				FileInputStream fis = new FileInputStream(inputPath);
				FileOutputStream fos = new FileOutputStream(outputPath);
				decrypt(key, fis, fos);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void encrypt(String key, InputStream is, OutputStream os) throws Throwable {
		encryptOrDecrypt(key, Cipher.ENCRYPT_MODE, is, os);
	}

	public static void decrypt(String key, InputStream is, OutputStream os) throws Throwable {
		encryptOrDecrypt(key, Cipher.DECRYPT_MODE, is, os);
	}
	
	public static void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) throws Throwable {

		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

		if (mode == Cipher.ENCRYPT_MODE) {
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			CipherInputStream cis = new CipherInputStream(is, cipher);
			doCopy(cis, os);
		} else if (mode == Cipher.DECRYPT_MODE) {
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			doCopy(is, cos);
		}
	}

	public static void doCopy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}
	
	public static void deleteFile(String path){
	    // A File object to represent the filename
	    File f = new File(path);

	    // Make sure the file or directory exists and isn't write protected
	    if (!f.exists())
	      throw new IllegalArgumentException(
	          "Delete: no such file or directory: " + path);

	    if (!f.canWrite())
	      throw new IllegalArgumentException("Delete: write protected: "
	          + path);

	    // If it is a directory, make sure it is empty
	    if (f.isDirectory()) {
	      String[] files = f.list();
	      if (files.length > 0)
	        throw new IllegalArgumentException(
	            "Delete: directory not empty: " + path);
	    }

	    // Attempt to delete it
	    boolean success = f.delete();

	    if (!success)
	      throw new IllegalArgumentException("Delete: deletion failed");
	}
}
