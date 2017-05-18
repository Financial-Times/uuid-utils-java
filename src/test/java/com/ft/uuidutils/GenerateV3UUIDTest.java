package com.ft.uuidutils;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class GenerateV3UUIDTest {

  private String randomText;

  @Before
  public void setUp() throws Exception {
    randomText = "some random text";
  }

  @Test
  public void testDoubleDigestUUIDIsRepeatable() throws Exception {
    final UUID firstUuid = GenerateV3UUID.doubleDigested(randomText);
    final UUID secondUuid = GenerateV3UUID.doubleDigested(randomText);

    assertThat(firstUuid, is(secondUuid));
    assertThat(firstUuid, is(not(sameInstance(secondUuid))));
  }

  @Test
  public void testSingleDigestUUIDIsRepeatable() throws Exception {
    final UUID firstUuid = GenerateV3UUID.singleDigested(randomText);
    final UUID secondUuid = GenerateV3UUID.singleDigested(randomText);

    assertThat(firstUuid, is(secondUuid));
    assertThat(firstUuid, is(not(sameInstance(secondUuid))));
  }
}