package academy.devdojo.springbootessentials.controller;

import academy.devdojo.springbootessentials.domain.Anime;
import academy.devdojo.springbootessentials.requests.AnimePostRequestBody;
import academy.devdojo.springbootessentials.requests.AnimePutRequestBody;
import academy.devdojo.springbootessentials.services.AnimeService;
import academy.devdojo.springbootessentials.util.AnimeCreator;
import academy.devdojo.springbootessentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springbootessentials.util.AnimePutRequestBodyCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp() throws Exception {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNonPageble())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.findByOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("List returns list of anime inside page object when sucessful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSucessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();
        org.assertj.core.api.Assertions.assertThat(animePage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        org.assertj.core.api.Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("List returns list of anime inside page object when sucessful")
    void list_ReturnsListOfAnimes_WhenSucessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> amimes = animeController.list().getBody();
        org.assertj.core.api.Assertions.assertThat(amimes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        org.assertj.core.api.Assertions.assertThat(amimes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns list of anime inside page object when sucessful")
    void findById_ReturnsListOfAnime_WhenSucessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Anime anime = animeController.findById(1).getBody();
        org.assertj.core.api.Assertions.assertThat(anime).isNotNull();
        org.assertj.core.api.Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns list of anime inside page object when sucessful")
    void findByName_ReturnsListOfAnime_WhenSucessful() {
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());
        List<Anime> anime = animeController.findByName("anime").getBody();
        org.assertj.core.api.Assertions.assertThat(anime).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByName returns list of anime inside page object when sucessful")
    void save_ReturnsListOfAnime_WhenSucessful() throws Exception {
        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
        org.assertj.core.api.Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

    }

    @Test
    @DisplayName("replace updates animes when sucessful")
    void replace_UpdatesAnime_WhenSucessful() throws Exception {
        org.assertj.core.api.Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePostRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePostRequestBody());

        org.assertj.core.api.Assertions.assertThat(entity).isNotNull();

        org.assertj.core.api.Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete removes anime when sucessful")
    void delete_RemovesAnime_WhenSucessful() throws Exception {

        org.assertj.core.api.Assertions.assertThatCode(() -> animeController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Anime> entity = animeController.delete(1);

        org.assertj.core.api.Assertions.assertThat(entity).isNotNull();

        org.assertj.core.api.Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }


}