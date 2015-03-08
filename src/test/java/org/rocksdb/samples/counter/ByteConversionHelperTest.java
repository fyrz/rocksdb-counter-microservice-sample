package org.rocksdb.samples.counter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ByteConversionHelperTest {

  @Test
  public void testRoundTrip() {
    long longValue = 5;
    assertThat(ByteConversionHelper.byteToLong(
        ByteConversionHelper.longToByte(longValue))).isEqualTo(longValue);
  }

  @Test
  public void testNullConversion() {
    assertThat(ByteConversionHelper.byteToLong(null)).isEqualTo(0);
  }
}
