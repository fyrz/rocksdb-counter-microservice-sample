package org.rocksdb.samples.counter;

import static spark.Spark.*;

/**
 * Micro-service providing named counter REST interfaces. Counters
 * are backed by an embedded K/V store called RocksDB.
 */
public class RocksDBCounterServiceSample {

  // RocksDBSimpleClient providing counter functionality
  private static final RocksDBFactory.RocksDBSimpleClient simpleClient =
      RocksDBFactory.rocksDBInstance("/tmp/simpleCounter");

  /**
   * Run Micro-Service.
   *
   * @param args command-line parameters.
   */
  public static void main(String[] args) {
    // REST interface to increase named counter by one
    get("/incrementCounter/:counterName", (request, response) -> {
      final String counterName = request.params(":counterName");
      simpleClient.incrementCounter(counterName.getBytes());
      return "";
    });

    // REST interface to retrieve numeric value of named counter
    get("/getCounter/:counterName", (request, response) -> {
      final String counterName = request.params(":counterName");
      long value = simpleClient.retrieveCounter(counterName.getBytes());

      return String.format("%d%n", value);
    });

    // REST interface to reset named counter to zero
    get("/resetCounter/:counterName", (request, response) -> {
      final String counterName = request.params(":counterName");
      simpleClient.resetCounter(counterName.getBytes());
      return "";
    });
  }
}
