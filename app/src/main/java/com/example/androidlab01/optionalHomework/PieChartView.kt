package com.example.androidlab01.optionalHomework

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.ColorUtils
import kotlin.math.*

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    fun setData(segments: List<Pair<String, Int>>, colors: List<Int>) {
        validate(segments, colors)
        this.segments = segments
        this.colors = colors
        selectedIndex = -1
        invalidate()
    }

    private var segments: List<Pair<String, Int>> = emptyList()
    private var colors: List<Int> = emptyList()
    private var selectedIndex: Int = -1

    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    private val GAP_DEG = 2f
    private val oval = RectF()
    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (segments.isEmpty()) return

        val cx = width / 2f
        val cy = height / 2f
        val radius = (min(width, height) / 2f) * 0.82f

        textPaint.textSize = radius * 0.12f

        oval.set(cx - radius, cy - radius, cx + radius, cy + radius)

        val totalGap = GAP_DEG * segments.size
        val availableDeg = 360f - totalGap

        val sweeps = segments.map { (_, value) -> availableDeg * value / 100f }

        var startAngle = -90f

        segments.indices.forEach { i ->
            val sweep = sweeps[i]
            val isSelected = i == selectedIndex

            val baseColor = colors[i]
            arcPaint.color = if (isSelected) brighten(baseColor) else baseColor

            path.reset()
            path.moveTo(cx, cy)
            path.arcTo(oval, startAngle, sweep)
            path.close()
            canvas.drawPath(path, arcPaint)

            val midAngle = Math.toRadians((startAngle + sweep / 2).toDouble())
            val labelR = radius * 0.65f
            val lx = cx + (labelR * cos(midAngle)).toFloat()
            val ly = cy + (labelR * sin(midAngle)).toFloat() + textPaint.textSize * 0.35f
            canvas.drawText("${segments[i].second}%", lx, ly, textPaint)

            startAngle += sweep + GAP_DEG
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_DOWN) return super.onTouchEvent(event)

        val cx = width / 2f
        val cy = height / 2f
        val radius = (min(width, height) / 2f) * 0.82f

        val dx = event.x - cx
        val dy = event.y - cy
        val dist = sqrt(dx * dx + dy * dy)

        if (dist > radius) {
            selectedIndex = -1
            invalidate()
            return true
        }

        var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat() + 90f
        if (angle < 0) angle += 360f

        val totalGap = GAP_DEG * segments.size
        val availableDeg = 360f - totalGap
        var cursor = 0f

        selectedIndex = -1
        segments.indices.forEach { i ->
            val sweep = availableDeg * segments[i].second / 100f
            val sectorEnd = cursor + sweep
            if (angle >= cursor && angle < sectorEnd) {
                selectedIndex = i
            }
            cursor = sectorEnd + GAP_DEG
        }

        invalidate()
        return true
    }
    private fun validate(segments: List<Pair<String, Int>>, colors: List<Int>) {

        require(segments.isNotEmpty()) { "Список секторов не должен быть пустым" }

        val sum = segments.sumOf { it.second }
        require(sum == 100) {
            "Сумма всех значений секторов должна равняться 100, но равна $sum"
        }

        require(colors.size == segments.size) {
            "Размер списка цветов (${colors.size}) должен совпадать с размером списка секторов (${segments.size})"
        }

        colors.indices.forEach { i ->
            val next = (i + 1) % colors.size
            require(colors[i] != colors[next]) {
                "Соседние секторы с индексами $i и $next имеют одинаковый цвет (${colors[i]}). " +
                        "Соседние секторы должны иметь разные цвета."
            }
        }
    }

    private fun brighten(color: Int): Int =
        ColorUtils.blendARGB(color, Color.WHITE, 0.35f)
}