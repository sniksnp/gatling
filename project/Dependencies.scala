import sbt._

object Dependencies { 

  /**************************/
  /** Compile dependencies **/
  /**************************/

  private val akkaVersion                    = "2.3.4"

  private def scalaLibrary(version: String)  = "org.scala-lang"                         % "scala-library"               % version
  private def scalaCompiler(version: String) = "org.scala-lang"                         % "scala-compiler"              % version
  private def scalaReflect(version: String)  = "org.scala-lang"                         % "scala-reflect"               % version
  private def scalaSwing(version: String)    = "org.scala-lang"                         % "scala-swing"                 % version
  private val ahc                            = "com.ning"                               % "async-http-client"           % "1.9.0-BETA6"
  private val netty                          = "io.netty"                               % "netty"                       % "3.9.2.Final"
  private val akkaActor                      = "com.typesafe.akka"                     %% "akka-actor"                  % akkaVersion
  private val config                         = "com.typesafe"                           % "config"                      % "1.2.1"
  private val saxon                          = "net.sf.saxon"                           % "Saxon-HE"                    % "9.5.1-6"      classifier "compressed"
  private val slf4jApi                       = "org.slf4j"                              % "slf4j-api"                   % "1.7.7"
  private val fastring                       = "com.dongxiguo"                         %% "fastring"                    % "0.2.4"
  private val threetenbp                     = "org.threeten"                           % "threetenbp"                  % "1.0"
  private val scopt                          = "com.github.scopt"                      %% "scopt"                       % "3.2.0"
  private val scalalogging                   = "com.typesafe"                          %% "scalalogging-slf4j"          % "1.1.0"
  private val jackson                        = "com.fasterxml.jackson.core"             % "jackson-databind"            % "2.4.1.3"
  private val boon                           = "io.fastjson"                            % "boon"                        % "0.23"
  private val jsonpath                       = "io.gatling"                            %% "jsonpath"                    % "0.5.0"
  private val uncommonsMaths                 = "io.gatling.uncommons.maths"             % "uncommons-maths"             % "1.2.3"
  private val joddLagarto                    = "org.jodd"                               % "jodd-lagarto"                % "3.6-RC1"
  private val jzlib                          = "com.jcraft"                             % "jzlib"                       % "1.1.3"
  private val redisClient                    = "net.debasishg"                         %% "redisclient"                 % "2.13"
  private val zinc                           = "com.typesafe.zinc"                      % "zinc"                        % "0.3.5.2"
  private val openCsv                        = "net.sf.opencsv"                         % "opencsv"                     % "2.3"
  private val jmsApi                         = "org.apache.geronimo.specs"              % "geronimo-jms_1.1_spec"       % "1.1.1"
  private val logbackClassic                 = "ch.qos.logback"                         % "logback-classic"             % "1.1.2"
  private val tdigest                        = "com.tdunning"                           % "t-digest"                    % "3.0"
  private val lru                            = "com.googlecode.concurrentlinkedhashmap" % "concurrentlinkedhashmap-lru" % "1.4"

  /***********************/
  /** Test dependencies **/
  /***********************/

  private val scalaTest                      = "org.scalatest"                         %% "scalatest"                   % "2.2.0"         % "test"
  private val akkaTestKit                    = "com.typesafe.akka"                     %% "akka-testkit"                % akkaVersion     % "test"
  private val mockitoCore                    = "org.mockito"                            % "mockito-core"                % "1.9.5"         % "test"
  private val activemqCore                   = "org.apache.activemq"                    % "activemq-broker"             % "5.8.0"         % "test"
  private val sprayCan                       = "io.spray"                               % "spray-can"                   % "1.3.1"         % "test"

  private val testDeps = Seq(scalaTest, akkaTestKit, mockitoCore, sprayCan)
  private val jmsTestDeps = Seq(activemqCore)

  /****************************/
  /** Dependencies by module **/
  /****************************/

  def coreDependencies(scalaVersion: String) = {
    def scalaLibs(version: String) = Seq(scalaLibrary _, scalaCompiler _, scalaReflect _).map(_(version))
    val loggingLibs = Seq(slf4jApi, scalalogging, logbackClassic)
    val checksLibs = Seq(jsonpath, jackson, boon, saxon, joddLagarto)

    Seq(akkaActor, uncommonsMaths, config, fastring, openCsv, lru, threetenbp) ++
    scalaLibs(scalaVersion) ++ loggingLibs ++ checksLibs ++ testDeps
  }

  val redisDependencies = redisClient +: testDeps

  val httpDependencies = Seq(ahc, netty, jzlib) ++ testDeps

  val jmsDependencies = Seq(jmsApi, lru) ++ testDeps ++ jmsTestDeps

  val chartsDependencies = tdigest +: testDeps

  val metricsDependencies = tdigest +: testDeps

  val appDependencies = Seq(scopt, zinc)

  def recorderDependencies(scalaVersion: String) = Seq(scalaSwing(scalaVersion), scopt, jackson) ++ testDeps
}
