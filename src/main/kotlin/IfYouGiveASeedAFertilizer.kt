fun main() {
    val count = readln().toInt()
    val lines = (1..count).map { readln() }

    val rawSeeds = lines.first().drop(7).split(" ").map { it.toLong() }
    val starts = rawSeeds.filterIndexed { index, _ -> index % 2 == 0 }
    val rangeLengths = rawSeeds.filterIndexed { index, _ -> index % 2 == 1 }
    val ranges = starts.mapIndexed { index, start -> start..start + rangeLengths[index] }

    val seedToSoilMapStart = lines.indexOf("seed-to-soil map:")
    val soilToFertilizerMapStart = lines.indexOf("soil-to-fertilizer map:")
    val fertilizerToWaterMapStart = lines.indexOf("fertilizer-to-water map:")
    val waterToLightMapStart = lines.indexOf("water-to-light map:")
    val lightToTempMapStart = lines.indexOf("light-to-temperature map:")
    val tempToHumidityMapStart = lines.indexOf("temperature-to-humidity map:")
    val humidityToLocationMapStart = lines.indexOf("humidity-to-location map:")

    val seedToSoilMap = lines.subList(seedToSoilMapStart + 1, soilToFertilizerMapStart - 1).map(::toRangePair)
    val soilToFertilizerMap =
        lines.subList(soilToFertilizerMapStart + 1, fertilizerToWaterMapStart - 1).map(::toRangePair)
    val fertilizerToWaterMap = lines.subList(fertilizerToWaterMapStart + 1, waterToLightMapStart - 1).map(::toRangePair)
    val waterToLightMap = lines.subList(waterToLightMapStart + 1, lightToTempMapStart - 1).map(::toRangePair)
    val lightToTempMap = lines.subList(lightToTempMapStart + 1, tempToHumidityMapStart - 1).map(::toRangePair)
    val tempToHumidityMap = lines.subList(tempToHumidityMapStart + 1, humidityToLocationMapStart - 1).map(::toRangePair)
    val humidityToLocationMap = lines.subList(humidityToLocationMapStart + 1, count).map(::toRangePair)

    val answer = ranges.mapIndexed { index, range ->
        val location = range.minOf { seed ->
            val soil = seedToSoilMap.getDestination(seed)
            val fertilizer = soilToFertilizerMap.getDestination(soil)
            val water = fertilizerToWaterMap.getDestination(fertilizer)
            val light = waterToLightMap.getDestination(water)
            val temp = lightToTempMap.getDestination(light)
            val humidity = tempToHumidityMap.getDestination(temp)
            val location = humidityToLocationMap.getDestination(humidity)
            location
        }
        println("Range $index of ${ranges.count()} completed")
        location
    }.min()
    println(answer)
}

fun toRangePair(string: String): Pair<LongRange, LongRange> {
    val parts = string.split(" ").map { it.toLong() }
    val rangeSize = parts.last()
    val destinationRange = parts.first()..parts.first() + rangeSize
    val sourceRange = parts[1]..parts[1] + rangeSize
    return sourceRange to destinationRange
}

fun List<Pair<LongRange, LongRange>>.getDestination(source: Long): Long {
    return this.find { source in it.first }?.getDestination(source) ?: source
}

fun Pair<LongRange, LongRange>.getDestination(source: Long): Long {
    val sourceRange = this.first
    val destinationRange = this.second

    val sourceStart = sourceRange.first
    val distance = source - sourceStart
    return destinationRange.first + distance
}