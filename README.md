# Chess AI

console chess ai that generates moves using minimax (with alpha/beta pruning)

Includes a PGN (portable game notation) parser for setting up boards from external sources.

The most interesting classes are `chess.engine.ai.MinimaxChessMoveAi` where the moves are generated, `chess.engine.legalmoves.LegalMoveGenerator` where all legal moves are calculated. And finally `chess.engine.BoardGenerator` where PGN and JSON files can be parsed into board objects.
