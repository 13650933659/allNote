package com.hsp.servlet;

import java.security.MessageDigest;

//����һ��MD5���ܵķ���

public class MD5_test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String md5text=getMD5("��˳ƽ");
		System.out.println("���ִ���md5��Ӧ��ֵ�ǣ�"+md5text);
	}

	public final static String getMD5(String s){
		char[] hexDigits={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		try{
			byte[] strTemp=s.getBytes();
			MessageDigest mdTemp=MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md=mdTemp.digest();
			int j=md.length;
			char[] str=new char[j*2];
			int k=0;
			for(int i=0;i<j;i++){
				byte byte0=md[i];
				str[k++]=hexDigits[byte0 >>> 4 & 0xf];
				str[k++]=hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}catch(Exception e){
			return null;
		}
	} 
}
