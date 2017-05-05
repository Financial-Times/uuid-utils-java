package com.ft.uuidutils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import com.ft.uuidutils.UuidFromUuidGenerator.Salts;
import java.util.UUID;
import org.junit.Test;

public class UuidFromUuidGeneratorTest {

  @Test
  public void operationIsRepeatable() throws Exception {
    final UuidFromUuidGenerator generator = UuidFromUuidGenerator.with(Salts.IMAGE_SET);
    final UUID initialUuid = UUID.randomUUID();

    final UUID firstResult = generator.generate(initialUuid);
    assertThat(firstResult, is(not(initialUuid)));

    final UUID secondResult = generator.generate(initialUuid);
    assertThat(secondResult, is(not(initialUuid)));

    assertThat(firstResult, is(secondResult));
    assertThat(firstResult, is(not(sameInstance(secondResult))));
  }

  @Test
  public void operationIsReversible() throws Exception {
    final UuidFromUuidGenerator generator = UuidFromUuidGenerator.with(Salts.IMAGE_SET);
    final UUID initialUuid = UUID.randomUUID();

    final UUID generatedUuid = generator.generate(initialUuid);
    assertThat(generatedUuid, is(not(initialUuid)));

    final UUID originalUuid = generator.revert(generatedUuid);
    assertThat(originalUuid, is(initialUuid));
  }

  @Test
  public void sameSaltSameResult() throws Exception {
    final UuidFromUuidGenerator firstGenerator = UuidFromUuidGenerator.with(Salts.IMAGE_SET);
    final UuidFromUuidGenerator secondGenerator = UuidFromUuidGenerator.with("imageset");

    final UUID initialUuid = UUID.randomUUID();

    final UUID firstResult = firstGenerator.generate(initialUuid);
    assertThat(firstResult, is(not(initialUuid)));

    final UUID secondResult = secondGenerator.generate(initialUuid);
    assertThat(secondResult, is(not(initialUuid)));

    assertThat(firstResult, is(secondResult));
    assertThat(firstResult, is(not(sameInstance(secondResult))));
  }

  @Test
  public void differentSaltDifferentResult() throws Exception {
    final UuidFromUuidGenerator firstGenerator = UuidFromUuidGenerator.with(Salts.IMAGE_SET);
    final UuidFromUuidGenerator secondGenerator = UuidFromUuidGenerator.with("not imageset");

    final UUID initialUuid = UUID.randomUUID();

    final UUID firstResult = firstGenerator.generate(initialUuid);
    assertThat(firstResult, is(not(initialUuid)));

    final UUID secondResult = secondGenerator.generate(initialUuid);
    assertThat(secondResult, is(not(initialUuid)));

    assertThat(firstResult, is(not(secondResult)));
  }

}