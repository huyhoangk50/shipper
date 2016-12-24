package com.app.temproject.shipper.Libs;

/**
 * Created by Admin on 17/12/2016.
 */

public class MoneyProcessing {
    private static final int ONE_BILLION = 1000000000;
    private static final int ONE_MILLION = 1000000;
    private static final int ONE_THOUSAND = 1000;

    private static final String BILLION = " tỉ ";
    private static final String MILLION = " triệu ";
    private static final String THOUSAND = " nghìn ";
    private static final String VND = " đồng ";


    public static String configVNDValue(int amountInFigures){
        String amountInWords = "";
        if(amountInFigures > ONE_BILLION){
            if(amountInFigures%ONE_BILLION == 0){
                return (amountInFigures/ONE_BILLION) + BILLION + VND;
            } else {
                return (amountInFigures/ONE_BILLION) + BILLION + configVNDValueLessThanOneBillion(amountInFigures - ONE_BILLION * amountInFigures/ONE_BILLION);
            }
        } else return configVNDValueLessThanOneBillion(amountInFigures);
    }

    private static String configVNDValueLessThanOneBillion(int amountInFigures) {
        if(amountInFigures > ONE_MILLION){
            if (amountInFigures % ONE_MILLION == 0){
                return (amountInFigures/ONE_MILLION) + MILLION + VND;
            } else {
                return (amountInFigures/ONE_MILLION) + MILLION + configVNDValueLessThanOneMillion(amountInFigures - ONE_MILLION * amountInFigures/ONE_MILLION);
            }
        } else {
            return configVNDValueLessThanOneMillion(amountInFigures);
        }
    }

    private static String configVNDValueLessThanOneMillion(int amountInFigures) {
        if(amountInFigures > ONE_THOUSAND){
            if (amountInFigures % ONE_THOUSAND == 0){
                return (amountInFigures/ONE_THOUSAND) + THOUSAND + VND;
            } else {
                return (amountInFigures/ONE_THOUSAND) + THOUSAND + (amountInFigures - ONE_THOUSAND * amountInFigures/ONE_THOUSAND) + VND;
            }
        } else {
            return amountInFigures + VND;
        }

    }
}
