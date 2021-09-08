import java.nio.file.{Path, Paths}

object IDEPathHelper {

  private val projectRootDir = Paths.get(getClass.getClassLoader.getResource("gatling.conf").toURI).getParent.getParent.getParent

  val mavenTargetDirectory: Path = projectRootDir.resolve("target")
  val mavenSrcMainDirectory: Path = projectRootDir.resolve("src").resolve("main")

  val mavenSourcesDirectory: Path = mavenSrcMainDirectory.resolve("scala")
  val mavenResourcesDirectory: Path = mavenSrcMainDirectory.resolve("resources")
  val mavenBinariesDirectory: Path = mavenTargetDirectory.resolve("classes")
  val resultsDirectory: Path = mavenTargetDirectory.resolve("gatling")
  val recorderConfigFile: Path = mavenResourcesDirectory.resolve("recorder.conf")
}
