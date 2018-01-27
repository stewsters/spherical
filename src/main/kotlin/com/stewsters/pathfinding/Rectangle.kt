package com.stewsters.pathfinding

class Rectangle(val lower: Vec2, val upper: Vec2){

    fun inside(point: Vec2) : Boolean{
        return point.x >= lower.x && point.y >= lower.y && point.x <= upper.x && point.y <= upper.y
    }

}
