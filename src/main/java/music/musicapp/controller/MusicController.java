package music.musicapp.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import music.musicapp.exception.ExceptionEnum;
import music.musicapp.exception.RestException;
import music.musicapp.service.MusicServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("user/")
@PreAuthorize("hasRole('USER_PREMIUM')")
public class MusicController {

    @Value("${application.project.music}")
    private String path;
    private final MusicServiceImpl musicService;

    @PostMapping("add-music")
    @PreAuthorize("hasAuthority('user_premium:create')")
    public ResponseEntity<String> createNewMusic(String path, MultipartFile file, @RequestParam String musicType,@RequestParam String musicText) throws IOException {
        String uploadedFileName = musicService.uploadFile(path, file, musicType, musicText);
        return ResponseEntity.ok(new RestException(ExceptionEnum.FILE_UPLOADED) + uploadedFileName);
    }

    @GetMapping("{fileName}")
    public void serveFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream inputStream = musicService.getResourceFile(path, fileName);
        response.setContentType("audio/mpeg");
        StreamUtils.copy(inputStream, response.getOutputStream());
    }
}
