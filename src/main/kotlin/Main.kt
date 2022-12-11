import day1.testCalorieCounting
import day10.testCathodeRayTube
import day11.testMonkeyInTheMiddle
import day2.testRockPaperScissors
import day3.testRucksackReorganization
import day4.testCampCleanup
import day5.testSupplyStacks
import day6.testTuningTrouble
import day7.testNoSpaceLeftOnTheDevice
import day8.testTreetopTreeHouse
import day9.testRopeBridge

fun main() {
    day11.main()

    runTests()
}

fun runTests() {
    testCalorieCounting()
    testRockPaperScissors()
    testRucksackReorganization()
    testCampCleanup()
    testSupplyStacks()
    testTuningTrouble()
    testNoSpaceLeftOnTheDevice()
    testTreetopTreeHouse()
    testRopeBridge()
    testCathodeRayTube()
    testMonkeyInTheMiddle()
}