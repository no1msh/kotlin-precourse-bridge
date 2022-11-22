package bridge

import bridge.domain.BridgeGame
import bridge.domain.BridgeMaker
import bridge.view.InputView
import bridge.view.OutputView

class BridgeGameController(
    private val inputView: InputView,
    private val outputView: OutputView,
    private val bridgeMaker: BridgeMaker
) {
    private lateinit var bridgeGame: BridgeGame

    fun startGame() {
        outputView.printGameStart()
        outputView.printRequestInputBridgeSize()
        val size = inputView.readBridgeSize()
        bridgeGame = BridgeGame(bridgeMaker.makeBridge(size))
        outputView.init(bridgeGame.getAnswerBridge(), bridgeMaker)
        val isWin = gameProgress(size)
        outputView.printResult(bridgeGame.getPosition(), isWin)
        outputView.printResultDetail(isWin, bridgeGame.getTryCount())
    }

    private fun gameProgress(size: Int): Boolean {
        while (bridgeGame.getPosition() < size) {
            outputView.printRequestInputDirectionToMove()
            val direction = inputView.readMoving()
            val isRight = bridgeGame.move(direction)
            outputView.printMap(bridgeGame.getPosition(), isRight)
            bridgeGame.nextPosition()
            if (isRight) continue
            if (isGameOver()) return false
        }
        return true
    }

}