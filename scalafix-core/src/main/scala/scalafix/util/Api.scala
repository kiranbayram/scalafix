package scalafix.util

import scalafix.internal.util.ProductLabeledStructure
import scalafix.internal.util.ProductStructure

trait Api {

  type RuleName = scalafix.rule.RuleName
  val RuleName = scalafix.rule.RuleName

  type Patch = scalafix.patch.Patch
  val Patch = scalafix.patch.Patch

  implicit class XtensionSeqPatch(patches: Iterable[Patch]) {
    def asPatch: Patch = Patch.fromIterable(patches)
  }

  implicit class XtensionOptionPatch(patch: Option[Patch]) {
    def asPatch: Patch = patch.getOrElse(Patch.empty)
  }

  type Diagnostic = scalafix.lint.Diagnostic
  val Diagnostic = scalafix.lint.Diagnostic

  type CustomMessage[T] = scalafix.config.CustomMessage[T]
  val CustomMessage = scalafix.config.CustomMessage

  implicit class XtensionScalafixProductInspect(product: Product) {
    // NOTE(olafurpg) I can't explain why, but fortunately these methods shadow
    // `scala.meta.Tree.structure` regardless of how `import scalafix.v1, scala.meta._`.
    // These structure methods work the same except they implement line wrapping which
    // makes the output more readable.
    def structure: String =
      ProductStructure.structure(product).render(80)
    def structure(printWidth: Int): String =
      ProductStructure.structure(product).render(printWidth)
    def structureLabeled: String =
      ProductLabeledStructure.structure(product).render(80)
    def structureLabeled(printWidth: Int): String =
      ProductLabeledStructure.structure(product).render(printWidth)
  }

}
