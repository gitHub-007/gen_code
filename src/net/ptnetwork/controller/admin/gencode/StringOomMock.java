package net.ptnetwork.controller.admin.gencode;

import java.util.ArrayList;
import java.util.List;

public class StringOomMock {
    static String base = "string";

    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder("");
        List<String> list = new ArrayList<String>();
        try {

            for (int i = 0; i < 20; i++) {
                base = base+base;
//            if (i>=5) break;
                System.out.println(String.format("%d:%d",i,(base.length()/"string".length())/2));
//            list.add(str.intern());
            }
        }catch (Exception e){

        }
    }
}