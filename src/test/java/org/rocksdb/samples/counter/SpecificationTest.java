package org.rocksdb.samples.counter;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class SpecificationTest {

  @Test
  public void byteConversionHelperIsAUtilityClass() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    SpecificationTestUtil.assertUtilityClassWellDefined(ByteConversionHelper.class);
  }

  @Test
  public void rocksDbCounterServiceIsAUtilityClass() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    SpecificationTestUtil.assertUtilityClassWellDefined(RocksDBCounterServiceSample.class);
  }

  @Test
  public void rocksDbFactoryIsAUtilityClass() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    SpecificationTestUtil.assertUtilityClassWellDefined(RocksDBFactory.class);
  }
}
