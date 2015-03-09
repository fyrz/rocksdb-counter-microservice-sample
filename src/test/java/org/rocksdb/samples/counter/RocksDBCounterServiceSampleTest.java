package org.rocksdb.samples.counter;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class RocksDBCounterServiceSampleTest {

  @Rule
  public TemporaryFolder dbFolder = new TemporaryFolder();

  @Before
  public void setup() {
    RocksDBCounterServiceSample.main(new String[]{ dbFolder.getRoot().getAbsolutePath() });
  }

  @Test
  public void testService() throws IOException {
    // reset the counter to zero
    assertThat(testRestInterface("resetCounter/testCounter", 200))
        .isEqualTo("");
    // counter must be zero
    assertThat(testRestInterface("getCounter/testCounter", 200))
        .isEqualTo(String.format("%d%n", 0));
    // increment counter by 1
    assertThat(testRestInterface("incrementCounter/testCounter", 200))
        .isEqualTo("");
    // retrieved value must be 1
    assertThat(testRestInterface("getCounter/testCounter", 200))
        .isEqualTo(String.format("%d%n", 1));
    // other counter must be zero
    assertThat(testRestInterface("getCounter/anotherCounter", 200))
        .isEqualTo(String.format("%d%n",0));
  }

  @Test
  public void invalidPath() throws IOException {
    testRestInterface("nullifyCounter/testCounter", 404);
  }

  @Test(expected = IllegalArgumentException.class)
  public void insufficientParamsPassedToMain() {
    RocksDBCounterServiceSample.main(new String[0]);
  }

  /**
   * Method to test REST interfaces using http client.
   *
   * @param path as relative path to localhost.
   * @param expectedStatus expected response status.
   * @return response body as String.
   * @throws IOException thrown if http client request fails.
   */
  String testRestInterface(final String path, final int expectedStatus) throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet("http://localhost:4567/"+path);
    String responseValue;
    try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
      assertThat(response.getStatusLine().getStatusCode()).isEqualTo(expectedStatus);
      HttpEntity entity = response.getEntity();
      // do something useful with the response body
      // and ensure it is fully consumed
      responseValue = EntityUtils.toString(entity);
    }
    return responseValue;
  }
}
