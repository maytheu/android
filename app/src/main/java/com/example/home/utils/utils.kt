package com.example.home.utils

import android.util.Log
import com.example.home.model.DataGroupAttribute

fun deviceAttr(atr: DataGroupAttribute): String {
    if (atr.attributeType == "1") {
        if (atr.attribute == "DOOR_OPEN_STATUS") {
            return if (atr.attributeValue == "1") "Opened" else "Closed"
        }
        if (atr.attribute == "WATER_LEAK_STATUS") {
            return if (atr.attributeValue == "1") {
                "Leaking"
            } else {
                "Not Leaking"
            }
        }
    }
    return atr.attributeValue
}

fun statusIcon(attr: DataGroupAttribute): String {
    Log.d("TAG", "statusIcon: ${attr.attribute}")
    if (attr.attribute == "SNR" || attr.attribute == "RSSI") {
        return "Wifi"
    }
    if (attr.attribute == "ALARM" || attr.attribute.contains("TIMES") || attr.attribute.contains("DURATION")) {
        return "Alarm"
    }
    if (attr.attribute == "Battery" || attr.attribute.contains("Battery")) {
        return "Battery"
    }
    if (attr.attribute == "Latitude" || attr.attribute == "Longitude") {
        return "loc"
    }
    return ""
}