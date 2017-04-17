/**
 *
 */
package com.ztravel.common.security;

import java.util.Random;

/**
 * @author zuoning.shen
 *
 */
public class RandomVerificationUtil {
    private static final int DEFAULT_LENGTH = 6;

    //Random char set, removed characters uneasy to recognize
    private static final char[] CHAR_CODE = { '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z' };

    /**
     * Generate verification code with default length
     * USING CHAR_CODE
     * @return
     */
    public static String getVerificationCharCode() {
        return getVerificationCharCode(DEFAULT_LENGTH, false);
    }

    /**
     * Generate verification code with default length
     * USING NUM_CODE
     * @return
     */
    public static String getVerificationNumCode() {
        return getVerificationNumCode(DEFAULT_LENGTH, false);
    }

    /**
     *
     * @param length
     * @param isCanRepeat
     * USING CHAR_CODE
     * @return
     */
    public static String getVerificationCharCode(int length, boolean isCanRepeat) {
        int n = CHAR_CODE.length;
        if (length > n && isCanRepeat == false) {
            throw new RuntimeException("Invalid input params!");
        }
        StringBuilder sb= new StringBuilder(length);
        if (isCanRepeat) {
            for (int i = 0; i < length; i++) {
                int r = (int) (Math.random() * n);
                sb.append(CHAR_CODE[r]);
            }
        } else {
            for (int i = 0; i < length; i++) {
                int r = (int) (Math.random() * n);
                sb.append(CHAR_CODE[r]);
                CHAR_CODE[r] = CHAR_CODE[n - 1];
                n--;
            }
        }
        return sb.toString();
    }

    /**
    *
    * @param length
    * @param isCanRepeat
    * USING NUM_CODE
    * @return
    */
   public static String getVerificationNumCode(int length, boolean isCanRepeat) {
	   
	   char[] NUM_CODE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	   
       int n = NUM_CODE.length;
       if (length > n && isCanRepeat == false) {
           throw new RuntimeException("Invalid input params!");
       }
       StringBuilder sb= new StringBuilder(length);
       if (isCanRepeat) {
           for (int i = 0; i < length; i++) {
               int r = (int) (Math.random() * n);
               sb.append(NUM_CODE[r]);
           }
       } else {
           for (int i = 0; i < length; i++) {
               int r = (int) (Math.random() * n);
               sb.append(NUM_CODE[r]);
               NUM_CODE[r] = NUM_CODE[n - 1];
               n--;
           }
       }
       return sb.toString();
   }

    public static void main(String[] args){
    	System.out.println(getVerificationNumCode());
    }
}


