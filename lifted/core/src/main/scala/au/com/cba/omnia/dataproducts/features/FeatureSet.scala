package au.com.cba.omnia.dataproducts.features

import scala.reflect.runtime.universe.TypeTag

import scalaz.syntax.std.boolean.ToBooleanOpsFromBoolean

import au.com.cba.omnia.maestro.api.Field

import Feature._

trait FeatureSet[S] {
  def namespace: Feature.Namespace

  def features: Iterable[Feature[S, Value]]

  def generate(source: S): Iterable[FeatureValue[S, Value]] = features.flatMap(f =>
    f.generate(source)
  )

  def generateMetadata: Iterable[FeatureMetadata[Value]] = {
    features.map(_.metadata)
  }
}

trait PivotFeatureSet[S] extends FeatureSet[S] {
  def entity(s: S): EntityId
  def time(s: S):   Time

  def pivot[V <: Value : TypeTag, FV <% V](field: Field[S, FV], fType: Feature.Type) =
    Patterns.pivot(namespace, fType, entity, time, field)
}

abstract class QueryFeatureSet[S, V <: Value : TypeTag] extends FeatureSet[S] {
  type Filter = S => Boolean

  def featureType:  Feature.Type

  def entity(s: S): EntityId
  def value(s: S):  V
  def time(s: S):   Time

  def queryFeature(featureName: Feature.Name, filter: Filter) =
    Patterns.general(namespace, featureName, featureType, entity, (s: S) => filter(s).option(value(s)), time)
}