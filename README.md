# Nibbles [![Build Status](https://jenkins.bits.team/buildStatus/icon?job=Bits%2FNibbles%2Fmaster)](https://jenkins.bits.team/job/Bits/job/Nibbles/job/master/)

Library full of utility functions and classes for server-side Fabric mods

## Usage

#### Gradle:

Add the Bits Maven repository:

```groovy
maven {
    name = "bits-maven-repo"
    url = "https://hogwarts.bits.team/nexus/repository/bits-maven-repo/"
    metadataSources {
        mavenPom()
        artifact()
    }
}
```

Add the Nibbles dependency

```groovy
modImplementation include("team.bits:nibbles:VERSION")
```

#### Maven:

Add the Bits Maven repository

```xml
<repository>
    <id>bits-maven-repo</id>
    <url>https://hogwarts.bits.team/nexus/repository/bits-maven-repo/</url>
</repository>
```

Add the Nibbles dependency

```xml
<dependency>
    <groupId>team.bits</groupId>
    <artifactId>nibbles</artifactId>
    <version>VERSION</version>
</dependency>
```
