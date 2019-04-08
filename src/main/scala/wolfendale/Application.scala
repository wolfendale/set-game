package wolfendale

import scala.util.Random

object Application extends App {

  val deck = for {
    number  <- Card.Number.values
    shape   <- Card.Shape.values
    colour  <- Card.Colour.values
    shading <- Card.Shading.values
  } yield Card(number, shape, colour, shading)

  val game = Game(Random.shuffle(deck.toList))

  // do some stuff!
}
