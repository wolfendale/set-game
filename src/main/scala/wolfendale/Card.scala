package wolfendale

import wolfendale.Card._

final case class Card(
                       number: Number.Value,
                       shape: Shape.Value,
                       colour: Colour.Value,
                       shading: Shading.Value
                     )

object Card {

  object Number extends Enumeration {
    val One, Two, Three = Value
  }

  object Shape extends Enumeration {
    val Oval, Squiggle, Diamond = Value
  }

  object Colour extends Enumeration {
    val Red, Purple, Green = Value
  }

  object Shading extends Enumeration {
    val Solid, Striped, Outlined = Value
  }

  def isSet(first: Card, second: Card, third: Card): Boolean = {

    val cards = Set(first, second, third)

    val numbers = cards.map(_.number)
    val shapes = cards.map(_.shape)
    val colours = cards.map(_.colour)
    val shadings = cards.map(_.shading)

    List[Set[_]](numbers, shapes, colours, shadings).forall {
      distinctFeatures =>
        distinctFeatures.size == 1 || distinctFeatures.size == 3
    }
  }
}
