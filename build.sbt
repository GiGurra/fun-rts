val ext_libgdxVersion = "1.9.3"

val funrts = Project(id = "funrts", base = file("."))
  .settings(
    organization := "se.joham",
    version := "SNAPSHOT",

    scalaVersion := "2.11.8",

    libraryDependencies ++= Seq(
      "org.json4s"           %%   "json4s-core"           % "3.3.0",
      "com.badlogicgames.gdx" %   "gdx"                   % ext_libgdxVersion,
      "com.badlogicgames.gdx" %   "gdx-freetype"          % ext_libgdxVersion,
      "com.badlogicgames.gdx" %   "gdx-backend-lwjgl"     % ext_libgdxVersion,
      "com.badlogicgames.gdx" %   "gdx-platform"          % ext_libgdxVersion classifier "natives-desktop",
      "com.badlogicgames.gdx" %   "gdx-freetype-platform" % ext_libgdxVersion classifier "natives-desktop",
      "net.java.dev.jna"      %   "jna-platform"          % "4.2.2",
      "net.java.dev.jna"      %   "jna"                   % "4.2.2"
    )
    
  )
  .dependsOn(uri("git://github.com/GiGurra/service-utils.git#0.1.12"))
