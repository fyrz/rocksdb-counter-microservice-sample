package org.rocksdb.samples.counter;

import java.nio.file.Files;
import java.nio.file.Paths;

import static spark.Spark.*;

/**
 * Microservice providing named counter REST interfaces. Counters
 * are backed by an embedded K/V store called RocksDB.
 */
final class RocksDBCounterServiceSample {

  // RocksDBSimpleClient providing counter functionality
  static RocksDBFactory.RocksDBSimpleClient simpleClient;

  /**
   * Run Microservice.
   *
   * @param args command-line parameters.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException("The application takes one argument which identifies the RocksDB storage location.");
    }

    // setup database connection
    simpleClient = RocksDBFactory.rocksDBInstance(args[0]);

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

  private RocksDBCounterServiceSample() {

  }
}
