package com.ft.uuidutils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import com.ft.uuidutils.DeriveUuid.Salts;
import java.util.UUID;
import org.junit.Test;

public class DeriveUuidTest {

  @Test
  public void operationIsRepeatable() throws Exception {
    final UUID initialUuid = UUID.randomUUID();

    final UUID firstResult = DeriveUuid.with(Salts.IMAGE_SET).from(initialUuid);
    assertThat(firstResult, is(not(initialUuid)));

    final UUID secondResult = DeriveUuid.with(Salts.IMAGE_SET).from(initialUuid);
    assertThat(secondResult, is(not(initialUuid)));

    assertThat(firstResult, is(secondResult));
    assertThat(firstResult, is(not(sameInstance(secondResult))));
  }

  @Test
  public void operationIsReversible() throws Exception {
    final UUID initialUuid = UUID.randomUUID();

    final UUID generatedUuid = DeriveUuid.with(Salts.IMAGE_SET).from(initialUuid);
    assertThat(generatedUuid, is(not(initialUuid)));

    final UUID originalUuid = DeriveUuid.with(Salts.IMAGE_SET).revert(generatedUuid);
    assertThat(originalUuid, is(initialUuid));
  }

  @Test
  public void sameSaltSameResult() throws Exception {

    final UUID initialUuid = UUID.randomUUID();

    final UUID firstResult = DeriveUuid.with(Salts.IMAGE_SET).from(initialUuid);
    assertThat(firstResult, is(not(initialUuid)));

    final UUID secondResult = DeriveUuid.with("imageset").from(initialUuid);
    assertThat(secondResult, is(not(initialUuid)));

    assertThat(firstResult, is(secondResult));
    assertThat(firstResult, is(not(sameInstance(secondResult))));
  }

  @Test
  public void differentSaltDifferentResult() throws Exception {

    final UUID initialUuid = UUID.randomUUID();

    final UUID firstResult = DeriveUuid.with(Salts.IMAGE_SET).from(initialUuid);
    assertThat(firstResult, is(not(initialUuid)));

    final UUID secondResult = DeriveUuid.with("not imageset").from(initialUuid);
    assertThat(secondResult, is(not(initialUuid)));

    assertThat(firstResult, is(not(secondResult)));
  }

}