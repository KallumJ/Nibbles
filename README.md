
# Nibbles [![Build Status](https://jenkins.bits.team/job/Bits/job/Nibbles/job/master/badge/icon)](https://jenkins.bits.team/job/Bits/job/Nibbles/job/master/) ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white) 

Library full of utility functions and classes for server-side Fabric mods

## Features

- Command framework for adding custom commands simply
- Event system with custom events, allowing for event driven functionality
- Scheduler for scheduling code to run in relation to ticks.
- Messaging functions for broadcasting and displaying titles to players.
- Teleporting system for safe and clean player teleporting between locations
- More!


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
