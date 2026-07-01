package com.grimco.gymapp.presentation.utils




fun Long.convertToTime(): String {
    val seconds = (( this / 1000) % 60).pad()
    val minutes = (( this / (1000 * 60)) % 60).pad()
    val hours = (( this / (1000 * 60 * 60)) % 24).pad()
    return "$hours:$minutes:$seconds"
}


fun Long.pad(length: Int = 2) = this.toString().padStart(length, '0')

