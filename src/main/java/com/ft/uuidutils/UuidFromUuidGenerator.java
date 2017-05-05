package com.ft.uuidutils;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.UUID;

public class UuidFromUuidGenerator {

  private final BitSet magic;

  private UuidFromUuidGenerator(final BitSet magic) {
    this.magic = magic;
  }

  public UUID generate(final UUID originalUuid) {
    return otherUuid(originalUuid);
  }

  public UUID revert(final UUID generatedUuid) {
    return otherUuid(generatedUuid);
  }

  private UUID otherUuid(UUID uuid) {
    BitSet uuidBits = BitSet.valueOf(leastSignificantUuidPartToBytes(uuid));
    uuidBits.xor(magic);

    // pad to the end of the array in case the bitset has shrunk by 8 bits or more
    byte[] xor = uuidBits.toByteArray();
    byte[] padded = new byte[8];
    Arrays.fill(padded, (byte) 0);
    System.arraycopy(xor, 0, padded, 0, xor.length);

    return bytesToUuid(uuid, padded);
  }

  private UUID bytesToUuid(UUID uuid, byte[] bytes) {
    final ByteBuffer bb = ByteBuffer.wrap(bytes);
    final long least = bb.getLong();

    return new UUID(uuid.getMostSignificantBits(), least);
  }

  public static final UuidFromUuidGenerator with(final Salts salt) {
    return new UuidFromUuidGenerator(salt.getMagic());
  }

  public static final UuidFromUuidGenerator with(final String salt) {
    return new UuidFromUuidGenerator(saltToMagic(salt));
  }

  public enum Salts {

    IMAGE_SET("imageset");

    private final BitSet magic;

    Salts(final String salt) {
      this.magic = saltToMagic(salt);
    }

    public BitSet getMagic() {
      return magic;
    }

  }

  private static BitSet saltToMagic(final String salt) {
    return BitSet.valueOf(
        leastSignificantUuidPartToBytes(
            UUID.nameUUIDFromBytes(salt.getBytes(UTF_8))
        )
    );
  }

  private static byte[] leastSignificantUuidPartToBytes(UUID uuid) {
    final ByteBuffer bb = ByteBuffer.wrap(new byte[8]);
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }

}