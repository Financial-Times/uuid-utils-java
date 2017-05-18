package com.ft.uuidutils;


import org.junit.Test;

public class UUIDValidationTest {

  @Test
  public void validUUIDLowercase() throws Exception {
    UUIDValidation.of("b015eee6-fab4-4307-bd5f-f32081fcc4cb");
  }

  @Test
  public void validUUIDUppercase() throws Exception {
    UUIDValidation.of("B015EEE6-FAB4-4307-BD5F-F32081FCC4CB");
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullUUID() throws Exception {
    UUIDValidation.of(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void missingGroup() throws Exception {
    UUIDValidation.of("b015eee6-4307-bd5f-f32081fcc4cb");
  }

  @Test(expected = IllegalArgumentException.class)
  public void wrongGroupLengths() throws Exception {
    UUIDValidation.of("b015eee6-4307-f32081fcc4cb-bd5f");
  }

  @Test(expected = IllegalArgumentException.class)
  public void missingChar() throws Exception {
    UUIDValidation.of("b015eee6-fab4-4307-bd5f-f32081fcc4c");
  }

  @Test(expected = IllegalArgumentException.class)
  public void extraChar() throws Exception {
    UUIDValidation.of("b015eee6-fab4-4307-bd5f-f32081fcc4cba");
  }

  @Test(expected = IllegalArgumentException.class)
  public void illegalChar() throws Exception {
    UUIDValidation.of("b015eee6-fab4-4307-bd5f-f32081fcc4cx");
  }

}