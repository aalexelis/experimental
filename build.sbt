name := "experimental"

version := "1.0"

scalaVersion := "2.10.2"

resolvers ++= Seq(
  "Java.net Maven2 Repository"     at "http://download.java.net/maven/2/",
  "Sonatype scala-tools repo"      at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype scala-tools releases"  at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype scala-tools snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

{
  val liftVersion = "3.0-SNAPSHOT"
  libraryDependencies ++= Seq(
    "net.liftweb"             %% "lift-webkit"       % liftVersion             % "compile",
    "net.liftmodules"         %% "fobo_3.0"          % "1.3-M1"                   % "compile",
    "net.liftweb"             %% "lift-testkit"      % liftVersion             % "test",
    "org.eclipse.jetty"       %  "jetty-webapp"      % "9.2.0.RC0"       %  "container,compile",
    "org.eclipse.jetty.orbit" %  "javax.servlet"     % "3.0.0.v201112011016"   %  "container,compile" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"          %  "logback-classic"   % "1.1.2",
    "org.scalatest"           %% "scalatest"         % "2.2.0-M1"               % "test",
    "com.h2database"          %  "h2"                % "1.4.178"
  )
}

com.earldouglas.xsbtwebplugin.WebPlugin.webSettings