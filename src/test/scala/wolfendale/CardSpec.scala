package wolfendale

import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Arbitrary.arbitrary
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FreeSpec, MustMatchers, OptionValues}
import wolfendale.Card._

class CardSpec extends FreeSpec with MustMatchers with GeneratorDrivenPropertyChecks with OptionValues {

  "3 cards" - {

    "whose features do not match" - {

      "must form a set" in {

        val card1 = Card(Number.One, Shape.Diamond, Colour.Green, Shading.Outlined)
        val card2 = Card(Number.Two, Shape.Oval, Colour.Purple, Shading.Solid)
        val card3 = Card(Number.Three, Shape.Squiggle, Colour.Red, Shading.Striped)

        Card.isSet(card1, card2, card3) mustBe true
      }
    }

    "with matching numbers" - {

      "must form a set" in {

        forAll(arbitrary[Number.Value]) {
          number =>

          val card1 = Card(number, Shape.Diamond, Colour.Green, Shading.Outlined)
          val card2 = Card(number, Shape.Oval, Colour.Purple, Shading.Solid)
          val card3 = Card(number, Shape.Squiggle, Colour.Red, Shading.Striped)

          Card.isSet(card1, card2, card3) mustBe true
        }
      }
    }

    "where only 2 cards have matching numbers" - {

      "must not form a set" in {

        forAll(Gen.pick(2, Number.values)) {
          numbers =>

            val number1 = numbers.headOption.value
            val number2 = numbers.lift(1).value

            val card1 = Card(number1, Shape.Diamond, Colour.Green, Shading.Outlined)
            val card2 = Card(number1, Shape.Oval, Colour.Purple, Shading.Solid)
            val card3 = Card(number2, Shape.Squiggle, Colour.Red, Shading.Striped)

            Card.isSet(card1, card2, card3) mustBe false
        }
      }
    }

    "with matching shapes" - {

      "must form a set" in {

        forAll(arbitrary[Shape.Value]) {
          shape =>

            val card1 = Card(Number.One, shape, Colour.Green, Shading.Outlined)
            val card2 = Card(Number.Two, shape, Colour.Purple, Shading.Solid)
            val card3 = Card(Number.Three, shape, Colour.Red, Shading.Striped)

            Card.isSet(card1, card2, card3) mustBe true
        }
      }
    }

    "where only 2 cards have matching shapes" - {

      "must not form a set" in {

        forAll(Gen.pick(2, Shape.values)) {
          shapes =>

            val shape1 = shapes.headOption.value
            val shape2 = shapes.lift(1).value

            val card1 = Card(Number.One, shape1, Colour.Green, Shading.Outlined)
            val card2 = Card(Number.Two, shape1, Colour.Purple, Shading.Solid)
            val card3 = Card(Number.Three, shape2, Colour.Red, Shading.Striped)

            Card.isSet(card1, card2, card3) mustBe false
        }
      }
    }

    "with matching colours" - {

      "must form a set" in {

        forAll(arbitrary[Colour.Value]) {
          colour =>

            val card1 = Card(Number.One, Shape.Diamond, colour, Shading.Outlined)
            val card2 = Card(Number.Two, Shape.Oval, colour, Shading.Solid)
            val card3 = Card(Number.Three, Shape.Squiggle, colour, Shading.Striped)

            Card.isSet(card1, card2, card3) mustBe true
        }
      }
    }

    "where only 2 cards have matching colours" - {

      "must not form a set" in {

        forAll(Gen.pick(2, Colour.values)) {
          colours =>

            val colour1 = colours.headOption.value
            val colour2 = colours.lift(1).value

            val card1 = Card(Number.One, Shape.Diamond, colour1, Shading.Outlined)
            val card2 = Card(Number.Two, Shape.Oval, colour1, Shading.Solid)
            val card3 = Card(Number.Three, Shape.Squiggle, colour2, Shading.Striped)

            Card.isSet(card1, card2, card3) mustBe false
        }
      }
    }

    "with matching shadings" - {

      "must form a set" in {

        forAll(arbitrary[Shading.Value]) {
          shading =>

            val card1 = Card(Number.One, Shape.Diamond, Colour.Green, shading)
            val card2 = Card(Number.Two, Shape.Oval, Colour.Purple, shading)
            val card3 = Card(Number.Three, Shape.Squiggle, Colour.Red, shading)

            Card.isSet(card1, card2, card3) mustBe true
        }
      }
    }

    "where only 2 have matching colours" - {

      "must not form a set" in {

        forAll(Gen.pick(2, Shading.values)) {
          shadings =>

            val shading1 = shadings.headOption.value
            val shading2 = shadings.lift(1).value

            val card1 = Card(Number.One, Shape.Diamond, Colour.Green, shading1)
            val card2 = Card(Number.Two, Shape.Oval, Colour.Purple, shading1)
            val card3 = Card(Number.Three, Shape.Squiggle, Colour.Red, shading2)

            Card.isSet(card1, card2, card3) mustBe false
        }
      }
    }
  }
}
