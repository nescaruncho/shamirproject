package com.shamirproject;

import java.math.BigInteger;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        BigInteger secreto = new BigInteger("1234");
        int n = 5;
        int minN = 3;
        BigInteger primo = new BigInteger("2089");

        List<BigInteger[]> partes = Shamir.generarPartes(n, minN, secreto, primo);

        System.out.println("Partes generadas: ");
        for (BigInteger[] parte : partes) {
            System.out.println("x = " + parte[0] + ", y = " + parte[1]);
        }

        List<BigInteger[]> subconjuntoPartes = partes.subList(0, minN);
        BigInteger secretoReconstruido = Shamir.reconstruccionSecreto(subconjuntoPartes, primo);

        System.out.println("Secreto reconstruido: " + secretoReconstruido);
    }
}
