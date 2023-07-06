package org.example.ECPoint;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Hex;

public class ECCWrapper {
    private static final SecureRandom random = new SecureRandom();
    public static final X9ECParameters curveParams = CustomNamedCurves.getByName("secp256k1");
    private static final ECDomainParameters domainParams = new ECDomainParameters(curveParams.getCurve(), curveParams.getG(), curveParams.getN(), curveParams.getH());

    public static ECPoint basePointGGet() {
        return domainParams.getG();
    }
    public static BigInteger generateSharedSecret(BigInteger privateKey, ECPoint publicKey) {
        ECPoint sharedPoint = scalarMult(privateKey, publicKey);
        BigInteger sharedSecret = sharedPoint.getXCoord().toBigInteger();
        return sharedSecret;
    }
    public static ECPoint ecPointGen(BigInteger x, BigInteger y) {
        return curveParams.getCurve().createPoint(x, y);
    }

    public static boolean isOnCurveCheck(ECPoint point) {
        return point.isValid();
    }

    public static ECPoint addECPoints(ECPoint a, ECPoint b) {
        return a.add(b);
    }

    public static ECPoint doubleECPoints(ECPoint a) {
        return a.twice();
    }

    public static ECPoint scalarMult(BigInteger k, ECPoint a) {
        return a.multiply(k);
    }

    public static String ecPointToString(ECPoint point) {
        return Hex.toHexString(point.getEncoded(true));
    }

    public static ECPoint stringToECPoint(String s) {
        byte[] encodedPoint = Hex.decode(s);
        return curveParams.getCurve().decodePoint(encodedPoint);
    }

    public static void printECPoint(ECPoint point) {
        System.out.println("X: " + point.getXCoord().toBigInteger());
        System.out.println("Y: " + point.getYCoord().toBigInteger());
    }

    public static boolean isEqual(ECPoint a, ECPoint b) {
        return a.equals(b);
    }

    public static BigInteger setRandom(int numBits) {
        byte[] bytes = new byte[(numBits + 7) / 8];
        random.nextBytes(bytes);
        return new BigInteger(1, bytes);
    }

    public static void main(String[] args) {
        ECPoint G = basePointGGet();
        BigInteger k = setRandom(256);
        BigInteger d = setRandom(256);

        ECPoint H1 = scalarMult(d, G);
        ECPoint H2 = scalarMult(k, H1);

        ECPoint H3 = scalarMult(k, G);
        ECPoint H4 = scalarMult(d, H3);

        boolean result = isEqual(H2, H4);
        System.out.println("Result: " + result);
    }
}
