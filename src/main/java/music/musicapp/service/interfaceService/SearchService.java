package music.musicapp.service.interfaceService;

import music.musicapp.dto.SearchResultDto;

public interface SearchService {
    SearchResultDto searchEngine(String query);
}
