package com.info.common.util;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


public class GUIDGenerator extends Object {
   private static Random myRand;               //基本java随机对象
   private static SecureRandom mySecureRand;   //安全随机对象

   private static String s_id;                 //ip地址字符串

   static
   {
      mySecureRand = new SecureRandom();
      //用安全随机对象产生一随机数并用该随机数初始化基本java随机对象
      long secureInitializer = mySecureRand.nextLong();
      myRand = new Random(secureInitializer);

      try
      {
         //获得当前主机的ip地址字符串
         s_id = InetAddress.getLocalHost().toString();
      }
      catch (UnknownHostException e)
      {
         e.printStackTrace();
      }
   }

   
   public GUIDGenerator()
   {
   }

   
   public static String genRandomGUID()
   {
      return genRandomGUID(false);
   }

   
   public static String genRandomGUID(boolean secure)
   {
      String valueBeforeMD5 = "";          //消息消化对象消化前的字符串
      String valueAfterMD5 = "";           //经消息消化对象消化后的GUID字符串

      MessageDigest md5 = null;   //消息消化对象
      StringBuffer sbValueBeforeMD5 = new StringBuffer();

      try
      {
         md5 = MessageDigest.getInstance("MD5");
      }
      catch (NoSuchAlgorithmException e)
      {
         System.out.println("Error: " + e);
         return valueBeforeMD5;
      }

      long time = System.currentTimeMillis();   //获得系统时间
      long rand = 0;                            //随机数

      if (secure)   //用安全随机对象获得随机数
      {
         rand = mySecureRand.nextLong();
      }
      else          //用基本随机对象获得随机数
      {
         rand = myRand.nextLong();
      }

      //拼接组成GUID的各个信息
      sbValueBeforeMD5.append(s_id);
      sbValueBeforeMD5.append(":");
      sbValueBeforeMD5.append(Long.toString(time));
      sbValueBeforeMD5.append(":");
      sbValueBeforeMD5.append(Long.toString(rand));
      valueBeforeMD5 = sbValueBeforeMD5.toString();
      md5.update(valueBeforeMD5.getBytes());
      byte[] array = md5.digest();   //消息消化对象进行消化动作，返回128bit

      String strTemp = "";
      for (int i = 0; i < array.length; i++)
      {
         strTemp = (Integer.toHexString(array[i] & 0XFF));
         if (strTemp.length() == 1)
         {
            valueAfterMD5 = valueAfterMD5 + "0" + strTemp;
         }
         else
         {
            valueAfterMD5 = valueAfterMD5 + strTemp;
         }
      }
      //GUID标准格式如：C2FEEEAC-CFCD-11D1-8B05-00600806D9B6
      return valueAfterMD5.toUpperCase();
   }

   public static void main(String args[])
   {
      for(int i=1; i<10; i++)
      {
         System.out.println(Integer.toString(i) + " : " + genRandomGUID());
      }
   }
}