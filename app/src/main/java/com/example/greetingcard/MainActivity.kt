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

*/
















