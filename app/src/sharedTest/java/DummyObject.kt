import com.example.rickandmorty.data.entities.Character
import com.example.rickandmorty.data.entities.CharacterList
import com.example.rickandmorty.data.entities.Info

object DummyObject {
    val fakeListChar: List<Character> = listOf(
        Character(
            "20-01-2022",
            "male",
            1,
            "",
            "Dony Nuransyah",
            "man",
            "nothing",
            "unknown",
            ""
        )
    )
    val fakeCharacterList = CharacterList(Info(1,"",1,false), fakeListChar)
    val fakeCharacter = Character(
        "20-01-2022",
        "male",
        1,
        "",
        "Dony Nuransyah",
        "man",
        "nothing",
        "unknown",
        ""
    )
}