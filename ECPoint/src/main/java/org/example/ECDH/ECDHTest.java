package org.example.ECDH;

import org.bouncycastle.math.ec.ECPoint;
import org.example.ECPoint.ECCWrapper;

import java.math.BigInteger;

public class ECDHTest {
    public static void main(String[] args) {
        ECPoint G = ECCWrapper.basePointGGet();

        // Учасник 1
        BigInteger privKey1 = ECCWrapper.setRandom(ECCWrapper.curveParams.getN().bitLength());
        ECPoint pubKey1 = ECCWrapper.scalarMult(privKey1, G);

        // Учасник 2
        BigInteger privKey2 = ECCWrapper.setRandom(ECCWrapper.curveParams.getN().bitLength());
        ECPoint pubKey2 = ECCWrapper.scalarMult(privKey2, G);

        // Обмін відкритими ключами
        ECPoint exchangedPubKey1 = pubKey2;
        ECPoint exchangedPubKey2 = pubKey1;

        // Учасник 1 обчислює спільний секрет
        BigInteger sharedSecret1 = ECCWrapper.generateSharedSecret(privKey1, exchangedPubKey1);
        String sharedSecretHex1 = sharedSecret1.toString(16);
        System.out.println("Спільний секрет учасника 1 (hex): " + sharedSecretHex1);

        // Учасник 2 обчислює спільний секрет
        BigInteger sharedSecret2 = ECCWrapper.generateSharedSecret(privKey2, exchangedPubKey2);
        String sharedSecretHex2 = sharedSecret2.toString(16);
        System.out.println("Спільний секрет учасника 2 (hex): " + sharedSecretHex2);

        // Порівняння спільних секретів
        boolean secretsMatch = sharedSecretHex1.equals(sharedSecretHex2);
        System.out.println("Секрети співпадають: " + secretsMatch);
    }
}

