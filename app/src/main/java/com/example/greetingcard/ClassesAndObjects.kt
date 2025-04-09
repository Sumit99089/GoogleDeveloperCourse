package com.example.greetingcard

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SmartDevice(val name: String,val category:String){
    var deviceStatus = "online"
        private set
    open val deviceType = "unknown"

    open fun turnOn(){
        deviceStatus = "on"
    }
    open fun turnOff(){
        deviceStatus = "off"
    }
}

class SmartTvDevice(deviceName: String, deviceCategory: String):
    SmartDevice(name = deviceName, category = deviceCategory){
    //     private var speakerVolume = 2
//     	set(value){
//             if(value in 0..100)
//             	field = value
//         }
    private var speakerVolume by RangeRegulator(initialValue=0, minValue=0, maxValue= 100)
    // 	private var channelNumber = 1
//     	set(value){
//             if(value in 1..200)
//             	field = value
//         }
    private var channelNumber by RangeRegulator(initialValue=1, minValue=1, maxValue= 200)

    override val deviceType = "Smart Tv"

    fun increaseSpeakerVolume(){
        speakerVolume++
        println("Current Speaker Volume: ${speakerVolume}")
    }

    fun nextChannel(){
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }

    override fun turnOn(){
        super.turnOn()
        println("$name is on. Volume: $speakerVolume. Channel: $channelNumber")
    }

    override fun turnOff(){
        super.turnOff()
        println("$name is off.")
    }

}

class SmartLightDevice(deviceName: String, deviceCategory: String):
    SmartDevice(name = deviceName, category = deviceCategory){

    override val deviceType = "Smart Light"

    private var brightnessLevel = 0
        set(value){
            if(value in 0..100)
                field = value
        }


    fun increaseBrightness(){
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }

    fun decreaseBrightness(){
        brightnessLevel--
        println("Brightness decreased to $brightnessLevel.")
    }

    override fun turnOn(){
        super.turnOn()
        brightnessLevel = 2
        println("$name is on. Brightness : $brightnessLevel.")
    }

    override fun turnOff(){
        super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }
}

class SmartHome(
    val smartTvDevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice
){
    var deviceTurnOnCount = 0
        private set

    fun turnOnTV(){
        deviceTurnOnCount++
        smartTvDevice.turnOn()
    }

    fun turnOffTv(){
        deviceTurnOnCount--
        smartTvDevice.turnOff()
    }

    fun increaseTVVolume(){
        smartTvDevice.increaseSpeakerVolume()
    }

    fun increaseTVChannel(){
        smartTvDevice.nextChannel()
    }

    fun turnOnSmartLight(){
        smartLightDevice.turnOn()
        deviceTurnOnCount++
    }

    fun turnOffSmartLight(){
        smartLightDevice.turnOff()
        deviceTurnOnCount--
    }

    fun increaseLightBrightness(){
        smartLightDevice.increaseBrightness()
    }

    fun decreaseLightBrightness(){
        smartLightDevice.decreaseBrightness()
    }

    fun turnOffAllDevices(){
        turnOffTv()
        turnOffSmartLight()
    }
}

class RangeRegulator(
    initialValue : Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any? , Int>{

    var fieldData = initialValue

    override fun getValue(thisRef : Any?, property : KProperty<*>): Int{
        return fieldData
    }

    override fun setValue(thisRef : Any?, property : KProperty<*>, value : Int){
        if(value in minValue..maxValue)
            fieldData = value
    }
}

// ReadWriteProperty<Any?, Int>   <T, V>
// In Kotlin, the ReadWriteProperty interface is used to create custom delegates for properties. It has two type parameters:

// T: The type of the object containing the delegated property. That is the class implementing this custom Read write property, here
//    the T is SmartLightDevice and SmartTvDevice as they are using the RangeRegulator:RearWriteProperty

// V: The type of the property value. the type of variable using "by RangeRegulator"


// thisRef : Any?, property : KProperty<*>
// thisRef: Any?: A reference to the object that owns the property.{Same as the Type T}
// When the property is accessed or modified, thisRef refers to the instance of the class containing the property.
// If it's a top-level property, thisRef will be null.
// Since thisRef is of type Any?, you can cast it to a specific class if needed to access its members.

// property: KProperty<*>: Provides metadata about the property being delegated.
// KProperty is part of Kotlin's reflection API and allows access to information like the property’s name, return type, and annotations.
// The wildcard * indicates that it can represent a property of any type.

// Summary:
// ReadWriteProperty<Any?, Int>: Allows the delegate to be used with any class (Any?) and manages properties of type Int.
// thisRef: Any?: Refers to the instance of the class containing the delegated property, or null for top-level properties.
// property: KProperty<*>: Provides reflective information about the property, such as its name and type.

fun main() {
    var smartDevice: SmartDevice = SmartTvDevice("Android TV", "Entertainment")
    smartDevice.turnOn()

    smartDevice = SmartLightDevice("Google Light", "Utility")
    smartDevice.turnOn()
}