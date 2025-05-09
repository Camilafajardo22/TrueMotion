package com.google.mlkit.vision.demo.kotlin.posedetector

import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.acos
import kotlin.math.PI

class LegStretchEvaluator {
    companion object {

        fun isRightLegStretched(pose: Pose): Boolean {
            val hip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
            val knee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
            val ankle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)

            if (listOf(hip, knee, ankle).any { it == null }) return false

            val angle = calculateAngle(hip!!, knee!!, ankle!!)
            return abs(angle - 180) < 20
        }

        fun isLeftLegStretched(pose: Pose): Boolean {
            val hip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
            val knee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
            val ankle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)

            if (listOf(hip, knee, ankle).any { it == null }) return false

            val angle = calculateAngle(hip!!, knee!!, ankle!!)
            return abs(angle - 180) < 20
        }

        fun isLegStretched(pose: Pose): Boolean {
            return isLeftLegStretched(pose) && isRightLegStretched(pose)
        }

        private fun calculateAngle(a: PoseLandmark, b: PoseLandmark, c: PoseLandmark): Double {
            val ab = doubleArrayOf((a.position.x - b.position.x).toDouble(), (a.position.y - b.position.y).toDouble())
            val cb = doubleArrayOf((c.position.x - b.position.x).toDouble(), (c.position.y - b.position.y).toDouble())

            val dot = ab[0] * cb[0] + ab[1] * cb[1]
            val magA = sqrt(ab[0] * ab[0] + ab[1] * ab[1])
            val magC = sqrt(cb[0] * cb[0] + cb[1] * cb[1])

            val angleRad = acos(dot / (magA * magC))
            return angleRad * (180.0 / PI)
        }
    }
}
