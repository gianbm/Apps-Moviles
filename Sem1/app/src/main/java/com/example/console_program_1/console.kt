package com.example.console_program_1

fun contarVocales(){
    var contador = 0
    val vocales = setOf('a', 'e', 'i', 'o', 'u')

    println("Por favor ingrese un texto:");
    var txt = readln()

    txt = txt.lowercase()

    for(letra in txt){
        if(letra in vocales){
            contador++
        }
    }

    println("El texto ingresado contiene "  + contador + " vocal/es")

}

fun main(){
    contarVocales()
}