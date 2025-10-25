package com.example.neettoper.data

data class DropperNote(
    val topic: String,
    val author: String, // e.g., "Priya (650+)"
    val note: String,
    val tip: String
)
object MockData {

    val subjects = listOf("Physics", "Chemistry", "Biology")

    val topicsBySubject = mapOf(
        "Physics" to listOf(
            "Units and Measurements",
            "Motion in a Straight Line",
            "Motion in a Plane",
            "Laws of Motion",
            "Work, Energy and Power",
            "System of Particles and Rotational Motion",
            "Gravitation",
            "Mechanical Properties of Solids",
            "Mechanical Properties of Fluids",
            "Thermal Properties of Matter",
            "Thermodynamics",
            "Kinetic Theory"
            // Add all other topics...
        ),
        "Chemistry" to listOf(
            "Some Basic Concepts of Chemistry",
            "Structure of Atom",
            "Classification of Elements and Periodicity",
            "Chemical Bonding and Molecular Structure",
            "States of Matter",
            "Thermodynamics",
            "Equilibrium",
            "Redox Reactions",
            "Hydrogen",
            "The s-Block Elements",
            "The p-Block Elements (Group 13 & 14)"
            // Add all other topics...
        ),
        "Biology" to listOf(
            "The Living World",
            "Biological Classification",
            "Plant Kingdom",
            "Animal Kingdom",
            "Morphology of Flowering Plants",
            "Anatomy of Flowering Plants",
            "Structural Organisation in Animals",
            "Cell: The Unit of Life",
            "Biomolecules",
            "Cell Cycle and Cell Division"
            // Add all other topics...
        )
    )

    val notesByTopic = mapOf(
        "Laws of Motion" to DropperNote(
            topic = "Laws of Motion",
            author = "Rohan V. (670+)",
            note = "My personal notes focused on free-body diagrams. This is the key. If you can draw the FBD correctly, you can solve any problem. I've attached my 3-page summary.",
            tip = "Don't just read! Practice at least 20 FBD problems. Focus on friction and inclined planes. It's a guaranteed question."
        ),
        "Structure of Atom" to DropperNote(
            topic = "Structure of Atom",
            author = "Ananya S. (685+)",
            note = "This chapter is all about formulas. Bohr's model, de Broglie, Heisenberg... I created a single-page formula sheet that saved me hours of revision.",
            tip = "Memorize all the formulas and their conditions. They will ask direct formula-based questions from this chapter."
        ),
        "Plant Kingdom" to DropperNote(
            topic = "Plant Kingdom",
            author = "Priya K. (690+)",
            note = "The trick here is examples. Algae, Bryophytes, Pteridophytes... they all have specific examples. My notes are just charts and mnemonics to remember them all.",
            tip = "Make mnemonics for everything! For example, 'Algae: C U Soon, Very Good' for Chlorophyceae, Phaeophyceae, Rhodophyceae."
        )
    )

}