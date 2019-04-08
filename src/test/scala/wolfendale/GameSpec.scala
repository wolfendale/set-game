package wolfendale

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FreeSpec, MustMatchers, OptionValues}
import wolfendale.Card._
import wolfendale.Game.MoveResult

class GameSpec extends FreeSpec with MustMatchers with GeneratorDrivenPropertyChecks with OptionValues {

  "scores" - {

    "must return the scores for the given game" in {

      forAll(arbitrary[List[Card]], arbitraryScores) {
        (deck, scores) =>

          val game = Game(deck, scores)

          val returnedScores = Game.scores.runA(game).value

          returnedScores mustEqual scores
      }
    }
  }

  "move" - {

    "when given a valid move" - {

      "must increase the given player's score and update the deck" in {

        forAll(arbitrary[Player], Gen.chooseNum(0, 10)) {
          (player, initialScore) =>

            val card1 = Card(Number.One, Shape.Diamond, Colour.Green, Shading.Outlined)
            val card2 = Card(Number.Two, Shape.Oval, Colour.Purple, Shading.Solid)
            val card3 = Card(Number.Three, Shape.Squiggle, Colour.Red, Shading.Striped)
            val card4 = Card(Number.One, Shape.Oval, Colour.Purple, Shading.Solid)

            val game = Game(List(card1, card2, card3, card4), Map(player -> initialScore))

            val (updatedGame, moveResult) = Game.move(player, card1, card2, card3).run(game).value

            moveResult mustBe MoveResult.Valid
            updatedGame.scores must contain (player -> (initialScore + 1))
            updatedGame.currentCards must contain only card4
            updatedGame.deck mustBe empty
        }
      }
    }

    "when given a move where the chosen cards are not in the current cards" - {

      "must not change the game state and return an invalid move result" in {

        forAll(arbitrary[Player], arbitrary[Card], arbitrary[Card], arbitrary[Card]) {
          (player, card1, card2, card3) =>

            val game = Game(List.empty)
            val (updatedGame, moveResult) = Game.move(player, card1, card2, card3).run(game).value

            updatedGame mustEqual game
            moveResult mustBe MoveResult.Invalid
        }
      }
    }
  }

  "winner" - {

    "when a game has completed" - {

      "must return the player with the highest score" in {

        val genScores = for {
          topScore    <- Gen.chooseNum(2, 10)
          topPlayer   <- arbitrary[Player]
          otherScores <- Gen.mapOf(for {
            player <- arbitrary[Player]
            score  <- Gen.chooseNum(0, topScore - 1)
          } yield player -> score)
        } yield (topPlayer -> topScore, otherScores)

        forAll(genScores) {
          case (topPlayer, scores) =>

            val game = Game(List.empty, scores + topPlayer)
            val winner = Game.winner.runA(game).value.value

            winner mustBe topPlayer
        }
      }
    }

    "when a game has not completed" - {

      "must return None" in {

        forAll(arbitrary[Game]) {
          game =>

            whenever(game.deck.nonEmpty || game.currentCards.nonEmpty) {

              val winner = Game.winner.runA(game).value
              winner mustNot be (defined)
            }
        }
      }
    }
  }
}
