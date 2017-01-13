package com.app.temproject.shipper;

import com.app.temproject.shipper.Libs.MoneyProcessing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Example local unit st_notification_item, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(Parameterized.class)
public class ExampleUnitTest {

    @Parameterized.Parameter
    private String amountInWords;

    @Parameterized.Parameter
    private int amountInFigures;

    public  ExampleUnitTest(String mAmountInWords, int mAmountInFigures){
        this.amountInFigures = mAmountInFigures;
        this.amountInWords = mAmountInWords;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"1 tỷ đồng", 1000000},
                {"9 tỉ 950 triệu 452 nghìn đồng", 9950452},
                {"500 triệu đồng", 500000},
                {"5 tỉ đồng", 5000000},
                {"5 tỉ 30 nghìn đồng", 5000030},
                {"6 triệu 27 nghìn đồng", 6027},
                {"90 tỉ đồng", 90000000},
                {"8 triệu đồng", 8000},
                {"342 nghìn đồng", 342},
                {"99 nghìn đồng", },
                {"4 nghìn đồng", 4}};
        return Arrays.asList(data);
    }


    @Test
    public void configVNDTest() throws Exception {
        assertEquals("Kết quả", amountInWords, MoneyProcessing.configVNDValue(amountInFigures));
    }


    @Test
    public void addTwoNumberTest() throws Exception {
        assertEquals("Kết quả", 1 + 1, 2);
    }
}