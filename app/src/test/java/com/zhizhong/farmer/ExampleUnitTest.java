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
    public void asdasdff() throws Exception {
        String a="sendno=116517999&app_key=575b9f5d70bb3d3244558125&masterSecret=478e06c2ccba09b0e7cb6161&receiver_type=5&receiver_value=&msg_type=1&msg_content={\"n_builder_id\":\"00\",\"n_title\":\"订单执行完成\",\"n_content\":\"您的订单N201710271048305498飞首已执行完成,请注意查看\"}&platform=android,ios&verification_code=A80DA28BC901329F2E546197C7B5EDE7&apns_production=0";
        byte[]aa=a.getBytes();
        System.out.println(aa);
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
    @Test
    public void asdf() throws Exception {
        String a="0910";
        System.out.println(Integer.parseInt(a));
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