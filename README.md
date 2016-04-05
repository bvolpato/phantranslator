phantranslator
========
:globe_with_meridians: Simple PhantomJS Google Translator Implementation (Selenium based)

[![Apache License](http://img.shields.io/badge/license-ASL-blue.svg)](https://github.com/brunocvcunha/phantranslator/blob/master/LICENSE)
[![Build Status](https://travis-ci.org/brunocvcunha/phantranslator.svg)](https://travis-ci.org/brunocvcunha/phantranslator)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.brunocvcunha.phantranslator/phantranslator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.brunocvcunha.phantranslator/phantranslator)

Requires [PhantomJS](http://phantomjs.org) installed and exposed in `PATH` variable.

Download
--------

Download [the latest JAR][1] or grab via Maven:
```xml
<dependency>
  <groupId>org.brunocvcunha.phantranslator</groupId>
  <artifactId>phantranslator</artifactId>
  <version>1.0</version>
</dependency>
```
or Gradle:
```groovy
compile 'org.brunocvcunha.phantranslator:phantranslator:1.0'
```

Snapshots of the development version are available in [Sonatype's `snapshots` repository][snap].

phantranslator requires at minimum Java 6.


Usage Example
--------

```java
PhantranslatorExecutor executor = new PhantranslatorExecutor("en", "pt");
String translated = executor.getTranslation("useful library"); // returns 'biblioteca útil'
```


 [1]: https://search.maven.org/remote_content?g=org.brunocvcunha.phantranslator&a=phantranslator&v=LATEST
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
