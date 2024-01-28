package music.musicapp.controller;

import lombok.RequiredArgsConstructor;
import music.musicapp.dto.ChangePasswordRequest;
import music.musicapp.dto.SearchResultDto;
import music.musicapp.service.interfaceService.SearchService;
import music.musicapp.service.interfaceService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/panel/")
@PreAuthorize("hasRole('USER')")
public class UserController {
    private final UserService userService;
    private final SearchService searchService;


    @GetMapping("search")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<SearchResultDto> globalSearch(@RequestParam String query) {
        return new ResponseEntity<>(searchService.searchEngine(query), HttpStatus.OK);
    }

    @PatchMapping("change/password")
    @PreAuthorize("hasAuthority('user:update')")
    public void changePassword(@RequestBody ChangePasswordRequest request, Principal principal) {
        userService.changePassword(request, principal);
    }
}