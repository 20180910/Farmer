package com.zhizhong.farmer;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

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
    public void wew() throws Exception {

        System.out.println(getPrice(2.2));  //111231.56
        System.out.println(getPrice(111231.5585));
        System.out.println(getPrice(2.25));
        System.out.println(getPrice(2.256));
        System.out.println(getPrice(2.250));
    }
    public double getPrice(double num){
        BigDecimal b = new BigDecimal(num);
        double result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
    @Test
    public void aaa() throws Exception {
        StringBuffer farmer=new StringBuffer("a,d,da,d,");
        System.out.println(farmer.deleteCharAt(farmer.length()-1));
        StringBuffer farmer2=new StringBuffer("a,d,da,d,");
        System.out.println(farmer2.deleteCharAt(farmer2.lastIndexOf(",")));
    }
    @Test
    public void bbb() throws Exception {
        aa();
    }

    private void aa() {
        for (int i = 0; i < 3; i++) {
            if(i==2){
                System.out.println(i);
                break;
            }
        }
        System.out.println(1111111111);
    }
}