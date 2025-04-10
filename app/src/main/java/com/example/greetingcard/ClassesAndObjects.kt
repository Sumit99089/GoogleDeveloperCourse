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
    open fun printDeviceInfo(){
        println("Device Name: $name, Device Category: $category, Device Status: $deviceStatus, Device Type: $deviceType")
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
    fun decreaseSpeakerVolume(){
        speakerVolume--
        println("Current Speaker Volume: ${speakerVolume}")
    }

    fun nextChannel(){
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }

    fun prevChannel(){
        channelNumber--
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

    override fun printDeviceInfo() {
        super.printDeviceInfo()

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
    //TV
    fun turnOnTV(){
        deviceTurnOnCount++
        smartTvDevice.turnOn()
    }

    fun turnOffTv(){
        deviceTurnOnCount--
        smartTvDevice.turnOff()
    }

    fun increaseTvVolume(){
        if (smartTvDevice.deviceStatus == "on")
            smartTvDevice.increaseSpeakerVolume()
    }

    fun decreaseTvVolume(){
        if (smartTvDevice.deviceStatus == "on")
            smartTvDevice.decreaseSpeakerVolume()
    }

    fun increaseTvChannel(){
        if (smartTvDevice.deviceStatus == "on")
            smartTvDevice.nextChannel()
    }

    fun decreaseTvChannel(){
        if (smartTvDevice.deviceStatus == "on")
            smartTvDevice.prevChannel()
    }

    fun smartTvInfo(){
        smartTvDevice.printDeviceInfo()
    }
    //SmartLight
    fun turnOnSmartLight(){
        smartLightDevice.turnOn()
        deviceTurnOnCount++
    }

    fun turnOffSmartLight(){
        smartLightDevice.turnOff()
        deviceTurnOnCount--
    }

    fun increaseLightBrightness(){
        if (smartLightDevice.deviceStatus=="on")
            smartLightDevice.increaseBrightness()
    }

    fun decreaseLightBrightness(){
        if (smartLightDevice.deviceStatus=="on")
            smartLightDevice.decreaseBrightness()
    }

    fun smartLightInfo(){
        smartLightDevice.printDeviceInfo()
    }
    //For Both
    fun turnOffAllDevices(){
        turnOffTv()
        turnOffSmartLight()
        deviceTurnOnCount-=2
    }

    fun turnOnAllDevices(){
        turnOnTV()
        turnOnSmartLight()
        deviceTurnOnCount+=2
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
    var smartDevice1 = SmartTvDevice("Android TV", "Entertainment")

    var smartDevice2 = SmartLightDevice("Google Light", "Utility")

    var smartHome : SmartHome = SmartHome(smartTvDevice = smartDevice1, smartLightDevice = smartDevice2)

    smartHome.turnOnAllDevices()
    smartHome.increaseTvVolume()
    smartHome.decreaseTvVolume()
    smartHome.increaseTvChannel()
    smartHome.decreaseTvChannel()
    smartHome.smartTvInfo()
    println(smartHome.deviceTurnOnCount)
    smartHome.increaseLightBrightness()
    smartHome.decreaseLightBrightness()
    smartHome.smartLightInfo()
    smartHome.turnOffAllDevices()
    println(smartHome.deviceTurnOnCount)
}




/*

Notes on Oops:

Encapsulation. Wraps the related properties and methods that perform action on those properties in a class.
For example, consider your mobile phone. It encapsulates a camera, display, memory cards, and several other hardware
and software components. You don't have to worry about how components are wired internally.

Abstraction. An extension to encapsulation. The idea is to hide the internal implementation logic as much as possible.
For example, to take a photo with your mobile phone, all you need to do is open the camera app, point your phone to the
scene that you want to capture, and click a button to capture the photo. You don't need to know how the camera app is built
or how the camera hardware on your mobile phone actually works. In short, the internal mechanics of the camera app and how a
mobile camera captures the photos are abstracted to let you perform the tasks that matter.

Inheritance. Enables you to build a class upon the characteristics and behavior of other classes by establishing a parent-child
relationship. For example, there are different manufacturers who produce a variety of mobile devices that run Android OS, but the
UI for each of the devices is different. In other words, the manufacturers inherit the Android OS feature and build their
customizations on top of it.

Polymorphism. The word is an adaptation of the Greek root poly-, which means many, and -morphism, which means forms.
Polymorphism is the ability to use different objects in a single, common way. For example, when you connect
a Bluetooth speaker to your mobile phone, the phone only needs to know that there's a device that can play audio over Bluetooth.
However, there are a variety of Bluetooth speakers that you can choose from and your phone doesn't need to know how
to work with each of them specifically.

class ClassName{
    fun sum(num1: Int, num2: Int):Int{
        return num1+num2
    }
}

fun main(){
    val x = ClassName()  // x is an object of class ClassName
    val result = x.sum(5,10)
}

Class with methods, properties and constructors:

class SmartDevice {
    Property/Attributes:

    val name = "Android TV"
    val category = "Entertainment"
    var deviceStatus = "online"
    Methods:

    fun turnOn() {
        println("Smart device is turned on.")
    }

    fun turnOff() {
        println("Smart device is turned off.")
    }
}
fun main() {
    val smartTvDevice = SmartDevice()
    println("Device name is: ${smartTvDevice.name}")
    smartTvDevice.turnOn()
    smartTvDevice.turnOff()
}

More on Class Properties:
var speakerVolume = 2
    get() = field  (field refers to the backing field of the property speakerVolume, meaning the place where the value of speakerVolume is stored)
    set(value) {
        field = value(Don't update the speakerVolume directly like "speakerVolume = value" as it will recursively call set function.
    }


set(value) {
    if (value >= 0) counter = value // ❌ leads to recursion
}
Because this will call the setter again!

So it would go like this:

counter = 5 is called → calls the setter.
Inside setter, you do counter = 5 → which calls the setter again.
This happens over and over, creating infinite recursion → leading to a stack overflow error.


Getters and setters are used to access and modify and set the value of Class attributes.
Understanding field and value in a Setter:

field: Represents the property's backing field, which is the actual storage location for
the property's value. It allows direct access to the property's current value within the
setter.

value: Represents the new value being assigned to the property. When you assign a value
to the property, this identifier holds the value that is being set.

Primary Constructor:

class ClassName constructor(parameters) {
    // Class body
    init{
        primary constructor body
    }
}

class ClassName(parameters) {
    // Class body
    init{
        primary constructor body
    }
}
The above declarations have no key differences. If the primary constructor is of public type
it is ok to remove the constructor keyword  like in the 2nd one above. If it of private/protected,
then :

class ClassName private/protected constructor(parameters)//primary constructor{
init{

}
}

If you declare parameters of primary constructor using val/var x:String , then x is both constructor parameter and class property/attributes


Secondary Constructor:
class ClassName(primaryConstructorParameters) {
    var classAttributes = //anything
        init{
            primary constructor body
        }
    constructor(secondaryConstructorParameter) : this(values from secondary constructors that primary constructor wants) {
        secondary constructor body
    }
}

So if we make an object of the above class like:
val x = ClassName(primaryConstructorParameters)
then only primary constructor is executed.

val x = ClassName(secondaryConstructorParameters)
then the secondary constructor is called and the secondary constructor calls primary constructor
using :this(parameters) and only after primary constructor runs will secondary one runs.

Example:
class SmartDevice(val name: String, val category: String) {
    var deviceStatus = "online"

    constructor(name: String, category: String, statusCode: Int) : this(name, category) {
        deviceStatus = when (statusCode) {
            0 -> "offline"
            1 -> "online"
            else -> "unknown"
        }
    }
}

If I make an object like val x = SmartDevice("AC","Electrical Appliance",0), then secondary constructor
is called but the secondary constructor first calls primary constructor using this(name, category) and passes
the name and category it gets as arguments to primary constructor and only after primary runs will secondary run.

Inheritance:{open keyword makes a class possible to be inherited}

open class SuperClass{
}

class SubClass : SuperClass{
}

Example:

open class SmartDevice(val name: String, val category: String) {
    ...
}

class SmartTvDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {
}

SmartTvDevice IS-A SmartDevice --ISA Relationship when one class Inherits other
Reuse code with open, override, and super.

Example:
open class SmartDevice {
    open val type = "Unknown"
    open fun turnOn() { println("Device turned on") }
}

class SmartTvDevice : SmartDevice() {
    override val type = "Smart TV" // Custom type
    override fun turnOn() {
        super.turnOn() // Reuse parent's code
        println("TV channel: $channel") // Add TV-specific logic
    }
}

SmartHome HAS-A SmartTvDevice -- HASA Relationship when one class has an instance of another class
One class contains another class (e.g., SmartHome has a SmartTvDevice).
Used when objects are related but don’t share an "IS-A" relationship.

class SmartHome(val smartTvDevice :SmartTvDevice){
    fun turnOnTv() {
        smartTvDevice.turnOn()
    }
}

Steps to Override:

Mark the parent method/property as open.
Use override in the child class.

Use super to call the parent’s original method.

Visibility Modifiers/Access Specifiers:

public. Default visibility modifier. Makes the declaration accessible everywhere.
The properties and methods that you want used outside the class are marked as public.

private. Makes the declaration accessible in the same class or source file.

protected. Makes the declaration accessible in subclasses. The properties and methods
that you want used in the class that defines them and the subclasses are marked with the protected visibility modifier.

internal. Makes the declaration accessible in the same module. The internal modifier
is similar to private, but you can access internal properties and methods from outside
the class as long as it's being accessed in the same module.

*/