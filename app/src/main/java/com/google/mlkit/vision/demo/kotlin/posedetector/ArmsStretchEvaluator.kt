package com.google.mlkit.vision.demo.kotlin.posedetector
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.PI

class ArmStretchEvaluator {
    companion object {

        fun isRightArmStretched(pose: Pose): Boolean {
            val shoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
            val elbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
            val wrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)

            if (listOf(shoulder, elbow, wrist).any { it == null }) return false

            val angle = calculateAngle(shoulder!!, elbow!!, wrist!!)
            return abs(angle - 180) < 20
        }

        fun isLeftArmStretched(pose: Pose): Boolean {
            val shoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
            val elbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
            val wrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)

            if (listOf(shoulder, elbow, wrist).any { it == null }) return false

            val angle = calculateAngle(shoulder!!, elbow!!, wrist!!)
            return abs(angle - 180) < 20
        }

        fun areBothArmsStretched(pose: Pose): Boolean {
            return isRightArmStretched(pose) && isLeftArmStretched(pose)
        }

        private fun calculateAngle(a: PoseLandmark, b: PoseLandmark, c: PoseLandmark): Double {
            val ab = doubleArrayOf(
                a.position.x.toDouble() - b.position.x.toDouble(),
                a.position.y.toDouble() - b.position.y.toDouble()
            )
            val cb = doubleArrayOf(
                c.position.x.toDouble() - b.position.x.toDouble(),
                c.position.y.toDouble() - b.position.y.toDouble()
            )


            val dot = ab[0] * cb[0] + ab[1] * cb[1]
            val magA = Math.sqrt(ab[0] * ab[0] + ab[1] * ab[1])
            val magC = Math.sqrt(cb[0] * cb[0] + cb[1] * cb[1])

            val angleRad = Math.acos(dot / (magA * magC))
            return angleRad * (180.0 / Math.PI)
        }
    }
}
