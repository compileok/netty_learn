package com.dmtest.netty_learn.utils;

import java.io.Closeable;

/**
 *
 * 关闭流工具类
 * 2018/9/1.
 */
public class CloseUtil {

    public static void close(Closeable res){
        if( res == null) {
            return;
        }

        try{
            res.close();
        }catch (Exception e){
            // nothing to do
        }

    }

}
