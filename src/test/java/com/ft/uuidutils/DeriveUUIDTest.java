package com.ft.uuidutils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import com.ft.uuidutils.DeriveUUID.Salts;
import java.util.UUID;
import org.junit.Test;

public class DeriveUUIDTest {

  @Test
  public void testFromIsRepeatable() throws Exception {
    final UUID initialUuid = UUID.randomUUID();

    final UUID firstResult = DeriveUUID.with(Salts.IMAGE_SET).from(initialUuid);
    assertThat(firstResult, is(not(initialUuid)));

    final UUID secondResult = DeriveUUID.with(Salts.IMAGE_SET).from(initialUuid);
    assertThat(secondResult, is(not(initialUuid)));

    assertThat(firstResult, is(secondResult));
    assertThat(firstResult, is(not(sameInstance(secondResult))));
  }

  @Test
  public void testFromIsReversible() throws Exception {
    final UUID initialUuid = UUID.randomUUID();

    final UUID generatedUuid = DeriveUUID.with(Salts.IMAGE_SET).from(initialUuid);
    assertThat(generatedUuid, is(not(initialUuid)));

    final UUID originalUuid = DeriveUUID.with(Salts.IMAGE_SET).from(generatedUuid);
    assertThat(originalUuid, is(initialUuid));
  }

  @Test
  public void testFromSameSaltSameResult() throws Exception {

    final UUID initialUuid = UUID.randomUUID();

    final UUID firstResult = DeriveUUID.with(Salts.IMAGE_SET).from(initialUuid);
    assertThat(firstResult, is(not(initialUuid)));

    final UUID secondResult = DeriveUUID.with("imageset").from(initialUuid);
    assertThat(secondResult, is(not(initialUuid)));

    assertThat(firstResult, is(secondResult));
    assertThat(firstResult, is(not(sameInstance(secondResult))));
  }

  @Test
  public void testFromDifferentSaltDifferentResult() throws Exception {

    final UUID initialUuid = UUID.randomUUID();

    final UUID firstResult = DeriveUUID.with(Salts.IMAGE_SET).from(initialUuid);
    assertThat(firstResult, is(not(initialUuid)));

    final UUID secondResult = DeriveUUID.with("not imageset").from(initialUuid);
    assertThat(secondResult, is(not(initialUuid)));

    assertThat(firstResult, is(not(secondResult)));
  }

}