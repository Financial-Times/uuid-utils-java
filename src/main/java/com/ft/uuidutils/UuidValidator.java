package com.ft.uuidutils;

import java.util.UUID;

public class UuidValidator {

  public static void validate(final String uuid) {
    if (uuid == null) {
      throw new IllegalArgumentException("Supplied UUID is null");
    }

    if (!UUID.fromString(uuid).toString().equalsIgnoreCase(uuid)) {
      throw new IllegalArgumentException("Supplied UUID: " + uuid + ", does not conform to RFC 4122");
    }
  }

}