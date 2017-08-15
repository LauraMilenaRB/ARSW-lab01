package edu.eci.arsw.math;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

///  <summary>
///  An implementation of the Bailey-Borwein-Plouffe formula for calculating hexadecimal
///  digits of pi.
///  https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula
///  *** Translated from C# code: https://github.com/mmoroney/DigitsOfPi ***
///  </summary>

public class PiDigits {
    private static final HashMap<Integer,byte[]> resp=new HashMap<>();
    /**
     * Returns a range of hexadecimal digits of pi.
     * @param start The starting location of the range.
     * @param count The number of digits to return
     * @param numhilos
     * @return An array containing the hexadecimal digits.
     */
    public static byte[] getDigits(int start, int count, int numhilos) {
        if (start < 0) {
            throw new RuntimeException("Invalid Interval");
        } else if (count < 0) {
            throw new RuntimeException("Invalid Interval");
        } else if (numhilos < 0 || numhilos > count) {
            throw new RuntimeException("Invalid Number Thread");
        }
        Thread[] hilos = new Thread[numhilos];
        int rangob = count / numhilos;
        int sobrante = count % numhilos;

        for (int i = 0; i < numhilos; i++) {
            if (sobrante != 0) {
                sobrante -= 1;
                hilos[i] = new PiThread(start, rangob + 1, i);
            } else {
                hilos[i] = new PiThread(start, rangob, i);
            }
            hilos[i].start();
            start += rangob;
        }
        for (int i = 0; i < numhilos; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException ex) {
                Logger.getLogger(PiDigits.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resp(numhilos, count);
    }
    private static byte[] resp(int nn,int count){
        byte[] respuesta=new byte[count];int cont=0;byte[] r;
        for(int i=0;i<nn;i++){
            r=resp.get(i);
            for(int j=0;j<r.length;j++){
                respuesta[cont]=r[j];cont+=1;
            }
        }
        return respuesta;
    }
    
    public synchronized static void addResp(int p,byte[] d) {
        resp.put(p, d);
    }
    
    
}
