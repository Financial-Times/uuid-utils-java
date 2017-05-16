package com.ft.uuidutils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

public class GenerateV5UUIDTest {

  private URL testUrl;

  @Before
  public void setUp() throws Exception {
    testUrl = new URL("http://test-api.ft.com/content/image_model_id");
  }

  @Test
  public void testFromUrlIsRepeatable() throws Exception {
    final UUID firstUuid = GenerateV5UUID.fromURL(testUrl);
    final UUID secondUuid = GenerateV5UUID.fromURL(testUrl);

    assertThat(firstUuid, is(secondUuid));
    assertThat(firstUuid, is(not(sameInstance(secondUuid))));
  }

}