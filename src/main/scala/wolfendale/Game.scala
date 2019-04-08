package wolfendale

import cats.data._

final case class Player(name: String)

final case class Game(
                       currentCards: Set[Card],
                       deck: List[Card],
                       scores: Map[Player, Int]
                     )

object Game {

  def apply(deck: List[Card], scores: Map[Player, Int] = Map.empty): Game = {
    val (currentCards, newDeck) = deck.splitAt(12)
    new Game(currentCards.toSet, newDeck, scores)
  }

  object MoveResult extends Enumeration {
    val Valid, Invalid = Value
  }

  def move(player: Player, first: Card, second: Card, third: Card): State[Game, MoveResult.Value] =
    State {
      game =>

        val cards = Set(first, second, third)

        if (cards.intersect(game.currentCards) == cards) {

          val currentPlayerScore = game.scores.getOrElse(player, 0)
          val newScores = game.scores + (player -> (currentPlayerScore + 1))
          val newCards = game.currentCards -- Set(first, second, third) ++ game.deck.take(3)
          val newDeck = game.deck.drop(3)

          (game.copy(scores = newScores, currentCards = newCards, deck = newDeck), MoveResult.Valid)
        } else {

          (game, MoveResult.Invalid)
        }
    }

  val winner: State[Game, Option[(Player, Int)]] =
    State {
      game =>

        val winner = if (game.scores.nonEmpty && game.currentCards.isEmpty && game.deck.isEmpty) {
          Some(game.scores.maxBy(_._2))
        } else {
          None
        }

        (game, winner)
    }

  val scores: State[Game, Map[Player, Int]] =
    State(game => (game, game.scores))
}