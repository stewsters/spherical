import com.stewsters.pathfinding.*
import org.junit.Test

class PathfinderTest {

    @Test
    fun testPathfindingInOpen() {

        val obstacles = listOf(
                Rectangle(Vec2[5, 5], Vec2[15, 10]), // switch
                Rectangle(Vec2[5, 15], Vec2[15, 16]) // scale
        )

        val fieldMap = Matrix2d(20, 30, { x, y ->
            obstacles.find { it.inside(Vec2[x, y]) } == null
        })

        val start = Vec2(1, 1)
        val end = Vec2(16, 5)

        val path = findPath(fieldMap, start, end)

        assert(path != null)
        assert(path?.first() == start)
        assert(path?.last() == end)
        assert(path?.size == getManhattanDistance(start, end) + 1)

        path?.forEach { println(it) }
    }


    @Test
    fun testNeighbors() {
        val neighbors = Vec2(3, 3).vonNeumanNeighborhood()

        assert(neighbors.size == 4)
        assert(neighbors.contains(Vec2[3, 4]))
        assert(neighbors.contains(Vec2[3, 2]))
        assert(neighbors.contains(Vec2[2, 3]))
        assert(neighbors.contains(Vec2[4, 3]))
    }
}