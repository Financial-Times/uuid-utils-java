package com.ft.uuidutils;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.UUID;

public class DeriveUuid {

  private final BitSet saltUuidLsb;

  private DeriveUuid(final BitSet saltUuidLsb) {
    this.saltUuidLsb = saltUuidLsb;
  }

  public UUID from(final UUID originalUuid) {
    return otherUuid(originalUuid);
  }

  public UUID revert(final UUID derivedUuid) {
    return otherUuid(derivedUuid);
  }

  private UUID otherUuid(UUID uuid) {
    final BitSet uuidBits = BitSet.valueOf(leastSignificantUuidPartToBytes(uuid));
    uuidBits.xor(saltUuidLsb);

    // pad to the end of the array in case the bitset has shrunk by 8 bits or more
    final byte[] xor = uuidBits.toByteArray();
    final byte[] padded = new byte[8];
    Arrays.fill(padded, (byte) 0);
    System.arraycopy(xor, 0, padded, 0, xor.length);

    return bytesToUuid(uuid, padded);
  }

  private UUID bytesToUuid(UUID uuid, byte[] bytes) {
    final ByteBuffer bb = ByteBuffer.wrap(bytes);
    final long least = bb.getLong();

    return new UUID(uuid.getMostSignificantBits(), least);
  }

  public static final DeriveUuid with(final Salts salt) {
    return new DeriveUuid(salt.getSaltUuidLsb());
  }

  public static final DeriveUuid with(final String salt) {
    return new DeriveUuid(saltToUuidLsb(salt));
  }

  public enum Salts {

    IMAGE_SET("imageset");

    private final BitSet saltUuidLsb;

    Salts(final String salt) {
      this.saltUuidLsb = saltToUuidLsb(salt);
    }

    public BitSet getSaltUuidLsb() {
      return saltUuidLsb;
    }

  }

  private static BitSet saltToUuidLsb(final String salt) {
    return BitSet.valueOf(
        leastSignificantUuidPartToBytes(
            UUID.nameUUIDFromBytes(salt.getBytes(UTF_8))
        )
    );
  }

  private static byte[] leastSignificantUuidPartToBytes(final UUID uuid) {
    final ByteBuffer bb = ByteBuffer.wrap(new byte[8]);
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }

}