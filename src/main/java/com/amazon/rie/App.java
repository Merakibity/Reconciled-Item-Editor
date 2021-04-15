package com.amazon.rie;

import java.io.IOException;

/**
 * Hello world!
 *  
 */
public class App {
    public static void main(String[] args) throws IOException {
        reconItemEdit ri = new reconItemEdit();
        reconItemEdit.createAndStartService();
        ri.createDriver();
        int l = ri.ketData();
        for (int i = 1; i <= l; i++) {
            ri.getValues(i);
            try {
                ri.changeTheDumb();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}