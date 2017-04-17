/**
 * 
 */
package com.ztravel.mediaserver.cache;

import java.io.Serializable;

/**
 * Simple media info stored in image cache
 * @author zuoning.shen
 *
 */
public class SimpleMedia implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -3923088095344786909L;
    private String fileName;
    private String content;
    
    public SimpleMedia(String fileName, String content){
        this.fileName = fileName;
        this.content = content;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
