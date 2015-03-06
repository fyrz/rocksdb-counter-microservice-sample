package org.rocksdb.samples.counter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * ByteConversionHelper to help in transforming long values
 * to platform-specific byte arrays and back again.
 */
class ByteConversionHelper {

  /**
   * Transforms a long value to the platform-specific
   * byte representation.
   *
   * @param value numeric value.
   * @return platform-specific long value.
   */
  public static byte[] longToByte(long value) {
    ByteBuffer longBuffer = ByteBuffer.allocate(8)
        .order(ByteOrder.nativeOrder());
    longBuffer.clear();
    longBuffer.putLong(value);
    return longBuffer.array();
  }

  /**
   * Transforms platform specific byte representation
   * to long value.
   *
   * @param data platform-specific byte array.
   *
   * @return numeric value.
   */
  public static long byteToLong(byte[] data) {
    if (data != null) {
      ByteBuffer longBuffer = ByteBuffer.allocate(8)
          .order(ByteOrder.nativeOrder());
      longBuffer.put(data, 0, 8);
      longBuffer.flip();
      return longBuffer.getLong();
    }
    return 0;
  }

  /**
   * Utility constructor
   */
  private ByteConversionHelper() {
  }
}
