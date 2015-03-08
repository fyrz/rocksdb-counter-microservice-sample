package org.rocksdb.samples.counter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import static org.assertj.core.api.Assertions.assertThat;

public class RocksDBFactoryTest {
  @Rule
  public TemporaryFolder dbFolder = new TemporaryFolder();

  public static final byte[] TEST_KEY = "TEST_KEY".getBytes();


  @Test
  public void rocksDbClient() {
    RocksDBFactory.RocksDBSimpleClient simpleClient =
        RocksDBFactory.rocksDBInstance(dbFolder.getRoot().getAbsolutePath());
    assertThat(simpleClient).isNotNull();

    assertThat(simpleClient.retrieveCounter(TEST_KEY))
        .isEqualTo(0);
    simpleClient.resetCounter(TEST_KEY);
    assertThat(simpleClient.retrieveCounter(TEST_KEY))
        .isEqualTo(0);
    simpleClient.incrementCounter(TEST_KEY);
    assertThat(simpleClient.retrieveCounter(TEST_KEY))
        .isEqualTo(1);
    simpleClient.db.close();
  }

  @Test(expected = RuntimeException.class)
  public void runtimeExceptionIfRocksErrorOnOpen() {
    RocksDBFactory.RocksDBSimpleClient simpleClient =
        RocksDBFactory.rocksDBInstance("/_$$%&/((§§§§%&$&§$");
  }

  @Test(expected = RuntimeException.class)
  public void runtimeExceptionIfRocksErrorOnIncrement() {
    RocksDBFactory.RocksDBSimpleClient simpleClient =
        RocksDBFactory.rocksDBInstance(dbFolder.getRoot().getAbsolutePath());
    simpleClient.db.close();
    simpleClient.db = new MockRocksDB();
    simpleClient.incrementCounter(TEST_KEY);
  }

  @Test(expected = RuntimeException.class)
  public void runtimeExceptionIfRocksErrorOnReset() {
    RocksDBFactory.RocksDBSimpleClient simpleClient =
        RocksDBFactory.rocksDBInstance(dbFolder.getRoot().getAbsolutePath());
    simpleClient.db.close();
    simpleClient.db = new MockRocksDB();
    simpleClient.resetCounter(TEST_KEY);
  }

  @Test(expected = RuntimeException.class)
  public void runtimeExceptionIfRocksErrorOnRetrieve() {
    RocksDBFactory.RocksDBSimpleClient simpleClient =
        RocksDBFactory.rocksDBInstance(dbFolder.getRoot().getAbsolutePath());
    simpleClient.db.close();
    simpleClient.db = new MockRocksDB();
    simpleClient.retrieveCounter(TEST_KEY);
  }

  /**
   * Mock to test error behavior
   */
  class MockRocksDB extends RocksDB {
    @Override
    public void merge(byte[] key, byte[] value) throws RocksDBException {
      throw new RocksDBException("X");
    }

    @Override
    public byte[] get(byte[] key) throws RocksDBException {
      throw new RocksDBException("X");
    }

    @Override
    public void remove(byte[] key) throws RocksDBException {
      throw new RocksDBException("X");
    }
  }
}
