[![CircleCI](https://circleci.com/gh/Financial-Times/uuid-utils-java.svg?style=svg)](https://circleci.com/gh/Financial-Times/uuid-utils-java) [![Coverage Status](https://coveralls.io/repos/github/Financial-Times/uuid-utils-java/badge.svg?branch=master)](https://coveralls.io/github/Financial-Times/uuid-utils-java?branch=master)

# uuid-utils-java
A java project with commonly used UUID operations:

## Validation
Validation of UUID strings is performed by using the `UUIDValidation` class. 
This class has a `public static` method named `of` that will throw an `IllegalArgumentException` in 
case the given UUID string is not valid.

Example usage:

```
try {
  UUIDValidation.of(uuidString);
} catch (final IllegalArgumentException e) {
  //do something because the uuid is invalid
}
```

## Generation
The generation of a UUID is performed by the `GenerateV3UUID` and `GenerateV5UUID` classes. 
The V3 generation is from a string and the V5 generation is from an URL. The operations are guaranteed to return the same UUID for the same 
given input. 

The V3 generation offers two `public static` methods `singleDigested` and `doubleDigested` which 
will take a string as a parameter and return a V3 UUID based on it.
The first one will apply the digest only one time, and the second will apply it 2 times consecutively in order to generate the UUID.

The V5 generation offers one `public static` method which is `fromURL` that takes an URL and returns a V5 UUID based on it.

#### Example usages:

##### UUID version 3 generation:
Single (normal) digested version:
```
final UUID firstUuid = GenerateV3UUID.singleDigested(someString);
final UUID secondUuid = GenerateV3UUID.singleDigested(someString);
final UUID thirdUuid = GenerateV3UUID.singleDigested(otherString);

if (firstUuid.equals(secondUuid) {
  //this is always true
}

if (firstUuid.equals(thirdUuid)) {
} else {
  //this should be always false
}

```

The above applies also for the double digested version:
```
final UUID firstUuid = GenerateV3UUID.doubleDigested(someString);
...
```

##### UUID version 5 generation:
```
final UUID generatedUuid = GenerateV5UUID.fromURL(someURL);
final UUID secondUuid = GenerateV5UUID.fromURL(someUrl);
final UUID thirdUuid = GenerateV5UUID.fromURL(otherUrl);

if (firstUuid.equals(secondUuid) {
  //this is always true
}

if (firstUuid.equals(thirdUuid)) {
} else {
  //this should be always false
}
```

## Derivation
This operation consists of deriving a UUID from another UUID and is performed by the class 
`DeriveUUID`. This derivation process uses a "salt". Some frequently used `Salts` are available 
as enums. In case these do not suffice, any string can be used.
 
**The operation is reversible, meaning that if the derived UUID is derived once again using the same 
salt, the original UUID will be obtained.**

To obtain an instance of the `DeriveUUID`, you must use the one of the two available `public static`
methods called `with` and provide a salt, from either the `Salts` enum, or as a string. On the
instance you can then call the `from` method which will derive a given UUID.

#### Example usages:

```
final UUID derivedUUID = DeriveUUID.with(Salts.IMAGE_SET).from(originalUuid);
final UUID doubleDerivedUUID = DeriveUUID.with(Salts.IMAGE_SET).from(derivedUuid);
if (originalUuid.equals(doubleDerivedUuid)) {
  //this is always true
}
```

```
final UUID derivedUuid = DeriveUUID.with("salt").from(originalUuid);
final UUID doubleDerivedUuid = DeriveUUID.with("salt").from(derivedUuid);
if (originalUuid.equals(doubleDerivedUuid)) {
  //this is always true
}
```

```
final UUID derivedUuid = DeriveUUID.with("salt_1").from(originalUuid);
final UUID doubleDerivedUuid = DeriveUUID.with("salt_2").from(derivedUuid);
if (originalUuid.equals(doubleDerivedUuid)) {
} else {
  //this is always false
}
```
