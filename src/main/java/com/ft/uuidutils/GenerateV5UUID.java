package com.ft.uuidutils;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.google.common.primitives.Longs;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

public class GenerateV5UUID {

    /**
     * See http://www.ietf.org/rfc/rfc4122.txt - Appendix C
     */
    private static final byte[] NAMESPACE_URL_ID = "6ba7b811-9dad-11d1-80b4-00c04fd430c8".getBytes(UTF_8);

    private static final String DIGEST_ALG = "SHA-1";
    private static final byte UUID_TYPE = 5;

    public static UUID fromURL(final URL url) {
        byte[] localId = url.toExternalForm().getBytes(UTF_8);

        try {
            final MessageDigest md = MessageDigest.getInstance(DIGEST_ALG);
            md.update(NAMESPACE_URL_ID);
            md.update(localId);
            byte[] digest = md.digest();

            final byte[] hi = new byte[8];
            Arrays.fill(hi, (byte) 0);
            //  Set octets zero through 3 of the time_low field to octets zero through 3 of the hash.
            hi[2] = digest[0];
            hi[3] = digest[1];
            // Set octets zero and one of the time_mid field to octets 4 and 5 of the hash.
            hi[5] = digest[2];
            // Set octets zero and one of the time_hi_and_version field to octets 6 and 7 of the hash.
            hi[7] = digest[3];
            // Set the four most significant bits (bits 12 through 15) of the time_hi_and_version field to the appropriate 4-bit version number from Section 4.1.3.
            hi[6] |= (UUID_TYPE << 4);

            final byte[] lo = new byte[8];
            Arrays.fill(lo, (byte) 0);
            // Set the clock_seq_hi_and_reserved field to octet 8 of the hash.
            lo[0] = digest[8];
            // Set the two most significant bits (bits 6 and 7) of the clock_seq_hi_and_reserved to zero and one, respectively.
            lo[0] &= 0xbf;
            lo[0] |= 0x80;
            // Set the clock_seq_low field to octet 9 of the hash.
            lo[1] = digest[9];
            // Set octets zero through five of the node field to octets 10 through 15 of the hash.
            System.arraycopy(digest, 10, lo, 2, 6);

            return new UUID(Longs.fromByteArray(hi), Longs.fromByteArray(lo));
        } catch (final NoSuchAlgorithmException e) {
            throw new InternalError(String.format("Digest %s not available", DIGEST_ALG), e);
        }
    }

    private GenerateV5UUID() {
    }

}