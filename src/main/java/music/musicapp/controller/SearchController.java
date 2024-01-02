package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.SearchResultDto;
import music.musicapp.service.interfaceService.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public SearchResultDto searchEngine(@RequestParam String query) {
        return searchService.searchEngine(query);
    }
}