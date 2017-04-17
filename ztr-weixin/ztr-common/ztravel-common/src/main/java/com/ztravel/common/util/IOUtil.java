package com.ztravel.common.util;

import java.io.IOException;
import java.io.InputStream;


/**
 * 
 * @author wanhaofan
 *
 */
public class IOUtil {
	public static byte[] readAsByteArray(InputStream inStream) throws IOException {
        int size = 1024;
        byte[] buff = new byte[size];
        int readSoFar = 0;
        while (true) {
            int nRead = inStream.read(buff, readSoFar, size - readSoFar);
            if (nRead == -1) {
                break;
            }
            readSoFar += nRead;
            if (readSoFar == size) {
                int newSize = size * 2;
                byte[] newBuff = new byte[newSize];
                System.arraycopy(buff, 0, newBuff, 0, size);
                buff = newBuff;
                size = newSize;
            }
        }
        byte[] newBuff = new byte[readSoFar];
        System.arraycopy(buff, 0, newBuff, 0, readSoFar);
        return newBuff;
    }
}
