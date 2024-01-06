package academy.devdojo.springbootessentials.util;

import academy.devdojo.springbootessentials.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {
    public static AnimePutRequestBody createAnimePostRequestBody() {
        return AnimePutRequestBody.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
