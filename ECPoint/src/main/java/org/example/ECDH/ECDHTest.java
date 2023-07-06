package org.example.ECDH;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ECDHTest {
    public static void main(String[] args) {
        // Create the curve parameters
        ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1");
        ECDomainParameters domainParams = new ECDomainParameters(ecSpec.getCurve(), ecSpec.getG(), ecSpec.getN());

        // Generate the key pairs for participant 1 and participant 2
        ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();
        ECKeyGenerationParameters keyGenerationParameters = new ECKeyGenerationParameters(domainParams, new SecureRandom());
        keyPairGenerator.init(keyGenerationParameters);

        AsymmetricCipherKeyPair keyPair1 = keyPairGenerator.generateKeyPair();
        AsymmetricCipherKeyPair keyPair2 = keyPairGenerator.generateKeyPair();

        // Extract the public and private keys for participant 1 and participant 2
        ECPrivateKeyParameters privateKey1 = (ECPrivateKeyParameters) keyPair1.getPrivate();
        ECPublicKeyParameters publicKey1 = (ECPublicKeyParameters) keyPair1.getPublic();

        ECPrivateKeyParameters privateKey2 = (ECPrivateKeyParameters) keyPair2.getPrivate();
        ECPublicKeyParameters publicKey2 = (ECPublicKeyParameters) keyPair2.getPublic();

        // Calculate the shared secrets
        ECDHBasicAgreement agreement1 = new ECDHBasicAgreement();
        agreement1.init(privateKey1);
        BigInteger sharedSecret1 = agreement1.calculateAgreement(publicKey2);

        ECDHBasicAgreement agreement2 = new ECDHBasicAgreement();
        agreement2.init(privateKey2);
        BigInteger sharedSecret2 = agreement2.calculateAgreement(publicKey1);

        // Convert shared secrets to hexadecimal strings
        String sharedSecretHex1 = sharedSecret1.toString(16);
        String sharedSecretHex2 = sharedSecret2.toString(16);

        // Compare the shared secrets
        boolean secretsMatch = sharedSecretHex1.equals(sharedSecretHex2);

        // Print the results
        System.out.println("Shared secret of participant 1 (hex): " + sharedSecretHex1);
        System.out.println("Shared secret of participant 2 (hex): " + sharedSecretHex2);
        System.out.println("Shared secrets match: " + secretsMatch);
    }
}
