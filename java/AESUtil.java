package com.test;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * AES加解密工具类
 * @.Desc AES对称加密算法，调用方法： 
 * 1) 对明文进行加密，并对密文进行Base64编码：AESUtil.encrypt(plainText, keyStr); plainText为需要加密的字符串，keyStr为密钥。 
 * 2) 先对密文进行Base64解码，然后再进行解密：AESUtil.decrypt(secretText, keyStr); secretText为需要解密的字符串，keyStr为密钥。
 */
public class AESUtil {
	/**
	 * 默认AES Key
	 */
	private static final String DEFAULT_AES_KEY_STR = "9875cce6826dbc1fc9083c12c6d75642";
	
	private static final String CHARSET = "UTF-8";
	
	private static final String ENCRYPT = "AES";
	// ECB 模式下不适用 向量定义
	//CBC模式需要制定向量
	private static final String ENCRYPT_TYPE = "AES/CBC/PKCS7Padding"; 

	private static final String PROVIDER = "BC";
	
	private static final String IV = "0123456789ABCDEF";
	
	/**
	 * 加密，并对密文进行Base64编码，采用默认密钥
	 * @param plainText
	 * 					明文
	 * @return String
	 * 		  			做了Base64编码的密文
	 * @throws Exception
	 */
	public static String encrypt(String plainText) throws Exception{
		return encrypt(plainText, DEFAULT_AES_KEY_STR);
	}
	
	
	 public static IvParameterSpec makeIv() throws UnsupportedEncodingException,  
	     DecoderException {  
		 return new IvParameterSpec(IV.getBytes(CHARSET));  
	 }
	
	/**
	 * 加密，并对密文进行Base64编码，可指定密钥
	 * @param plainText
	 * 					明文
	 * @param keyStr
	 * 					密钥
	 * @return String
	 * 		  			做了Base64编码的密文
	 * @throws Exception
	 */
	public static String encrypt(String plainText, String keyStr) throws Exception{
		try {
			byte[] keyBytes = keyStr.getBytes(CHARSET);
			Key ckey = new SecretKeySpec(keyBytes, ENCRYPT);
	        Security.addProvider(new BouncyCastleProvider());
			Cipher cp = Cipher.getInstance(ENCRYPT_TYPE,PROVIDER);
			IvParameterSpec iv = makeIv();
			cp.init(Cipher.ENCRYPT_MODE, ckey,iv);
			byte[] inputByteArray = plainText.getBytes(CHARSET);
			byte[] cipherBytes = cp.doFinal(inputByteArray);
			String result = Base64.encodeBase64String(cipherBytes);
			result = result.replace("+", "%2b");
			result = result.replace("\r\n", "").replace("\n", "");
			return result;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 对做了Base64编码的密文进行解密，采用默认密钥
	 * @param secretText
	 * 						做了Base64编码的密文
	 * @return String
	 * 						解密后的字符串      
	 * @throws Exception
	 */
	public static String decrypt(String secretText) throws Exception{
		return decrypt(secretText, DEFAULT_AES_KEY_STR);
	}
	
	/**
	 * 对做了Base64编码的密文进行解密
	 * @param secretText
	 * 						做了Base64编码的密文
	 * @param keyStr
	 * 						密钥
	 * @return String
	 * 						解密后的字符串      
	 * @throws Exception
	 */
	public static String decrypt(String secretText, String keyStr) throws Exception{
		secretText = secretText.replace("%2b", "+");
		byte[] cipherByte = Base64.decodeBase64(secretText);
		byte[] keyBytes = keyStr.getBytes(CHARSET);
		try {
			Key ckey = new SecretKeySpec(keyBytes, ENCRYPT);
			Security.addProvider(new BouncyCastleProvider());
			Cipher cp = Cipher.getInstance(ENCRYPT_TYPE,PROVIDER);
			IvParameterSpec iv = makeIv();
			cp.init(Cipher.DECRYPT_MODE, ckey,iv);
			byte[] decryptBytes = cp.doFinal(cipherByte);
			return new String(decryptBytes, CHARSET).replace("", "");
		} catch (Exception e) {
			throw e;
		}		
	}	
	
	// 测试
	public static void main(String[] args) {
		try {
//			String content = "hello world";
//			System.out.println("加密前：" + content);
			// 加密
//			String encryptResult = encrypt(content);
//			System.out.println("加密后：" + encryptResult);
			// 解密
			String encryptResult = "3Zziz5nCWyoXrFXtMXuiyg==";
			String decryptResult = decrypt(encryptResult);//decrypt("MfNVg%2bLiK9QzCien5EirmDRenFRwmuteJH0xlPi04k4aHqVne8sjmxtkwM9uL7I6OGC4gzSxbs13h9kpsWLqxA==");//decrypt(base64_encryptResult);
			System.out.println("解密后：" + decryptResult);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
