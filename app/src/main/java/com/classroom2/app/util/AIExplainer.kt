package com.classroom2.app.util

import com.classroom2.app.domain.model.ExplanationMode

/**
 * Local templated "AI" — produces structured, classroom-friendly explanations
 * without any external API. Each mode follows the polish spec's exact layout
 * so the result card always looks intentional.
 */
object AIExplainer {

    fun explain(concept: String, mode: ExplanationMode): String {
        val raw = concept.trim()
        if (raw.isBlank()) return emptyResponse()
        if (raw.lowercase() == "polymorphism") return polymorphismResponse(mode)
        val c = raw.replaceFirstChar { it.lowercase() }
        val C = raw.replaceFirstChar { it.uppercase() }
        return when (mode) {
            ExplanationMode.LIKE_I_AM_12 ->
                "Think of $c like a familiar everyday tool. In simple terms, it means a rule or idea you can reuse in many situations. " +
                "You use it when you want the same kind of solution to work for different inputs."
            ExplanationMode.EXAMPLE ->
                "Example: $C in real life is when the same instruction produces different results depending on who follows it.\n" +
                "Why it matters: it lets one idea cover many cases.\n" +
                "Common mistake: assuming $c only has a single fixed meaning."
            ExplanationMode.THREE_BULLETS ->
                "• $C is the main idea behind this topic.\n" +
                "• It works by applying the same rule across many cases.\n" +
                "• Remembering one clear example makes $c easy to apply later."
            ExplanationMode.MINI_QUIZ ->
                "Check your understanding:\nQuestion: What is the core idea of $c?\n" +
                "A. Memorizing the definition only\n" +
                "B. Understanding how the rule applies in different cases\n" +
                "C. Avoiding examples entirely\n" +
                "Answer: B"
        }
    }

    private fun polymorphismResponse(mode: ExplanationMode): String = when (mode) {
        ExplanationMode.LIKE_I_AM_12 ->
            "Think of polymorphism like the word \"speak\". A dog speaks by barking, a cat by meowing, a person with words. " +
            "Same action, different result depending on who does it. " +
            "In programming, the same method name behaves differently for different objects."
        ExplanationMode.EXAMPLE ->
            "Example: Three classes — Dog, Cat, Cow — each implement a method called sound(). " +
            "Calling animal.sound() returns \"Woof\", \"Meow\", or \"Moo\" depending on the actual object.\n" +
            "Why it matters: one interface, many behaviors — cleaner code, fewer if/else trees.\n" +
            "Common mistake: confusing polymorphism with overloading (overloading is multiple methods with the same name in one class)."
        ExplanationMode.THREE_BULLETS ->
            "• Poly = many, morph = form. Polymorphism = many forms.\n" +
            "• The same method name can do different things for different objects.\n" +
            "• Achieved through inheritance or interfaces in object-oriented languages."
        ExplanationMode.MINI_QUIZ ->
            "Check your understanding:\nQuestion: What does polymorphism mean in programming?\n" +
            "A. A variable changing value\n" +
            "B. One interface having many forms\n" +
            "C. A loop inside another loop\n" +
            "Answer: B"
    }

    private fun emptyResponse(): String =
        "Type a concept above, pick a mode, then tap Explain. " +
        "Try \"polymorphism\" for a structured example."
}
