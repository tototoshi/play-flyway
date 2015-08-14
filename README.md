# play-flyway

![travis](https://api.travis-ci.org/tototoshi/play-flyway.png)

Flyway plugin for Play 2.1 - 2.3. It aims to be a substitute for play-evolutions.

__For Play 2.4, We will develop Play Module at [flyway/flyway-play](https://github.com/flyway/flyway-play)__

## Features

 - Based on [Flyway](http://flywaydb.org/)
 - No 'Downs' part.
 - Independent of DBPlugin(play.api.db).

## Install

### For Play 2.3.x
In Build.scala/build.sbt

```scala
libraryDependencies += "com.github.tototoshi" %% "play-flyway" % "1.2.2"
```

and write play.plugins.

```
1000:com.github.tototoshi.play2.flyway.Plugin
```

### For Play 2.2.x
In Build.scala/build.sbt

```scala
libraryDependencies += "com.github.tototoshi" %% "play-flyway" % "1.2.2-2.2.x"
```

and write play.plugins.

```
1000:com.github.tototoshi.play2.flyway.Plugin
```

### For Play 2.1.x
In Build.scala/build.sbt

```scala
libraryDependencies += "com.github.tototoshi" %% "play-flyway" % "0.2.0"
```

and write play.plugins.

```
1000:com.github.tototoshi.play2.flyway.Plugin
```

## Document

Please see [Document site](http://tototoshi.github.io/play-flyway/)
