package com.ft.uuidutils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;

import com.ft.uuidutils.UuidFromUrlGenerator.Namesapces;
import java.net.URL;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;

public class UuidFromUrlGeneratorTest {

  private URL testUrl;

  @Before
  public void setUp() throws Exception {
    testUrl = new URL("http://test-api.ft.com/contente/image_model_id");
  }

  @Test
  public void operationIsRepeatable() throws Exception {
    final UuidFromUrlGenerator generator = UuidFromUrlGenerator.with(Namesapces.IMAGE_MODEL);
    final URL testUrl = new URL("http://test-api.ft.com/contente/image_model_id");

    final UUID firstUuid = generator.generate(testUrl);
    final UUID secondUuid = generator.generate(testUrl);

    assertThat(firstUuid, is(secondUuid));
    assertThat(firstUuid, is(not(sameInstance(secondUuid))));
  }

  @Test
  public void sameNamespaceSameResult() throws Exception {
    final UuidFromUrlGenerator firstGenerator = UuidFromUrlGenerator.with(Namesapces.IMAGE_MODEL);
    final UuidFromUrlGenerator secondGenerator = UuidFromUrlGenerator.with("6ba7b811-9dad-11d1-80b4-00c04fd430c8");

    final UUID firstUuid = firstGenerator.generate(testUrl);
    final UUID secondUuid = secondGenerator.generate(testUrl);

    assertThat(firstUuid, is(secondUuid));
    assertThat(firstUuid, is(not(sameInstance(secondUuid))));
  }

  @Test
  public void differentNamespaceDifferentResult() throws Exception {
    final UuidFromUrlGenerator firstGenerator = UuidFromUrlGenerator.with(Namesapces.IMAGE_MODEL);
    final UuidFromUrlGenerator secondGenerator = UuidFromUrlGenerator.with("some-namespace");

    final UUID firstUuid = firstGenerator.generate(testUrl);
    final UUID secondUuid = secondGenerator.generate(testUrl);

    assertThat(firstUuid, is(not(secondUuid)));
  }

}