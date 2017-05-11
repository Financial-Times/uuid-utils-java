package com.ft.uuidutils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

public class GenerateUuidTest {


  private URL testUrl;

  @Before
  public void setUp() throws Exception {
    testUrl = new URL("http://test-api.ft.com/content/image_model_id");
  }

  @Test
  public void operationIsRepeatable() throws Exception {
    final UUID firstUuid = GenerateUuid.from(testUrl);
    final UUID secondUuid = GenerateUuid.from(testUrl);

    assertThat(firstUuid, is(secondUuid));
    assertThat(firstUuid, is(not(sameInstance(secondUuid))));
  }

}