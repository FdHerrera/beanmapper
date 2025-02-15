[![Build Status](https://github.com/42BV/beanmapper/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/42BV/beanmapper/actions?query=workflow%3A%22Java+CI+with+Maven%22)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/663f3d7da8b849bb978c8860d1a0145d)](https://www.codacy.com/gh/42BV/beanmapper/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=42BV/beanmapper&amp;utm_campaign=Badge_Grade)
[![BCH compliance](https://bettercodehub.com/edge/badge/42BV/beanmapper?branch=master)](https://bettercodehub.com/)
[![codecov](https://codecov.io/gh/42BV/beanmapper/branch/master/graph/badge.svg)](https://codecov.io/gh/42BV/beanmapper)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.beanmapper/beanmapper/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.beanmapper/beanmapper)
[![Javadocs](https://www.javadoc.io/badge/io.beanmapper/beanmapper.svg)](https://www.javadoc.io/doc/io.beanmapper/beanmapper)
[![Apache 2](http://img.shields.io/badge/license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

# Beanmapper

Beanmapper is a Java library for mapping dissimilar Java classes with similar names. The use
cases for Beanmapper are the following:
* mapping from forms to entities, because:
  * for security reasons you want to accept only a limited number of fields as input
  * the form fields are simplified to support frontend processing
* mapping from entities to results, because:
  * you want to simplify the result for frontend processing
  * you want to expose a limited number of fields for security reasons

## Maven dependency

In order to use Beanmapper in your project, simply add the following Maven dependency:

```xml
<dependency>
    <groupId>io.beanmapper</groupId>
    <artifactId>beanmapper</artifactId>
    <version>4.1.0</version>
</dependency>
```

## Getting started

You want to map two dissimilar classes with no hierarchical relation (save java.lang.Object), 
but with a fairly similar naming schema for the fields.

![Basic use case for Beanmapper](docs/images/beanmapper-usecase.png)

```java
public class SourceClass {
   public Long id;
   public String name;
   public LocalDate date;
}
```

```java
public class TargetClass {
   public String name;
   public LocalDate date;
}
```

```java
BeanMapper beanMapper = new BeanMapper();
SourceClass source = new SourceClass();
source.id = 42L;
source.name = "Henk";
source.date = LocalDate.of(2015, 4, 1));
TargetClass target = beanMapper.map(source, TargetClass.class);
```

## What's more?

The library can help you with the following situations:
* nested dissimilar classes
* ignoring parts
* mapping to fields with other names, even if nested
* settings defaults if no value is found
* unwrapping class layers in order to flatten the structure
* works directly on the bean, no getters/setters required
* supports a combination of automated and manual processing
* adding conversion modules for data types

## What Beanmapper is not for

Beanmapper is *not* a library for deep-copying classes. Whenever Beanmapper can get away with a shallow
copy, it will do so. Deep-copying is reserved for dissimilar classes.

## Want to know more?

Find the rest of the documentation on [beanmapper.io](http://beanmapper.io).

## License

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
