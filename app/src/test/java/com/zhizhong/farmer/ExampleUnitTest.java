package com.zhizhong.farmer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void aaa() throws Exception {
        StringBuffer farmer=new StringBuffer("a,d,da,d,");
        System.out.println(farmer.deleteCharAt(farmer.length()-1));
        StringBuffer farmer2=new StringBuffer("a,d,da,d,");
        System.out.println(farmer2.deleteCharAt(farmer2.lastIndexOf(",")));
    }
}