import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Arbitrary.arbitrary

package object wolfendale {

  import Card._

  implicit lazy val arbitraryNumber: Arbitrary[Number.Value] = Arbitrary(Gen.oneOf(Number.values.toSeq))
  implicit lazy val arbitraryShape: Arbitrary[Shape.Value] = Arbitrary(Gen.oneOf(Shape.values.toSeq))
  implicit lazy val arbitraryColour: Arbitrary[Colour.Value] = Arbitrary(Gen.oneOf(Colour.values.toSeq))
  implicit lazy val arbitraryShading: Arbitrary[Shading.Value] = Arbitrary(Gen.oneOf(Shading.values.toSeq))

  implicit lazy val arbitraryCard: Arbitrary[Card] = Arbitrary {
    for {
      number  <- arbitrary[Number.Value]
      shape   <- arbitrary[Shape.Value]
      colour  <- arbitrary[Colour.Value]
      shading <- arbitrary[Shading.Value]
    } yield Card(number, shape, colour, shading)
  }

  implicit lazy val arbitraryPlayer: Arbitrary[Player] = Arbitrary(Gen.alphaNumStr.map(Player))

  lazy val arbitraryScores: Gen[Map[Player, Int]] = Gen.mapOf(
    for {
      player <- arbitrary[Player]
      score  <- Gen.chooseNum(0, 10)
    } yield player -> score
  )

  implicit lazy val arbitraryGame: Arbitrary[Game] = Arbitrary {

    for {
      deck   <- arbitrary[List[Card]]
      scores <- arbitraryScores
    } yield Game(deck, scores)
  }
}
