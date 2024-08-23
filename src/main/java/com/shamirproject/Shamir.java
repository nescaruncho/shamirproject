package com.shamirproject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Shamir {
    
    //Función que genera coeficienes aleatorios para el polinomio
    public static BigInteger[] generarCoeficientes (int min, BigInteger secreto, BigInteger primo, SecureRandom random) {
        BigInteger[] coeficientes = new BigInteger[min];
        coeficientes[0] = secreto;
        for (int i = 1; i < min; i++) {
            coeficientes[i] = new BigInteger(primo.bitLength(), random).mod(primo);
        }
        return coeficientes;
    }

    //Función que genera las partes en las que se divide el secreto
    public static List<BigInteger[]> generarPartes(int numPartes, int min, BigInteger secreto, BigInteger primo) {
        SecureRandom random = new SecureRandom();
        BigInteger[] coeficientes = generarCoeficientes(min, secreto, primo, random);
        List<BigInteger[]> partes = new ArrayList<>();

        for (int i = 1; i < numPartes; i++) {
            BigInteger x = BigInteger.valueOf(i);
            BigInteger y = coeficientes[0];

            for (int j = 1; j < min; j++) {
                y = y.add(coeficientes[j].multiply(x.pow(j)).mod(primo)).mod(primo);
            }

            partes.add(new BigInteger[]{x, y});
        }

        return partes;
    }

    //Función de reconstrucción del secreto
    public static BigInteger reconstruccionSecreto(List<BigInteger[]> partes, BigInteger primo) {
        BigInteger secreto = BigInteger.ZERO;

        for (int i = 0; i < partes.size(); i++) {
            BigInteger[] parte = partes.get(i);
            BigInteger xi = parte[0];
            BigInteger yi = parte[1];

            BigInteger numerador = BigInteger.ONE;
            BigInteger denominador = BigInteger.ONE;

            for (int j = 0; j < partes.size(); j++) {
                if (i !=j) {
                    BigInteger xj = partes.get(j)[0];
                    numerador = numerador.multiply(xj.negate()).mod(primo);
                    denominador = denominador.multiply(xi.subtract(xj)).mod(primo);
                }
            }

            BigInteger lagrange = yi.multiply(numerador).multiply(denominador.modInverse(primo)).mod(primo);

            secreto = secreto.add(lagrange).mod(primo);
        }

        return secreto;
    }
}
