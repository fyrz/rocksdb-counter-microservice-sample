package org.rocksdb.samples.counter;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

/**
 * Factory to provide {@link RocksDBSimpleClient} instances.
 */
public final class RocksDBFactory {

  /**
   * Simple RocksDBClient to provide atomic increment, reset and get
   * methods.
   *
   */
  static class RocksDBSimpleClient {
    RocksDB db;
    final Options options;

    private static final byte[] byteArrayOne = ByteConversionHelper.longToByte(1);

    /**
     * Initialize RocksDBSimpleClient
     *
     * @param path Path to RocksDB.
     */
    RocksDBSimpleClient(final String path) {
      try {
        options = new Options()
            .setCreateIfMissing(true)
            .setMergeOperatorName("uint64add")
            .setWriteBufferSize(100);
        db = RocksDB.open(options, path);
      } catch (RocksDBException e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * Increment counter with name {@code key} by one.
     *
     * @param key name of counter.
     */
    public void incrementCounter(final byte[] key) {
      try {
        // Perform atomic merge (read/modify/write) operation.
        db.merge(key, byteArrayOne);
      } catch (RocksDBException e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * Retrieve long value of counter with name {@code key}.
     *
     * @param key name of counter.
     *
     * @return current value of counter with name {@code key}.
     */
    public long retrieveCounter(final byte[] key) {
      try {
        return ByteConversionHelper.byteToLong(db.get(key));
      } catch (RocksDBException e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * Reset counter with name {@code key}
     *
     * @param key name of counter.
     */
    public void resetCounter(final byte[] key) {
      try {
        db.remove(key);
      } catch (RocksDBException e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Retrieve a {@link RocksDBFactory.RocksDBSimpleClient}
   * instance.
   *
   * @param path path to RocksDB.
   *
   * @return {@link RocksDBFactory.RocksDBSimpleClient} instance.
   */
  public static RocksDBSimpleClient rocksDBInstance(final String path) {
    return new RocksDBSimpleClient(path);
  }

  private RocksDBFactory() {

  }
}
