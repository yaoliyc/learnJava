package org.example;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        String s = DigestUtils.sha1Hex("111111");
        System.out.println(s);
    }
}
