package academy.devdojo.springbootessentials.repository;

import academy.devdojo.springbootessentials.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@DisplayName("Tests for Anime Repository")
@Log4j2
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save creates anime when Sucessful")
    void save_PersistAnime_WhenSucessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        assertThat(animeSaved).isNotNull();
        assertThat(animeSaved.getId()).isNotNull();
        assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());

    }

    @Test
    @DisplayName("Save updates anime when Sucess")
    void save_UpdateAnime_WhenSucessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeUpdated = this.animeRepository.save(animeToBeSaved);
        animeUpdated.setName("Overlord");
        log.info(animeUpdated.getName());
        assertThat(animeUpdated).isNotNull();
        assertThat(animeUpdated.getId()).isNotNull();
        assertThat(animeUpdated.getName()).isEqualTo(animeToBeSaved.getName());

    }

    @Test
    @DisplayName("Delete removes anime when Sucessful")
    void delete_RemovesAnime_WhenSucessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        this.animeRepository.delete(animeSaved);
        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());
        assertThat(animeOptional.isEmpty()).isTrue();

    }

    @Test
    @DisplayName("Find By Name removes anime when Sucessful")
    void findName_ReturnsListOfAnime_WhenSucessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        String name = animeSaved.getName();
        List<Anime> animes = this.animeRepository.findByName(name);
        assertThat(animes).isNotEmpty();
        assertThat(animes).contains(animeSaved);

    }

    @Test
    @DisplayName("Find By Name returns empty list when No Animes Is Found")
    void findName_ReturnsEmptyList_WhenAnimeNotFound() {
        List<Anime> animes = this.animeRepository.findByName("123");
        assertThat(animes).isEmpty();
    }



    private Anime createAnime(){
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }

}