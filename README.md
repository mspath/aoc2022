# Advent of Code 2022

[Advent of Code] - an advent calendar of programming puzzles.

I'll attempt to solve them with Kotlin.

[Advent of Code]:https://adventofcode.com/2022 

---

### Day 1 Calorie Counting

find the elves carrying the most calories.

### Day 2 Rock Paper Scissors

play Rock Paper Scissors based on a provided strategy.

### Day 3 Rucksack Reorganization

help the elves packing their rucksacks.

### Day 4 Camp Cleanup

help the elves discover overlapping assignments.

### Day 5 Supply Stacks

help the elves with logistics.

### Day 6 Tuning Trouble

help the elves with their messages. 

### Day 7 No Space Left On The Device

help the elves with s system update. This if the first puzzle which becomes much easier once you've
figured out what you actually don't have to do. (i.e. a tree lends itself as a feasible data structure,
but it turns out to be hard to parse, 'flattening' the process and just storing sizes to all affected
paths is much easier)

### Day 8 Treetop Tree House

help the elves with finding a nice spot for their tree house. The first grid puzzle of the year, it
was a little tricky to integrate the 'perspective' of each location. (probably it would have been
easier to just use loops and breaks)

### Day 9 Rope Bridge

help the elves crossing a river by simulating a rope bridge.

### Day 10 Cathode-Ray Tube

emulate a very cool CRT.

### Day 11 Monkey in the Middle

deal with a group of monkeys who are playing with your items. The first puzzle this year, where it's 
essential to find out what you don't need to worry about.

### Day 12 Hill Climbing Algorithm

help the elves finding the shortest path. My solution is not very idiomatic, but I like it as
a poor man's breath first search which delegates some logic towards the data class.

### Day 13 Distress Signal

calibrate your device to communicate with the elves.

### Day 14 Regolith Reservoir

simulate a sandy cave.

### Day 15 Beacon Exclusion Zone

locate beacons in a large grid.

### Day 16 Proboscidea Volcanium

navigate a cave with valves.

### Day 17 Pyroclastic Flow

calculate the height of a narrow chamber with falling rocks.

### Day 18 Boiling Boulders

calculate the surface area of obsidian.

### Day 19 Not Enough Minerals

simulate a series of state machines. 

### Day 20 Grove Positioning System

Find the location of the grove.

### Day 21 Monkey Math

Play with the monkeys and figure out their math. Obviously the use an interesting system.

### Day 22 Monkey Map

Find the password for a strange input device.

### Day 23 Unstable Diffusion

Help the elves picking star fruit.

### Day 24 Blizzard Basin

Navigate a basin full of blizzards.

### Day 25 Full of Hot Air

Help the elves fueling up their balloon. Obviously they use an interesting numeric system.

---

## Links

here are some links to repositories and articles covering this season of Advent of Code.

### Kotlin

- [Advent of Code 2022 Directory](https://todd.ginsberg.com/post/advent-of-code/2022/) by Todd Ginsberg. Elegant solutions in Kotlin.
- [advent-2022-kotlin](https://github.com/tginsberg/advent-2022-kotlin) Todd's repository.
- [Advent of Code in Kotlin](https://www.youtube.com/playlist?list=PLlFc5cFwUnmwxQlKf8uWp-la8BVSTH47J) Jetbrains has a series of live events on Youtube.

### Python

- [Jonathan Paulson's Channel](https://www.youtube.com/channel/UCuWLIm0l4sDpEe28t41WITA)
- [hyper neutrino's Channel](https://www.youtube.com/@hyper-neutrino)

those two are not only amazingly fast in parsing the puzzle, understanding the problem, and implementing
the solution, they also explain their reasoning and the algorithm involved.

### Rust

tbd

### Misc

- [Advent of Code Subreddit](https://www.reddit.com/r/adventofcode/)

---

Environment

- Kotlin 1.8.10
- Intellij 2022.3.3

---

```
language: kotlin
repo: aoc2022
status: active
updated: 2023-03-19
```