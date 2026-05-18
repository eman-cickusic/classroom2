package com.classroom2.app.util

import com.classroom2.app.domain.model.ExplanationMode

object AIExplainer {

    fun explain(concept: String, mode: ExplanationMode): String {
        val c = concept.trim().ifBlank { return emptyResponse() }
        val lower = c.lowercase()

        if (lower == "polymorphism") return polymorphismResponse(mode)

        return when (mode) {
            ExplanationMode.LIKE_I_AM_12 ->
                "Think of $c as a real thing you already know — a tool, a recipe, or a game move. " +
                "It is a way of doing something that can be reused in different situations. " +
                "Once you understand the simple version, the complicated version becomes easy."
            ExplanationMode.EXAMPLE ->
                "Example of $c:\n" +
                "Imagine a class assignment where the same instructions produce different results " +
                "depending on which student follows them. The instructions are the $c. Different " +
                "inputs (students) follow the same rules, but each output is unique."
            ExplanationMode.THREE_BULLETS ->
                "• $c is one of the key ideas you'll keep seeing in this topic.\n" +
                "• It works by reusing the same rule across many situations.\n" +
                "• Remembering one clear example makes it easy to apply later."
            ExplanationMode.MINI_QUIZ ->
                "Quick check: What is the main idea of $c?\n" +
                "A) Memorizing the definition only\n" +
                "B) Understanding how the rule applies in different cases\n" +
                "C) Avoiding examples entirely\n" +
                "D) Guessing based on word length\n\n" +
                "(Correct answer: B)"
        }
    }

    private fun polymorphismResponse(mode: ExplanationMode): String = when (mode) {
        ExplanationMode.LIKE_I_AM_12 ->
            "Polymorphism is when one word means many forms.\n\n" +
            "Imagine the word \"speak\". A dog speaks by barking. A cat speaks by meowing. " +
            "A person speaks with words. Same action, different result depending on who is doing it. " +
            "In programming, the same method name behaves differently for different objects."
        ExplanationMode.EXAMPLE ->
            "Example of polymorphism:\n\n" +
            "Three classes — Dog, Cat, Cow — each implement a method called `sound()`. " +
            "When you call `animal.sound()`, you get \"Woof\", \"Meow\", or \"Moo\" depending on the actual object. " +
            "One method name, many behaviors — that's polymorphism."
        ExplanationMode.THREE_BULLETS ->
            "• Poly = many, morph = form. Polymorphism = many forms.\n" +
            "• The same method name can do different things for different objects.\n" +
            "• Achieved through inheritance or interfaces in object-oriented languages."
        ExplanationMode.MINI_QUIZ ->
            "Mini quiz: What does polymorphism mean in programming?\n" +
            "A) A variable changing value\n" +
            "B) One interface having many forms\n" +
            "C) A loop inside another loop\n" +
            "D) Storing data permanently\n\n" +
            "(Correct answer: B)"
    }

    private fun emptyResponse(): String =
        "Type a concept above and pick a mode — then tap Explain. " +
        "Try \"polymorphism\" for a demo-ready answer."
}
