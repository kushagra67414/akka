/*
 * Copyright (C) 2018-2021 Lightbend Inc. <https://www.lightbend.com>
 */

package akka

import com.typesafe.sbt.MultiJvmPlugin
import sbt.{AutoPlugin, Def, PluginTrigger, Plugins, ScalafixSupport, Setting, inConfig}
import scalafix.sbt.ScalafixPlugin
import scalafix.sbt.ScalafixPlugin.autoImport.scalafixConfigSettings

object ScalafixForMultiNodePlugin extends AutoPlugin with ScalafixSupport {
  override def trigger: PluginTrigger = allRequirements

  override def requires: Plugins = MultiNode && ScalafixPlugin

  import MultiJvmPlugin.autoImport._

  lazy val scalafixIgnoredSetting: Seq[Setting[_]] = Seq(
    ignore(MultiJvm)
  )

  override def projectSettings: Seq[Def.Setting[_]] =
    Seq(MultiJvm).flatMap(c => inConfig(c)(scalafixConfigSettings(c))) ++
      scalafixIgnoredSetting ++ Seq(
      updateProjectCommands(
        alias = "fixall",
        value = ";scalafixEnable;scalafixAll;scalafmtAll"),
      updateProjectCommands(
        alias = "sortImports",
        value = ";scalafixEnable;scalafixAll SortImports;scalafmtAll")
    )
}
