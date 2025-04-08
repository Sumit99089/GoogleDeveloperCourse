package com.example.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.example.greetingcard.ui.theme.GreetingCardTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            GreetingCardTheme {
//                Scaffold(modifier = Modifier.fillMaxSize(),
//                    content = { padding ->
//                        GreetingText(
//                            "Happy Birthday Sam!",
//                            "from Emma",
//                            Modifier.padding(padding)
//                        )
//                    }
//                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingImage(
                        message = stringResource(R.string.happy_birthday_sam),
                        from = stringResource(R.string.from_emma)
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            fontSize = 88.sp,
            lineHeight = 116.sp,   // By default the line height is around 14-16 sp, so when we make the text size 100 sp, the text overlaps. so we have to increase lineHeight.Remove lineHeight to see the error
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = from,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun GreetingImage(message: String, from: String, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.androidparty)
    Box(modifier){
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop, //To adjust the scale type of the image, which says how to size the image, to make it fullscreen.
            alpha = 0.5F // For Opacity of the image
        )
        GreetingText(
            message = message,
            from = from,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

//@ Annotation, eg:- @JSON, @GET, @Composable
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    GreetingCardTheme {
        GreetingImage("Happy Birthday Sam", "From Emma")
    }
}

/*

Notes:

By default the line height is around 14-16 sp(not sure if this is the default height),
so when we make the text size 100 sp, the text overlaps.
So we have to increase lineHeight. Remove lineHeight to see the error

Scaffold Parameters:
Parameter	                Purpose
topBar	                    Puts a TopAppBar at the top
bottomBar	                Adds BottomNavigation or any footer UI
floatingActionButton	    Displays a FAB
drawerContent	            For side navigation drawer
snackbarHost	            To show snackbars
content	                    Main screen content goes here

Example Code:

Scaffold(
    topBar = {
        TopAppBar(title = { Text("My App") })
    },
    bottomBar = {
        BottomNavigation {
            // your nav items here
        }
    },
    floatingActionButton = {
        FloatingActionButton(onClick = { /* do something */ }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    },
    content = { padding ->      //use the padding only in the content section, the other parameters are handled by itself
        // Main screen content
        Column(modifier = Modifier.padding(padding)) {
            Text("Hello, World!")
        }
    }
)

End of Example Code

column = 1  row = 1  2  3
         2
         3
When we call functions, we call them like Row(), but Row can also be called like
Row(){
    Trailing Lambda{we define the lambda function here}
}

We can also define the lambda function inside the parenthesis like but the above is preferred:
Row(
    content = {
        Define the lambda function here
    }
)


dp=density independent pixels
sp=scale independent pixels


When we use scaffold in the main activity and call the function in it, the text is not aligned horizontally
That is because:

The GreetingText is placed inside the Scaffold's content lambda,
and the padding provided by the Scaffold is applied to it.
This padding ensures that the content does not overlap with the topBar,
bottomBar, or system bars. However, this padding can affect the available space
for the GreetingText, and if not handled carefully, it might prevent the
Column from occupying the full height, thus affecting the vertical centering of its children.

The surface does not impose such restrictions.The difference in content positioning between Surface and
Scaffold arises from their handling of layout constraints and padding. Surface offers a straightforward container
without additional constraints, while Scaffold manages layout components and applies padding to prevent overlap with
system bars and its own components. Understanding these behaviors allows you to manage content positioning effectively in Jetpack Compose.



For Row: horizontalArrangement & verticalAlignment
For Column: verticalArrangement & horizontalAlignment
Because:
A Row lays out its children horizontally → so you arrange them horizontally and align them vertically.

A Column lays out its children vertically → so you arrange them vertically and align them horizontally.


vertical Alignment( Types via Image : https://developer.android.com/static/codelabs/basic-android-kotlin-compose-add-images/img/df69881d07b064d0.gif
horizontal Arrangement Types via Image : https://developer.android.com/static/codelabs/basic-android-kotlin-compose-add-images/img/c1e6c40e30136af2.gif




Padding values:
https://developer.android.com/static/codelabs/basic-android-kotlin-compose-add-images/img/2e96e127f9f8c7_960.png
Modifier.padding(
    start = 16.dp,
    top = 16.dp,
    end = 16.dp,
    bottom = 16.dp
)
or
Modifier.padding(8.dp)

Hardcoded String values make it difficult to translate the app to different language. So we use string resource. Like:
message = stringResource(R.string.happy_birthday_sam),
                        from = stringResource(R.string.from_emma)



2 types of viewing Images:

Image Composable:
High-level UI element.

Used to display images from resources (e.g., painterResource), bitmaps, URLs (via libraries like Coil), etc.

Automatically handles layout, accessibility, scaling, and recomposition.

Ideal for UI development and straightforward image display.

Image(
    painter = painterResource(id = R.drawable.my_image),
    contentDescription = "My image",
    modifier = Modifier.size(100.dp)
)

Draw into Canvas:

Low-level drawing API inside Modifier.drawBehind or Modifier.drawWithContent.

Gives direct access to the Canvas so you can draw shapes, paths, text, or even images manually.

More flexible and powerful, but requires more control and manual layout handling.

Great for custom drawings, effects, or animations.

Modifier.drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            color = Color.Red
        }
        canvas.drawCircle(center, radius, paint)
    }
}

Feature:	        Image()	                                    drawIntoCanvas
Abstraction Level:	High-level composable	                    Low-level drawing API
Use Case:	        Displaying images	                        Custom drawing (shapes, custom images, etc.)
Ease of Use:	    Very easy	                                Requires manual setup
Performance:	    Optimized for typical image usage	        More control but needs careful optimization
Layout Integration:	Follows Compose layout automatically	    You manage positioning and size manually
Accessibility:	    Supports content descriptions	            Manual if needed




Modifier.weight(1f) makes both the child composable take the same size(both width and height) as in Compose Quadrant app.

Different types of When Statement checking:

fun main() {
    val x: Any = 4

    when (x) {
        2, 3, 5, 7 -> println("x is a prime number between 1 and 10.")
        in 1..10 -> println("x is a number between 1 and 10, but not a prime number.")
        is Int -> println("x is an integer number, but not between 1 and 10.")
        else -> println("x isn't an integer number.")
    }
}

Variables in kotlin can store functions
fun printStop() = println("Stop")
val messageFunction: () -> Unit = ::printStop


when (x) {
    in 1..10 -> print("x is in the range")
    in validNumbers -> print("x is valid")
    !in 10..20 -> print("x is outside the range")
    else -> print("none of the above")
}

for (i in 1..3) {
    print(i)
}

for (i in 6 downTo 0 step 2) {
    print(i)
}


for (i in array.indices) {
    print(array[i])
}

To use methods on a null variable/Access a property of a nullable variable:


1. Use the ?. safe call operator

Syntax: nullableVariable ?. method/property

fun main() {
    var favoriteActor: String? = null
    println(favoriteActor?.length)-> Output: null
    favoriteActor = "Sandra Oh"
    println(favoriteActor?.length)-> Output: 9
}

2.Use the !! not-null assertion operator

Syntax: nullableVariable !!. method/property

fun main() {
    var favoriteActor: String? = "Sandra Oh"
    println(favoriteActor!!.length) -> Output: 9
    favoriteActor = null
    println(favoriteActor?.length)-> Throws NullPointerException(https://developer.android.com/static/codelabs/basic-android-kotlin-compose-nullability/img/c74ab53164de0c01_1440.png)
}

3.Using if/else statements

fun main() {
    var favoriteActor: String? = null

    if(favoriteActor != null) {
      println("The number of characters in your favorite actor's name is ${favoriteActor.length}.")
    } else {
      println("You didn't input a name.")
    }
}

4.Use the ?: Elvis operator

val variable = nullableVariable?.method/property ?: defaultValue

fun main() {
    var favoriteActor: String? = "Sandra Oh"

    val lengthOfName = favoriteActor?.length ?: 0

    println("The number of characters in your favorite actor's name is $lengthOfName.")-> Output: 9

    favoriteActor = null

    val lengthOfName = favoriteActor?.length ?: 0

    println("The number of characters in your favorite actor's name is $lengthOfName.")-> Output: 0
}

Oops:

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

















