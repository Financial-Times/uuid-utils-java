[![CircleCI](https://circleci.com/gh/Financial-Times/uuid-utils-java.svg?style=svg)](https://circleci.com/gh/Financial-Times/uuid-utils-java) [![Coverage Status](https://coveralls.io/repos/github/Financial-Times/uuid-utils-java/badge.svg?branch=master)](https://coveralls.io/github/Financial-Times/uuid-utils-java?branch=master)

# uuid-utils-java
A java project with commonly used UUID operations:

## Validation
Validation of UUID strings is performed by using the the `UuidValidation` class. 
This class has a `public static` method named `of` that will throw an `IllegalArgumentException` in 
case the given UUID string is not valid.

Example usage:

```
try {
  UuidValidation.of(uuidString);
} catch (final IllegalArgumentException e) {
  //do something because the uuid is invalid
}
```

## Generation
The generation of a UUID is performed by the `GenerateUuid` class. The generation can be done 
starting from a string or a URL. The operation is guaranteed to return the same UUID for the same 
given input. Two `public static` methods are available, both called `from` which 
will take either a string or a URL as a parameter and will return the generated UUID.

Example usages:

```
final UUID firstUuid = GenerateUuid.from(someString);
final UUID secondUuid = GenerateUuid.from(someString);
final UUID thirdUuid = GenerateUuid.from(otherString);

if (firstUuid.equals(secondUuid) {
  //this is always true
}

if (firstUuid.equals(thirdUuid)) {
} else {
  //this should be always false
}

```

```
final UUID generatedUuid = GenerateUuid.from(someURL);
final UUID secondUuid = GenerateUuid.from(someUrl);
final UUID thirdUuid = GenerateUuid.from(otherUrl);

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
`DeriveUuid`. This derivation process uses a "salt". Some frequently used `Salts` are available 
as enums. In case these do not suffice, any string can be used.
 
**The operation is reversible, meaning that if the derived UUID is derived once again using the same 
salt, the original UUID will be obtained.**

To obtain an instance of the `DeriveUuid`, you must use the one of the two available `public static`
methods called `with` and provide a salt, from either the `Salts` enum, or as a string. On the
instance you can then call the `from` method which will derive a given UUID.

Example usages:

```
final UUID derivedUuid = DeriveUuid.with(Salts.IMAGE_SET).from(originalUuid);
final UUID doubleDerivedUuid = DeriveUuid.with(Salts.IMAGE_SET).from(derivedUuid);
if (originalUuid.equals(doubleDerivedUuid)) {
  //this is always true
}
```

```
final UUID derivedUuid = DeriveUuid.with("salt").from(originalUuid);
final UUID doubleDerivedUuid = DeriveUuid.with("salt").from(derivedUuid);
if (originalUuid.equals(doubleDerivedUuid)) {
  //this is always true
}
```

```
final UUID derivedUuid = DeriveUuid.with("salt_1").from(originalUuid);
final UUID doubleDerivedUuid = DeriveUuid.with("salt_2").from(derivedUuid);
if (originalUuid.equals(doubleDerivedUuid)) {
} else {
  //this is always false
}
```
