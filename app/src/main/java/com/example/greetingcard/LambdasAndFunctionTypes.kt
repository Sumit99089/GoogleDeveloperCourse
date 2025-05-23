package com.example.greetingcard
// Using Functions as Data Types, taking functions as inputs, returning a function etc.
// Functions are first class constructs in kotlin meaning they can be assigned to a variable, passed
// as an argument or even be returned
//A function that returns a function or takes a function as an argument is called a higher-order
// function
fun main(){

    var x = :: trick1 // or var x = trick1().
    //The :: operator tells the compiler that trick is a function or just use trick()
    x() // calling the trick1 fun via x

    var y = trick
    y() // calling the trick2 lambda fun via y

    var z= trickOrTreat1(false) {it->
        return@trickOrTreat1 "Take $it cupcakes"
    //return@trickOrTreat statement specifies that the return is from the lambda passed to
    //trickOrTreat, not from the enclosing function(here enclosing fun = main fun.
    }
    z()
    var q= trickOrTreat1(false) {
        "Take $it cupcakes"
    }
    q()
    var p = trickOrTreat2(false)
    p()

    //Repeat function, A higher order function. works same as a normal loop
    repeat(5){
        trick()
        treat()
        trickOrTreat2((it%2==0))
    }
}

fun trick1(){
    println("No treats")
}

//Lambda Function
var trick = {
    println("No treats")
}

var treat: () -> Unit = {
    println("Have a treat")
}

//Both trick and treat variables are od datatype lambda function and just like var x: Int,
// here treat is of type ()->Unit. Here treat doesnot take any parameters as input as shown by
// empty () and does not return anything as ->Unit like (Int, String)->Double, takes int and string
// as input and returns a double

// This function returns a lambda function which does not take anything as input and returns Unit(void)
// Here they return lambdas trick or treat which are of type ()->Unit
// It also takes in a lambda (Int)->String as an input
fun trickOrTreat1(isTrick: Boolean, extraTreat : (Int)->String ): () -> Unit {
    if(isTrick)
        return trick
    else{
        extraTreat(5)
        return treat
    }
}

fun trickOrTreat2(isTrick: Boolean, extraTreat : ((Int)->String)? = null ): () -> Unit {
    if(isTrick)
        return trick
    else{
        // For null safety, using safe call operator to invoke/call the function
        // prints null if extraTreat is null, otherwise prints string that extraTreat returns
        println(extraTreat?.invoke(5))
//        extraTreat is a nullable lambda of type ((Int) -> String)?.
//        If extraTreat is not null, use it.
//        If extraTreat is null, use the fallback lambda { _ -> "Default treat" }.
//        { _ -> "Default treat" }
//        _ indicates the parameter (an Int) is ignored.
//        Returns the string "Default treat" regardless of the input.
        println((extraTreat ?: { _ -> "Default treat" }).invoke(5))
        println(extraTreat!!.invoke(5))
        return treat
    }
}

//Modifier.wrapContentSize(Alignment.Center) use:
//Content Sizing: Ensures a composable only consumes as much space as its content requires.
//Alignment within Parent: Positions the composable content within its parent using the specified
//alignment, such as centering or aligning to edges.

//Composables are stateless by default, which means that they don't hold a value and can be
//recomposed any time by the system, which results in the value being reset. However, Compose
//provides a convenient way to avoid this. Composable functions can store an object in memory
//using the remember composable.
//
//Make the result variable a remember composable.
//The remember composable requires a function to be passed.
//
//In the remember composable body, pass in a mutableStateOf() function and then pass the function a
// 1 argument.
//The mutableStateOf() function returns an observable. You learn more about observables later,
//but for now this basically means that when the value of the result variable changes, a
//recomposition is triggered, the value of the result is reflected, and the UI refreshes.





